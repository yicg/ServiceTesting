package com.testerhome.hogwarts;

import org.junit.jupiter.api.Test;

import java.net.URL;

/**
 * @Author: yicg
 * @Date: 2021/2/16 下午12:50
 * @Version: 1.0
 */
public class ApiTest {

    @Test
    void rescource(){
        URL url=getClass().getResource("/data/demo.har.json");
        System.out.println(url.getFile());
        System.out.println(url.getPath());
    }
}
