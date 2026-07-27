package com.TradeX.Controller;

import com.TradeX.config.JwtProvider;
import com.TradeX.modal.TwoFactorOTP;
import com.TradeX.modal.User;
import com.TradeX.repository.UserRepository;
import com.TradeX.response.AuthResponse;
import com.TradeX.service.CustomerUserDetailsService;
import com.TradeX.service.EmailService;
import com.TradeX.service.TwoFactorOtpService;
import com.TradeX.utils.OtpUtils;
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
     private UserRepository userRepository ;

     @Autowired
     private CustomerUserDetailsService customerUserDetailsService;

     @Autowired
     private TwoFactorOtpService twoFactorOtpService;

     @Autowired
     private EmailService emailService;

     @PostMapping("/signup")
     public ResponseEntity<AuthResponse> register (@RequestBody User user) throws Exception {

         User isEmailExist = userRepository.findByEmail(user.getEmail());
         if (isEmailExist != null) {
             throw new Exception("email is already Exist use with an other account");
         }

         User newUser = new User();
         newUser.setFullName(user.getFullName());
         newUser.setEmail(user.getEmail());
         newUser.setPassword(user.getPassword());

         User savedUser =  userRepository.save( newUser);

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

        String userName = user.getEmail();
        String password = user.getPassword();



        Authentication auth = authenticate(userName,password);

        SecurityContextHolder.getContext().setAuthentication(auth);

        User authUser = userRepository.findByEmail(userName);

        String jwt = JwtProvider.generateToken(auth);

        if (user.getTowFactorAuth().isEnabled()) {

            AuthResponse res = new AuthResponse();

            res.setJwt("Tow Factor Auth is enabled ");

            res.setTwoFactorAuthEnabled(true);

            String otp = OtpUtils.generatedOTP();


            TwoFactorOTP oldTwoFactorOtp = twoFactorOtpService.findByUser(authUser.getId());
            if (oldTwoFactorOtp != null) {
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOtp);
            }
            TwoFactorOTP newTwoFactorOtp = twoFactorOtpService.createTwoFactorOtp(
                    authUser,otp,jwt);

           emailService.sendVerificationEmail(userName,otp);



            res.setSession(newTwoFactorOtp.getId());
            return  new ResponseEntity<>(res, HttpStatus.CREATED);
        }

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
    public  ResponseEntity<AuthResponse> verifySiginOtp (String email) {
        AuthResponse res = new AuthResponse();
        //02:34
    }
}
