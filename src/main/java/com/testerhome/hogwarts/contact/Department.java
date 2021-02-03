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
        return given().log().all()
                .param("access_token", Wework.getToken())
                .param("id",id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().log().all().statusCode(200).extract().response();
    }

    public Response list2(int id){
        Response response= requestSpecification
                .param("id",id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list").then().extract().response();
        reset();
        return response;
    }


    /**
     * 创建部门
     * @return
     */
    public Response creat(String name,String parentid){
        //使用模板技术，通过读取json文件并修改所需要的字段
       String body= JsonPath.parse(this.getClass().getResourceAsStream("/data/create.json"))
                .set(".name",name)
                .set(".parentid",parentid).jsonString();

        return given().log().all().contentType(ContentType.JSON)
                .queryParam("access_token",Wework.getWeworkToken(WeworkConfig.getInstance().contactSecret))
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all().statusCode(200).extract().response();
    }

    /**
     * 封装given
     * @param name
     * @param parentid
     * @return
     */
    public Response creat2(String name,String parentid){
        reset();
        //使用模板技术，通过读取json文件并修改所需要的字段
        String body= JsonPath.parse(this.getClass().getResourceAsStream("/data/create.json"))
                .set(".name",name)
                .set(".parentid",parentid).jsonString();

        return requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all().statusCode(200).extract().response();
    }

    /**
     * 封装参数
     * @param map
     * @return
     */
    public Response creat3(HashMap<String,Object> map){
        reset();
        DocumentContext documentContext = JsonPath.parse(this.getClass().getResourceAsStream("/data/create.json"));
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
         //使用模板技术，通过读取json文件并修改所需要的字段
            for (Map.Entry<String,Object> maps:entrySet){
                documentContext.set(maps.getKey(), maps.getValue());
            }
        return requestSpecification
                .body(documentContext.jsonString())
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all().statusCode(200).extract().response();
    }


    /**
     * 更新部门信息
     * @param id
     * @param name
     * @param parentid
     * @return
     */
    public Response updateDepartment(int id,String name,int parentid){
        String jsonString = JsonPath.parse(this.getClass().getResourceAsStream("/data/updateDepartment.json"))
                .set("$.id", id)
                .set("$.name", name)
                .set("$.parentid", parentid).jsonString();

        return given().queryParam("access_token",Wework.getWeworkToken(WeworkConfig.getInstance().contactSecret))
                .body(jsonString)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().log().all().statusCode(200).extract().response();

    }

    public Response updateDepartment2(int id,String name,int parentid){
        reset();
        String jsonString = JsonPath.parse(this.getClass().getResourceAsStream("/data/updateDepartment.json"))
                .set("$.id", id)
                .set("$.name", name)
                .set("$.parentid", parentid).jsonString();

        return requestSpecification
                .body(jsonString)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().log().all().statusCode(200).extract().response();

    }

    /**
     * 再次运用封装思想
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
                Response response=given().log().all()
                        .queryParam("access_token",Wework.getWeworkToken(WeworkConfig.getInstance().contactSecret))
                        .queryParam("id",id)
                        .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                        .then().log().all().statusCode(200).extract().response();

                return response;

    }

    public Response deleteDepartment2(int id ){
        Response response=requestSpecification
                .queryParam("id",id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().log().all().statusCode(200).extract().response();

        return response;

    }

    /**
     * 删除部门信息,清除帐数据
     * @return
     */
    public Response deleteAll(){
        reset();
        List<Integer> ids= list(0).then().extract().path("department.id");
        System.out.println(ids);
        for (Integer id:ids
             ) {
            deleteDepartment(id);
        }
        return null;
    }
}
