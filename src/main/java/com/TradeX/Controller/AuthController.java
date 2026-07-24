package com.TradeX.Controller;

import com.TradeX.modal.User;
import com.TradeX.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
     @Autowired
    private UserRepository UserRepository ;
     @PostMapping("/signup")
    public ResponseEntity<User> register ( @RequestBody User user) throws Exception {

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




         return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
