package com.xmut.forum.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class BasicUtils {

    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static Timestamp getTime(){
        return new Timestamp(new Date().getTime());
    }
}
