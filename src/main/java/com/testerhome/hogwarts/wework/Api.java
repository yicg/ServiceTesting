package com.testerhome.hogwarts.wework;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

/**
 * @Author: yicg
 * @Date: 2021/1/26 下午11:16
 * @Version: 1.0
 */
public class Api {

    HashMap<String,Object> query=new HashMap<String, Object>();

    public Api(){
        useRelaxedHTTPSValidation();
    }

    public RequestSpecification getDefaultRequestSpecification() {
        return given().log().all();
    }


    /**
     * 抽取公共参数方法
     * @param path  json文件地址
     * @param map   传入的map参数
     * @return
     */
    public static String template(String path,HashMap<String,Object> map){
        DocumentContext documentContext = JsonPath.parse(Api.class.getResourceAsStream(path));
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
        DocumentContext documentContext = JsonPath.parse(Api.class.getResourceAsStream(path));
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        //使用模板技术，通过读取json文件并修改所需要的字段
        for (Map.Entry<String,Object> maps:entrySet){
            documentContext.set(maps.getKey(), maps.getValue());
        }

        String method=documentContext.read("method");
        String url=documentContext.read("url");

        return getDefaultRequestSpecification().when().request(method,url);
    }

    public  Response templateFromSwagger(String path,String pattern,HashMap<String,Object> map){
        //todo 支持从swagger生成接口定义数据
        //读取请求,进行更新
        DocumentContext documentContext = JsonPath.parse(Api.class.getResourceAsStream(path));
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        //使用模板技术，通过读取json文件并修改所需要的字段
        for (Map.Entry<String,Object> maps:entrySet){
            documentContext.set(maps.getKey(), maps.getValue());
        }

        String method=documentContext.read("method");
        String url=documentContext.read("url");

        return getDefaultRequestSpecification().when().request(method,url);
    }

    public Response templateFromYaml(String path,HashMap<String,Object> map){
        //读取yaml
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            Restful restful= mapper.readValue(Restful.class.getResourceAsStream(path),Restful.class);
            //请求参数为get时
            if(restful.method.toLowerCase().contains("get")){
                //把输入的map替换到get请求参数中
                map.entrySet().forEach(entry->{
                    restful.query.replace(entry.getKey(),entry.getValue());
                });
            }
            // 请求参数为post时
            if(restful.method.toLowerCase().contains("post")){
                if(map.containsKey("_body")){
                    restful.body=map.get("_body").toString();
                }
                if(map.containsKey("_file")){
                    String filePath=map.get("_file").toString();
                    map.remove("_file");
                    restful.body=template(filePath,map);
                }
            }
            RequestSpecification requestSpecification=getDefaultRequestSpecification();

            if(restful.query !=null){
                restful.query.entrySet().forEach(entry->{
                    requestSpecification.queryParam(entry.getKey(),entry.getValue());
                });
            }
            if(restful.body !=null){
                requestSpecification.body(restful.body);
            }
            return requestSpecification.log().all()
                    .when().request(restful.method,restful.url)
                    .then().log().all().extract().response();

        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public Restful getApiFromYaml(String path) {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(WeworkConfig.class.getResourceAsStream(path), Restful.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Restful updateApiFromMap(Restful restful,HashMap<String,Object> map){
            if(map==null){
                return restful;
            }
            //请求参数为get时
            if(restful.method.toLowerCase().contains("get")){
                //把输入的map替换到get请求参数中
                map.entrySet().forEach(entry->{
                    restful.query.replace(entry.getKey(),entry.getValue());
                });
            }
            // 请求参数为post时
            if(restful.method.toLowerCase().contains("post")){
                if(map.containsKey("_body")){
                    restful.body=map.get("_body").toString();
                }
                if(map.containsKey("_file")){
                    String filePath=map.get("_file").toString();
                    map.remove("_file");
                    restful.body=template(filePath,map);
                }
            }
            return restful;
    }

    public Response getResponseFromRestful(Restful restful) {

        RequestSpecification requestSpecification = getDefaultRequestSpecification();

        //System.out.println(mapper.writeValueAsString(WeworkConfig.getInstance()));
        if (restful.query != null) {
            restful.query.entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }

        if (restful.body != null) {
            requestSpecification.body(restful.body);
        }
        String[] url=updateUrl(restful.url);
        return requestSpecification.log().all()
                .when().request(restful.method, restful.url)
                .then().log().all()
                .extract().response();


    }

    public Response getResponseFromYaml(String path, HashMap<String, Object> map) {
        //fixed: 根据yaml生成接口定义并发送
        Restful restful = getApiFromYaml(path);
        restful = updateApiFromMap(restful, map);

        RequestSpecification requestSpecification = getDefaultRequestSpecification();

        if (restful.query != null) {
            restful.query.entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }

        if (restful.body != null) {
            requestSpecification.body(restful.body);
        }
        String[] url=updateUrl(restful.url);

        return requestSpecification.log().all()
                .header("Host", url[0])
                .when().request(restful.method, url[1])
                .then().log().all()
                .extract().response();

    }


    private String[] updateUrl(String url) {
        //fixed: 多环境支持，替换url，更新host的header
        HashMap<String, String> hosts=WeworkConfig.getInstance().env.get(WeworkConfig.getInstance().current);

        String host="";
        String urlNew="";
        for(Map.Entry<String, String> entry : hosts.entrySet()){
            if(url.contains(entry.getKey())){
                host=entry.getKey();
                urlNew=url.replace(entry.getKey(), entry.getValue());
            }
        }

        return new String[]{host, urlNew};



    }
    //todo: 支持wsdl soap

    public Response readApiFromYaml(String path, HashMap<String, Object> map) {
        //todo: 动态调用
        return null;
    }


}
