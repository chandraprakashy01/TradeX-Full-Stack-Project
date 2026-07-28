package com.TradeX.service;

import com.TradeX.Domain.VerificationType;
import com.TradeX.config.JwtProvider;
import com.TradeX.modal.TowFactorAuth;
import com.TradeX.modal.User;
import com.TradeX.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user == null){
            throw new Exception(" user not fond");
        }
        return user;
    }

    @Override
    public User findUserProfileByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);

        if (user == null){
            throw new Exception(" user not fond");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new Exception(" user not fond");
        }
        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType,
                                              String sendTo,
                                              User user) {


        TowFactorAuth towFactorAuth = new TowFactorAuth();
        towFactorAuth.setEnabled(true);
        towFactorAuth.setSendTo(verificationType);
        user.setTowFactorAuth(towFactorAuth);

        return userRepository.save(user);
    }


    @Override
    public User updatePassword(User user, String newPassword) {
         user.setPassword(newPassword);

        return userRepository.save(user);
    }
}
