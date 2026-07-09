package com.codingshuttle.razorpay.common.utils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class RandomizerUtils {

    // UUID = 32 character random

  private final static   SecureRandom SECURED_RANDOM = new SecureRandom();

    public static String randomBase64(int length){
        //UUID.randomUUID().toString().replace("-","");

    byte[] buff = new byte[length];
        SECURED_RANDOM.nextBytes(buff);
       // [1,3,-4,11]   { -128 , 127 }
    return Base64.getUrlEncoder().withoutPadding().encodeToString(buff);
    }
}
