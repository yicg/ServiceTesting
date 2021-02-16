package com.testerhome.hogwarts.contact;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.testerhome.hogwarts.wework.Wework;
import com.testerhome.hogwarts.wework.WeworkConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

/**
 * @Author: yicg
 * @Date: 2021/1/24 下午10:22
 * @Version: 1.0
 * 部门管理
 */
public class Department extends Contact{
    /**
     * 获取部门列表
     * @param id
     * @return
     */
    public Response list(int id){
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",id);
        return templateFromYaml("/api/list.yaml",map);
    }

    public Response list2(int id){
        Response response= getDefaultRequestSpecification()
                .param("id",id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list").then().extract().response();
        return response;
    }


    /**
     * 创建部门
     * @return
     */
    public Response creat(String name,String parentid){
        //使用模板技术，通过读取json文件并修改所需要的字段
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("name",name);
        hashMap.put("parentid",parentid);
        hashMap.put("_file","/data/create.json");

        return templateFromYaml("/api/create.yaml",hashMap);

    }

    public Response creat(HashMap<String,Object> map){
        map.put("_file","/data/create.json");

        return templateFromYaml("/api/create.yaml",map);

    }

    /**
     * 更新部门信息
     * @param id
     * @param name
     * @param parentid
     * @return
     */
    public Response updateDepartment(int id,String name,int parentid){
       HashMap<String,Object> hashMap=new HashMap<>();
       hashMap.put("_file","/data/updateDepartment.json");
       hashMap.put("name",name);
       hashMap.put("id",id);
       hashMap.put("parentid",parentid);

       return templateFromYaml("/api/update.yaml",hashMap);

    }


    /**
     * 再次运用封装思想,从har文件里读取
     * @param map
     * @return
     */
    public Response updateDepartment3(HashMap<String,Object> map){
      return templateFromHar("demo.har.json","https://work.weixin.qq.com/wework_admin/party?action=addparty",map);

    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    public Response deleteDepartment(int id ){
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("id",id);
        return templateFromYaml("/api/delete.yaml",hashMap);

    }

    /**
     * 删除部门信息,清除帐数据
     * @return
     */
    public Response deleteAll(){
        List<Integer> ids= list(0).then().extract().path("department.id");
        System.out.println(ids);
        for (Integer id:ids
             ) {
            deleteDepartment(id);
        }
        return null;
    }
}
