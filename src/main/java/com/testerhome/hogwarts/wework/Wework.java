package com.testerhome.hogwarts.wework;

import io.restassured.RestAssured;


/**
 * @Author: yicg
 * @Date: 2021/1/24 下午10:08
 * @Version: 1.0
 */
public class Wework {

    private static String token;

    public static String getWeworkToken(String secret){
       return RestAssured.given().log().all()
                .queryParam("corpid", WeworkConfig.getInstance().corpid)
                .queryParam("corpsecret", secret)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all().statusCode(200)
                .extract().path("access_token");//取出access_token
    }

    /**
     * 通讯录的token
     * @return
     */
    public static String getWeworkTokenForContact(){

        return null;
    }

    /**
     * 存储token
     * @return
     */
    public static String getToken(){
        // todo 支持两种类型的token
        if(token==null){
            token=getWeworkToken(WeworkConfig.getInstance().contactSecret);
        }
        return token;
    }
}
