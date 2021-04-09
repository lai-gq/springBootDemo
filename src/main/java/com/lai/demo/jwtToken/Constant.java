package com.lai.demo.jwtToken;

import java.util.UUID;

public class Constant {

    public static final String JWT_ID = UUID.randomUUID().toString();//随机数
    public static final String JWT_SECRET = "woyebuzhidaoxiediansha";//加密密文
    public static final int JWT_TTL = 60*60*1000; //有效时长
    public static final String JWT_ISSUER = "testUser";//JWT签发人
}