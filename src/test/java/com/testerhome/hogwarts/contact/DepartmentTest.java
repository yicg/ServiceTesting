package com.testerhome.hogwarts.contact;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: yicg
 * @Date: 2021/1/24 下午10:34
 * @Version: 1.0
 */
class DepartmentTest {
    String random=String.valueOf(System.currentTimeMillis());
    Department department=new Department();

    /**
     * 获取部门列表
     */
    @Test
    void list() {
        department.list(0).then().statusCode(200).body("department.name[0]",equalTo("测试信息科技有限公司"));  //department.name[0]获取第一个name
        department.list(2).then().statusCode(200).body("department.name[0]",equalTo("研发中心"));  //department.name[0]获取第一个name

    }

    @Test
    void list2() {
        department.list2(0).then().statusCode(200).body("department.name[0]",equalTo("测试信息科技有限公司"));  //department.name[0]获取第一个name

    }

    @Test
    void creat() {
        department.creat("测试部门","1");
    }

    @Test
    void creat3() {
        HashMap<String,Object> map=new HashMap<>();
        map.put("name","测试部门3");
        map.put("parentid","1");
        department.creat3(map);
    }

    @Test
    void updateDepartment() {
        String nameOld="test_department_001"+random;
        department.creat(nameOld,"1").path("depart");
        //获取部门列表
        int id = department.list(0).path("department.find{ it.name=='" + nameOld +"'}.id");
        department.updateDepartment(id,"test_department_002"+random,1);
        //删除部门
        department.deleteDepartment(id).then().body("errcode",equalTo(0));
    }

    @Test
    void deleteDepartment() {
        String nameOld="test_department_001"+random;
        department.creat(nameOld,"1").path("depart");
        //获取部门列表
        int id = department.list(0).path("department.find{ it.name=='" + nameOld +"'}.id");
        department.deleteDepartment(id).then().body("errcode",equalTo(0));
    }

    /**
     * 测试封装后的方法
     */
    @Test
    void updateDepartment2() {
        String nameOld="test_department_001"+random;
        department.creat2(nameOld,"1").path("depart");
        //获取部门列表
        int id = department.list2(0).path("department.find{ it.name=='" + nameOld +"'}.id");
        department.updateDepartment2(id,"test_department_002"+random,1);
        //删除部门
        department.deleteDepartment2(id).then().body("errcode",equalTo(0));
    }

    @Test
    void deleteAll() {
        department.deleteAll();
    }
}