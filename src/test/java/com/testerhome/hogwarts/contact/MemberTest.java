package com.testerhome.hogwarts.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @Author: yicg
 * @Date: 2021/1/27 下午10:57
 * @Version: 1.0
 */
class MemberTest {
    static Member member;

    String random=String.valueOf(System.currentTimeMillis());

    @BeforeEach
     void setUp(){
        member=new Member();
    }

    /**
     * junit5的数据驱动
     */
    @ParameterizedTest
    @ValueSource(strings = { "test1_","test2_","test3_" })
     void createMember(String name) {
        String nameNew=name+random;

        HashMap<String,Object> map=new HashMap<>();
        map.put("userid",nameNew);
        map.put("name",nameNew);
        map.put("department", Arrays.asList(1,2));
        map.put("mobile","153"+random.substring(0,8));
        map.put("email",random+"@qq.com");

        member.createMember(map).then().log().all().statusCode(200).body("errcode",equalTo(0));
    }

    /**
     * junit5的数据驱动,驱动csv文件
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/members.csv")
    void createMember(String name,String alisa) {
        String nameNew=name+random;
        HashMap<String,Object> map=new HashMap<>();
        map.put("userid",nameNew);
        map.put("name",nameNew);
        map.put("alias",alisa);
        map.put("department", Arrays.asList(1,2));
        map.put("mobile","153"+random.substring(0,8));
        map.put("email",random+"@qq.com");

        member.createMember(map).then().log().all().statusCode(200).body("errcode",equalTo(0));
    }
}