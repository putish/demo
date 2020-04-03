package cn.zucc.demo.util;

/**
 * @description:
 * @author: hjj
 * @create: 2020-02-24 14:53
 */


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValueUtil {

    public static final int EXPIRE_TIME = 3 * 60 * 60 * 1000;
    private static final String TOKEN_SECRET = "emh1YmluZzMxNjAyNDAy";

    // 多线程
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer randomValue = random.nextInt(9000000 ) + 1000000;
        Long timestamp = System.currentTimeMillis();
        return timestamp + String.valueOf(randomValue);
    }

    public static String sign(String username, String remote_add) {
        String ret = "";
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            ret = JWT.create()
                    .withHeader(header)
                    .withClaim("name", username)
                    .withClaim("remote_add", remote_add)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static boolean verify(String token, String username, String remote_addr) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);

            boolean nameMatch = jwt.getClaim("name").asString().equals(username);
            boolean remoteAddrMatch = jwt.getClaim("remote_add").asString().equals(remote_addr);

            return nameMatch && remoteAddrMatch;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean checkTel(String str) throws Exception{
        Pattern pattern = Pattern.compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    public static boolean equals(Double d1, Double d2) {
        return Math.abs(d1 - d2) < 0.001;
    }
}
