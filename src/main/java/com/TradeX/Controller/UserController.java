package com.TradeX.Controller;


import com.TradeX.Domain.VerificationType;
import com.TradeX.modal.User;
import com.TradeX.service.EmailService;
import com.TradeX.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    private String jwt;
    private VerificationType verificationType;

    @GetMapping("/api/user/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization")String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(user, HttpStatus.OK);


    }

    @PostMapping("/api/user/verification/{verificationType}/send-otp")
    public ResponseEntity<User> sendVerificationOtp(
            @RequestHeader("Authorization")String jwt,
            @PathVariable VerificationType verificationType) throws Exception {


        User user = userService.findUserProfileByJwt(jwt);



        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PatchMapping("/api/user/enalbe-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@RequestHeader("Authorization")String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
