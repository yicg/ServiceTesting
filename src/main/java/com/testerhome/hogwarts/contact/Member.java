package com.testerhome.hogwarts.contact;

import io.restassured.response.Response;

import java.util.HashMap;

/**
 * @Author: yicg
 * @Date: 2021/1/24 下午10:22
 * @Version: 1.0
 * 成员
 */
public class Member extends Contact{

    /**
     * 创建成员
     * @param map
     * @return
     */
    public Response createMember(HashMap<String,Object> map){
        String body=template("/data/member.json",map);
        return getDefaultRequestSpecification()
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all().extract().response();
    }
}
