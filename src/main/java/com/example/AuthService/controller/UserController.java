package com.example.AuthService.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.example.AuthService.dto.*;
import com.example.AuthService.exceptions.SpringTwitterException;
import com.example.AuthService.model.*;
import com.example.AuthService.repository.ForgotPasswordRepository;
import com.example.AuthService.repository.VerificationTokenRepository;
import com.example.AuthService.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.example.AuthService.repository.AuthControlRepository;
import com.example.AuthService.security.TokenUtils;
import com.example.AuthService.service.AuthControlService;


@RestController
@RequestMapping("/auth")
public class UserController {


   @Autowired
   private AuthControlRepository authControlRepository;

   @Autowired
	private MailService mailService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;


	@Autowired
	private ForgotPasswordRepository forgotPasswordRepository;


    @Autowired
    TokenUtils tokenUtils;
   
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> createAuthenticationToken(@Valid
            @RequestBody LoginDTO loginDTO, HttpServletResponse response) {

		AuthControl check = authControlRepository.findByUsername(loginDTO.getUsername()).orElseGet(null);
		if (!check.isEnabled()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not Validated");
		}

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),loginDTO.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        System.out.println("xxxxxxxxxxxxxxxxxxx");       
        return ResponseEntity.ok(new TokenDTO(jwt));
    }
    
    @PostMapping(value = "/",consumes = "application/json")
    public ResponseEntity<UserPassDTO> register(@Valid
            @RequestBody RegisterDTO registerDTO, HttpServletResponse response) {
    	
    		List<AuthControl> allUsers = authControlRepository.findAll();
    		for(AuthControl i : allUsers) {
    			if (i.getUsername().equals(registerDTO.getUsername())) {
    				throw new ResponseStatusException(
    				           HttpStatus.FORBIDDEN, "Username Taken");
    				    }
    			}

//		final String pattern = "^[a-zA-Z0-9].{8,}";



				AuthControl x = new AuthControl();
				x.setUsername(registerDTO.getUsername());
				x.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
				x.setEmail(registerDTO.getEmail());
				x.setRole(ERole.valueOf(registerDTO.getRole()));
				x.setEnabled(false);
				authControlRepository.save(x);

				String token = generateVerificationToken(x);
					mailService.sendMail(new NotificationEmail("Please activate your account",
					x.getEmail(), "Thank you for signing up to Spring Twitter, " +
					"please click on the below url to activate your account : " +
					"http://localhost:8080/auth/accountVerification/" + token));



				UserPassDTO userPass = new UserPassDTO();
				userPass.setAge(registerDTO.getAge());
				userPass.setCity(registerDTO.getCity());
				userPass.setName(registerDTO.getName());
				userPass.setSex(registerDTO.getSex());
				userPass.setSurname(registerDTO.getSurname());
				userPass.setUsername(registerDTO.getUsername());

				RestTemplate restTemplate = new RestTemplate();
				HttpEntity<UserPassDTO> request = new HttpEntity<>(userPass);

				String serviceUrl = "http://registracija:8082/users/";
				UserPassDTO UserPassResponse = restTemplate.postForObject(serviceUrl,
						request, UserPassDTO.class);
				return new ResponseEntity<UserPassDTO>(userPass, HttpStatus.CREATED);




    }

	@PostMapping(value = "/businessregister", consumes = "application/json")
	public ResponseEntity<BusinessPassDTO> registerBusiness(@Valid @RequestBody BusinessRegisterDTO bla){

		List<AuthControl> allUsers = authControlRepository.findAll();
		for(AuthControl i : allUsers) {
			if (i.getUsername().equals(bla.getUsername())) {
				throw new ResponseStatusException(
						HttpStatus.FORBIDDEN, "Username Taken");
			}
		}

		AuthControl x = new AuthControl();
		x.setUsername(bla.getUsername());
		x.setPassword(passwordEncoder.encode(bla.getPassword()));
		x.setEmail(bla.getEmail());
		x.setRole(ERole.valueOf(bla.getRole()));
		x.setEnabled(false);
		authControlRepository.save(x);

		String token = generateVerificationToken(x);
		mailService.sendMail(new NotificationEmail("Please activate your business account",
				x.getEmail(), "Thank you for signing up to Spring Twitter, " +
				"please click on the below url to activate your account : " +
				"http://localhost:8080/auth/accountVerification/" + token));

		BusinessPassDTO businessPass = new BusinessPassDTO();
		businessPass.setUsername(bla.getUsername());
		businessPass.setCompanyName(bla.getCompanyName());
		businessPass.setEmail(bla.getEmail());
		businessPass.setWebsite(bla.getWebsite());

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<BusinessPassDTO> request = new HttpEntity<>(businessPass);

		String serviceUrl = "http://registracija:8082/users/business";
		UserPassDTO UserPassResponse = restTemplate.postForObject(serviceUrl,
				request, UserPassDTO.class);
		return new ResponseEntity<BusinessPassDTO>(businessPass, HttpStatus.CREATED);


	}

	private String generateVerificationToken(AuthControl authControl) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setAuthControl(authControl);

		verificationTokenRepository.save(verificationToken);
		return token;
	}

	//@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String Username = verificationToken.getAuthControl().getUsername();
		AuthControl authControl = authControlRepository.findByUsername(Username).orElseThrow(() -> new SpringTwitterException("User not found with name - " + Username));
		authControl.setEnabled(true);
		authControlRepository.save(authControl);
	}

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(() -> new SpringTwitterException("Invalid Token"));
		fetchUserAndEnable(verificationToken.get());
	}




    @PostMapping("/changepassword")
    public ResponseEntity changePassword(Authentication auth, @RequestBody ChangePasswordDTO changePasswordDTO){
    	
    	  System.out.println(auth);
    	  UserDetails userDetails = (UserDetails)auth.getPrincipal();
    	  AuthControl a = authControlRepository.findById(userDetails.getUsername()).orElse(null);
    	  String passwordInSystem = a.getPassword();
    	  String oldPassword = changePasswordDTO.getOldPassword();
    	  String oldPasswordEncrypted = passwordEncoder.encode(oldPassword);
    	  
    	  
    	  BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
    	  boolean passChecker = bc.matches(oldPassword, passwordInSystem);
    	  
    	  if (passChecker) {
      			a.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
      			authControlRepository.save(a);	
      			return new ResponseEntity(HttpStatus.ACCEPTED);
      		}
    	  
    	  return new ResponseEntity(HttpStatus.BAD_REQUEST);
      
    }


	@GetMapping("accountVerification/{mytoken}")
	public ResponseEntity<String> verifiedAccount(@PathVariable String mytoken) {
		verifyAccount(mytoken);

		return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
	}

	@GetMapping("/profile")
	public ResponseEntity getProfile(Authentication auth){

		UserDetails userDetails = (UserDetails)auth.getPrincipal();
		AuthControl a = authControlRepository.findById(userDetails.getUsername()).orElse(null);

		UserAndRole userAndRole = new UserAndRole();
		userAndRole.setUsername(a.getUsername());
		userAndRole.setRole(a.getRole().toString());

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<UserAndRole> request = new HttpEntity<>(userAndRole);

		String serviceUrl = "http://registracija:8082/users/myProfile?username={username}&role={role}";
		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("username", a.getUsername());
		uriParams.put("role", a.getRole().toString());
		System.out.println(a.getUsername() + a.getRole().toString() );
		
		if(a.getRole().equals(ERole.User)) {
			User UserPassResponse = restTemplate.getForObject(serviceUrl, User.class, uriParams);
			return new ResponseEntity<User>(UserPassResponse, HttpStatus.OK);
		}
		if(a.getRole().equals(ERole.BusinessUser)) {
			BusinessUser UserPassResponse = restTemplate.getForObject(serviceUrl, BusinessUser.class, uriParams);
			return new ResponseEntity<BusinessUser>(UserPassResponse, HttpStatus.OK);
		}
		
	     
		return new ResponseEntity<UserAndRole>(userAndRole, HttpStatus.OK);
	}


	@PostMapping("/forgotPasswordEnterMail/{email}")
	public ResponseEntity napraviTokenIPosaljiGa(Authentication auth,@PathVariable("email") String email){
		UserDetails userDetails = (UserDetails)auth.getPrincipal();
		AuthControl a = authControlRepository.findById(userDetails.getUsername()).orElse(null);

		String enteredEmail = email;

		String fpToken = generateForgotPasswordToken(a.getUsername(),email);

		mailService.sendMail(new NotificationEmail("Forgot password token",
				a.getEmail(), "You've been provided with forgot password token. " +
				"Copy this token and paste it in provided field: "+ "\n" + fpToken));

		String n = "Your token is created and sent to email adress: " + email;
		return new ResponseEntity(n,HttpStatus.CREATED);
	}


	private String generateForgotPasswordToken(String username,String email) {

		String token = UUID.randomUUID().toString();
		ForgotPasswordToken verificationToken = new ForgotPasswordToken();
		verificationToken.setToken(token);
		verificationToken.setUsername(username);
		verificationToken.setEmail(email);
		verificationToken.setExpireDate(LocalDateTime.now().plusDays(2));

		forgotPasswordRepository.save(verificationToken);
		return token;
	}



//	@GetMapping("/forgotPasswordTokenCheck")
//	private ResponseEntity
}
