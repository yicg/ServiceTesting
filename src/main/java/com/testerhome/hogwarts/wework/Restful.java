package com.testerhome.hogwarts.wework;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

/**
 * @Author: yicg
 * @Date: 2021/1/26 下午11:16
 * @Version: 1.0
 */
public class Restful {

    HashMap<String,Object> query=new HashMap<String, Object>();

    public RequestSpecification requestSpecification=given() ;

    public Response send(){
        requestSpecification=given().log().all();

//        query.entrySet().forEach(entry->{
//            //不停的从map里追加参数进来
//            requestSpecification.queryParam(entry.getKey(),entry.getValue());
//        });

        Set<Map.Entry<String, Object>> entrySet = query.entrySet();
        for (Map.Entry<String, Object> map:entrySet
             ) {
            //不停的从map里遍历并追加到请求体中
            requestSpecification.queryParam(map.getKey(),map.getValue());
        }

        return requestSpecification.when().request("get","http://www.baidu.com");
    }


    /**
     * 抽取公共参数方法
     * @param path  json文件地址
     * @param map   传入的map参数
     * @return
     */
    public static String template(String path,HashMap<String,Object> map){
        DocumentContext documentContext = JsonPath.parse(Restful.class.getResourceAsStream(path));
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        //使用模板技术，通过读取json文件并修改所需要的字段
        for (Map.Entry<String,Object> maps:entrySet){
            documentContext.set(maps.getKey(), maps.getValue());
        }
        return documentContext.jsonString();
    }

    public  Response templateFromHar(String path,String pattern,HashMap<String,Object> map){
        //todo: 支持从har文件中读取接口定义并发送
        //读取请求,进行更新
        DocumentContext documentContext = JsonPath.parse(Restful.class.getResourceAsStream(path));
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        //使用模板技术，通过读取json文件并修改所需要的字段
        for (Map.Entry<String,Object> maps:entrySet){
            documentContext.set(maps.getKey(), maps.getValue());
        }

        String method=documentContext.read("method");
        String url=documentContext.read("url");

        return requestSpecification.when().request(method,url);
    }

    public  Response templateFromSwagger(String path,String pattern,HashMap<String,Object> map){
        //todo 支持从swagger生成接口定义数据
        //读取请求,进行更新
        DocumentContext documentContext = JsonPath.parse(Restful.class.getResourceAsStream(path));
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        //使用模板技术，通过读取json文件并修改所需要的字段
        for (Map.Entry<String,Object> maps:entrySet){
            documentContext.set(maps.getKey(), maps.getValue());
        }

        String method=documentContext.read("method");
        String url=documentContext.read("url");

        return requestSpecification.when().request(method,url);
    }

    //todo :也可以从YML文件中读取等等


}
