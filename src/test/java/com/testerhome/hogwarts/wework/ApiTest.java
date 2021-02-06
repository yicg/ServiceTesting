package com.testerhome.hogwarts.wework;

import org.junit.jupiter.api.Test;

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
        api.templateFromYaml("/api/list.yaml",null).then().statusCode(200);
    }
}