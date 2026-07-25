package com.TradeX.Controller;

import com.TradeX.config.JwtProvider;
import com.TradeX.modal.User;
import com.TradeX.repository.UserRepository;
import com.TradeX.response.AuthResponse;
import com.TradeX.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

     @Autowired
     private UserRepository UserRepository ;

     @Autowired
     private CustomerUserDetailsService customerUserDetailsService;

     @PostMapping("/signup")
     public ResponseEntity<AuthResponse> register (@RequestBody User user) throws Exception {

         User isEmailExist = UserRepository.findByEmail(user.getEmail());
         if (isEmailExist != null) {
             throw new Exception("email is already Exist use with an other account");
         }

         User newUser = new User();
         newUser.setFullName(user.getFullName());
         newUser.setEmail(user.getEmail());
         newUser.setPassword(user.getPassword());

         User savedUser =  UserRepository.save( newUser);

         Authentication auth = new UsernamePasswordAuthenticationToken(
                 user.getEmail(),
                 user.getPassword()

         );
         SecurityContextHolder.getContext().setAuthentication(auth);

         String jwt = JwtProvider.generateToken(auth);

         AuthResponse res = new AuthResponse();
         res.setJwt(jwt);
         res.setStatus(true);
         res.setMessage("success");

         return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

        String userName = user.getPassword();
        String password = user.getPassword();



        Authentication auth = authenticate(userName,password);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid  password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }
}
