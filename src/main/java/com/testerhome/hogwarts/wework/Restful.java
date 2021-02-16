package com.testerhome.hogwarts.wework;

import java.util.HashMap;

/**
 * @Author: yicg
 * @Date: 2021/2/6 上午10:37
 * @Version: 1.0
 */
public class Restful {
    public String url;
    public String method;
    public HashMap<String,String> heads;
    public HashMap<String,Object> query=new HashMap<>();
    public String body;
}
