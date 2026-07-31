package com.TradeX.Controller;


import com.TradeX.Domain.VerificationType;
import com.TradeX.Request.ForgotPasswordTokenRequest;
import com.TradeX.Request.ResetPasswordRequest;
import com.TradeX.modal.ForgotPasswordToken;
import com.TradeX.modal.User;
import com.TradeX.modal.VerificationCode;
import com.TradeX.response.ApiResponse;
import com.TradeX.response.AuthResponse;
import com.TradeX.service.EmailService;
import com.TradeX.service.ForgotPasswordService;
import com.TradeX.service.UserService;
import com.TradeX.service.VerificationCodeService;
import com.TradeX.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

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
    public ResponseEntity<String> sendVerificationOtp(
            @PathVariable String otp,
            @RequestHeader("Authorization")String jwt,
            @PathVariable VerificationType verificationType) throws Exception {


        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode=verificationCodeService
                .getVerificationCodeByUser(user.getId());
        if(verificationCode==null){

            verificationCode=verificationCodeService
                    .sendverificationCode(user, verificationType);

        }
        if(verificationType==VerificationType.EMAIL){
            emailService.sendVerificationOtpEmail(user.getEmail()
                    ,verificationCode.getOtp() );

        }


        return new ResponseEntity<>(" verification otp Successfully Sned", HttpStatus.OK);
    }


    @PatchMapping("/api/user/enalbe-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@RequestHeader("Authorization")String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode= verificationCodeService
                .getVerificationCodeByUser(user.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
                verificationCode.getEmail():verificationCode.getMobile();

        boolean isVerified=verificationCode.getOtp().equals(sendTo);
        if(isVerified){
            User updateuser = userService.enableTwoFactorAuthentication(
                    verificationCode.getVerificationType(),sendTo,user);
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updateuser, HttpStatus.OK);

        }

        throw new Exception("Wrong verification code");
    }
    @PostMapping("/auth/reset-password/verification/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(

            @RequestBody ForgotPasswordTokenRequest req) throws Exception {

          User user = userService.findUserProfileByEmail(req.getSendTo());
          String otp= OtpUtils.generatedOTP();
          UUID uuid = UUID.randomUUID();
          String id = uuid.toString();


          ForgotPasswordToken token =  forgotPasswordService.findByUser(user.getId());

          if(token==null){
              token= forgotPasswordService.createToken(user,id,otp,req.getVerificationType(),req.getSendTo());
          }
          if (req.getVerificationType().equals(VerificationType.EMAIL)) {

              emailService.sendVerificationOtpEmail(
                      user.getEmail()
                      ,token.getOtp());
          }
        AuthResponse response = new AuthResponse();
          response.setSession(token.getId());
          response.setMessage("Forgot Password Reset Successfully");



        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PatchMapping("/api/auth/reset-password/verify-otp/")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam String id,
            @RequestBody ResetPasswordRequest req,
            @RequestHeader("Authorization")String jwt
            ) throws Exception {


        ForgotPasswordToken forgotpasswordToken = forgotPasswordService.findById(id);

        boolean isVerified = forgotpasswordToken.getOtp()
                .equals(req.getOtp());
        if(isVerified){
            userService.updatePassword(forgotpasswordToken.getUser(),req.getPassword());

            ApiResponse res= new ApiResponse();
            res.setMessage("Password Update Successfully");


            return new ResponseEntity<>(res,HttpStatus.ACCEPTED);

        }
throw new Exception("Wrong verification code");
    }


}
