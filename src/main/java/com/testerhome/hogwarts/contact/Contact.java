package com.testerhome.hogwarts.contact;

import com.testerhome.hogwarts.wework.Api;
import com.testerhome.hogwarts.wework.Wework;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

/**
 * @Author: yicg
 * @Date: 2021/1/26 下午11:28
 * @Version: 1.0
 */
public class Contact extends Api {

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

        requestSpecification.filter((req,res,ctx)->{
            //todo 对请求、响应做封装，可以添加请求头等操作
            return ctx.next(req,res);
        });
    }
}
