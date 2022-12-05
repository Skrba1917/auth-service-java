package com.example.AuthService.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.AuthService.dto.*;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.example.AuthService.model.AuthControl;
import com.example.AuthService.model.ERole;
import com.example.AuthService.repository.AuthControlRepository;
import com.example.AuthService.security.TokenUtils;
import com.example.AuthService.service.AuthControlService;


@RestController
@RequestMapping("/auth")
public class UserController {


   @Autowired
   private AuthControlRepository authControlRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    TokenUtils tokenUtils;
   
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> createAuthenticationToken(@Valid
            @RequestBody LoginDTO loginDTO, HttpServletResponse response) {

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
				x.setRole(ERole.valueOf(registerDTO.getRole()));
				authControlRepository.save(x);

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
		x.setRole(ERole.valueOf(bla.getRole()));
		authControlRepository.save(x);

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
//    
//    @PostMapping(value="/changepassword", consumes ="application/json")
//    public ResponseEntity  changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, HttpServletResponse response) {
//    	
//    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    	String username = userDetails.getUsername();
//    	String password = userDetails.getPassword();
//    	String oldPassword = changePasswordDTO.getOldPassword();
//    	String oldPasswordEncrypted = passwordEncoder.encode(oldPassword);
//    	
//    	AuthControl x = authControlRepository.findById(username).orElse(null);
//    	
//    	if (oldPasswordEncrypted.matches(password)) {
//    		x.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
//    		authControlRepository.save(x);	
//    		throw new ResponseStatusException(HttpStatus.ACCEPTED, "Password changed successfully");
//    	}
//    	
//    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request for password change");   	
//    	   	
//    }
    
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

}
