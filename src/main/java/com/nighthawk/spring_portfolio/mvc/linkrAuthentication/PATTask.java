package com.nighthawk.spring_portfolio.mvc.linkrAuthentication;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PATTask {
    @Autowired
    PatJpaRepository PATRepo; 

    @Scheduled(fixedRate = 100000)
    public void TestTask(){
        List<LinkrPAT> pats = PATRepo.findAll();
        for(LinkrPAT p : pats){
            p.setPAT(generateUniqueToken(6));
            PATRepo.save(p);
        }
        System.out.println("executed");
    }

    public String generateUniqueToken(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length / 2];
        secureRandom.nextBytes(randomBytes);
        BigInteger bigInteger = new BigInteger(1, randomBytes);
        String token = bigInteger.toString(16);

        // Ensure the token has the specified length
        while (token.length() < length) {
            token = "0" + token;
        }

        return token;
    }

}