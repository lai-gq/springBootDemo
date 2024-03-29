package com.lai.demo.jwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public SecretKey generalKey() {
        String stringKey = Constant.JWT_SECRET;
        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建token
     * @param claims  需要存放在token的数据
     * @param subject 用户
     * @return
     * @throws Exception
     */
    public String createJWT(Map<String, Object> claims, String subject) throws Exception {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //设置JWT过期时间
        long expMillis = nowMillis + Constant.JWT_TTL;
        Date exp = new Date(expMillis);
        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        SecretKey key = generalKey();

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(Constant.JWT_ID)         // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)              // iat: jwt的签发时间
                .setExpiration(exp)           //设置过期时间
                .setIssuer(Constant.JWT_ISSUER) // issuer：jwt签发人
                .setSubject(subject)        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key); // 设置签名使用的签名算法和签名使用的秘钥
        return builder.compact();
    }

    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(key)                 //设置签名的秘钥
                .parseClaimsJws(jwt).getBody();     //设置需要解析的jwt
        return claims;
    }

    public static void main(String[] args) {
        Map<String, Object> claims = new HashMap<>(3);
        claims.put("userID","123456");
        claims.put("user","风雨兼程");
        claims.put("password","123");
        String subject ="lgq";//subject
        try {
            JwtUtil util = new JwtUtil();
            String jwt = util.createJWT(claims,subject);
            System.out.println("生成的JWT：" + jwt);

            System.out.println("\n解密：\n");
            Claims c = util.parseJWT(jwt);
            System.out.println("JWT的唯一标识:"+c.getId());
            System.out.println("JWT的签发时间:"+c.getIssuedAt());
            System.out.println("JWT的主体:"+c.getSubject());
            System.out.println("JWT签发人:"+c.getIssuer());
            System.out.println("JWT的过期时间:"+c.getExpiration());
            System.out.println("JWT的Claims（payload部分）:"+c.get("userID"));
            System.out.println("JWT的Claims（payload部分）:"+c.get("user"));
            System.out.println("JWT的Claims（payload部分）:"+c.get("password"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}