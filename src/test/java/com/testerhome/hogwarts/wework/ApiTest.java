package com.testerhome.hogwarts.wework;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: yicg
 * @Date: 2021/2/6 上午10:52
 * @Version: 1.0
 */
class ApiTest {

    @Test
    void templateFromYaml() {
        Api api=new Api();
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",1);
        api.templateFromYaml("/api/list.yaml",map).then().statusCode(200);
    }
}