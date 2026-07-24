package com.TradeX.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {
    @GetMapping
    public  String home(){
        return"Welcome to trading Platform";
    }
    @GetMapping("/SecureRound")
    public  String SecureRound(){
        return"Welcome to trading Platform and See the secrity of this platform";
    }

}
