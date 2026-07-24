package com.TradeX.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JwtProvider {
    private  static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());
    public  static  String generateToken( Authentication auth){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);
        return null;
    }

    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<Object> auth = new HashSet<>();
        for (GrantedAuthority ga : authorities) {
            auth.add(ga.getAuthority());

        }
        return String.join("," + auth);
    }
}
