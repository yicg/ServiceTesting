package com.testerhome.hogwarts.contact;

import com.testerhome.hogwarts.wework.Restful;
import com.testerhome.hogwarts.wework.Wework;
import com.testerhome.hogwarts.wework.WeworkConfig;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

/**
 * @Author: yicg
 * @Date: 2021/1/26 下午11:28
 * @Version: 1.0
 */
public class Contact extends Restful {

    String random=String.valueOf(System.currentTimeMillis());

    /**
     * 初始化
     */
    public Contact(){
        reset();
    }

    //初始化token
    public void reset(){
        requestSpecification=given()
                .log().all()
                .queryParam("access_token", Wework.getToken())
                .contentType(ContentType.JSON);

    }
}
