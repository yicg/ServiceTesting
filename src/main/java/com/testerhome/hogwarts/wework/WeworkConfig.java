package com.testerhome.hogwarts.wework;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;

/**
 * @Author: yicg
 * @Date: 2021/1/24 下午9:30
 * @Version: 1.0
 */
public class WeworkConfig {
    public String AgentId="3010040";
    public String Secret="Ldys9cD8SqpPiePYEaNQqOf-4i1vX_OabNJ8qEsrtCA";
    public String corpid="wwa90325ed46addba3";
    public String contactSecret="7gPOADnNRysIkwhO3br0IGjp_9ekuuZiwLsa4WvUaB4";

    //单例封装有个配置
    private static WeworkConfig weworkConfig;
    public static WeworkConfig getInstance(){
        if(weworkConfig==null){
            //原方法
            //weworkConfig=new WeworkConfig();

            //通过序列化yml文件
            weworkConfig=load("/conf/WeworkConfig.yaml");
            System.out.println(weworkConfig);
            System.out.println(weworkConfig.AgentId);
        }
        return weworkConfig;
    }

    public static WeworkConfig load(String path){
        //从yaml/json文件去读取配置
        //读取yaml
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
           return mapper.readValue(WeworkConfig.class.getResourceAsStream(path),WeworkConfig.class);
            //System.out.println(mapper.writeValueAsString(WeworkConfig.getInstance()));
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

}
