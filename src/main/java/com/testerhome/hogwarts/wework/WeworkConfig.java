package com.testerhome.hogwarts.wework;

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
            weworkConfig=new WeworkConfig();
        }
        return weworkConfig;
    }

    public static void load(String path){
        //todo 从yaml/json文件去读取配置
    }

}
