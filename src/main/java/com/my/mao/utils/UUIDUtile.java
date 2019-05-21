package com.my.mao.utils;

import java.util.UUID;

public class UUIDUtile {

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
