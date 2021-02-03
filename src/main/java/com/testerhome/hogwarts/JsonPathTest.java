package com.testerhome.hogwarts;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.util.List;


/**
 * @Author: yicg
 * @Date: 2021/1/29 下午11:06
 * @Version: 1.0
 */
public class JsonPathTest {

    /**
     *1. $.store.book[*].author  获取store下的所有book下的author的值：["Nigel Rees","Evelyn Waugh","Herman Melville","J. R. R. Tolkien"]
     *2. $.store.book[0].author  获取store下的第一个book下的所有author的值：Nigel Rees
     *3. $..author 获取递归所有的author的值：["Nigel Rees","Evelyn Waugh","Herman Melville","J. R. R. Tolkien"]
     *4. $.store.* 获取store下所有子节点的值：[[{"category":"reference","author":"Nigel Rees","title":"Sayings of the Century","price":8.95},{"category":"fiction","author":"Evelyn Waugh","title":"Sword of Honour","price":12.99},{"category":"fiction","author":"Herman Melville","title":"Moby Dick","isbn":"0-553-21311-3","price":8.99},{"category":"fiction","author":"J. R. R. Tolkien","title":"The Lord of the Rings","isbn":"0-395-19395-8","price":22.99}],{"color":"red","price":19.95}]
     *5. $.store..price 获取store下的所有价格：[8.95,12.99,8.99,22.99,19.95]
     *6. $..book[2] 获取第三个book对象的值：[{"category":"fiction","author":"Herman Melville","title":"Moby Dick","isbn":"0-553-21311-3","price":8.99}]
     *7. $..book[-1] 获取倒数第一个book的对象值：[{"category":"fiction","author":"J. R. R. Tolkien","title":"The Lord of the Rings","isbn":"0-395-19395-8","price":22.99}]
     *8. $..book[0,1] 获取前两个book的对象值：[{"category":"reference","author":"Nigel Rees","title":"Sayings of the Century","price":8.95},{"category":"fiction","author":"Evelyn Waugh","title":"Sword of Honour","price":12.99}]
     *9. $..book[:2] 获取下标从0到1的book的对象值，不包含下标2： [{"category":"reference","author":"Nigel Rees","title":"Sayings of the Century","price":8.95},{"category":"fiction","author":"Evelyn Waugh","title":"Sword of Honour","price":12.99}]
     *10. $..book[1:2] 获取下标为1到1的book的对象值，即只去下标为1的对象： [{"category":"fiction","author":"Evelyn Waugh","title":"Sword of Honour","price":12.99}]
     *11. $..book[-2:] 获取最后两个book的对象值：[{"category":"fiction","author":"Herman Melville","title":"Moby Dick","isbn":"0-553-21311-3","price":8.99},{"category":"fiction","author":"J. R. R. Tolkien","title":"The Lord of the Rings","isbn":"0-395-19395-8","price":22.99}]
     *12. $..book[1:]  获取booke中从下标为1开始的所有对象： [{"category":"fiction","author":"Evelyn Waugh","title":"Sword of Honour","price":12.99},{"category":"fiction","author":"Herman Melville","title":"Moby Dick","isbn":"0-553-21311-3","price":8.99},{"category":"fiction","author":"J. R. R. Tolkien","title":"The Lord of the Rings","isbn":"0-395-19395-8","price":22.99}]
     *13. $..book[?(@.isbn)] 获取book中包含有isbn元素的所有对象： [{"category":"fiction","author":"Herman Melville","title":"Moby Dick","isbn":"0-553-21311-3","price":8.99},{"category":"fiction","author":"J. R. R. Tolkien","title":"The Lord of the Rings","isbn":"0-395-19395-8","price":22.99}]
     *14. $.store.book[?(@.price < 10)] 获取store下的book对象中price<10的元素： [{"category":"reference","author":"Nigel Rees","title":"Sayings of the Century","price":8.95},{"category":"fiction","author":"Herman Melville","title":"Moby Dick","isbn":"0-553-21311-3","price":8.99}]
     *15. $..book[?(@.price <= $['expensive'])] 获取book下所有对象中的price的价格<=根目录下expensive的值的所有元素： [{"category":"reference","author":"Nigel Rees","title":"Sayings of the Century","price":8.95},{"category":"fiction","author":"Herman Melville","title":"Moby Dick","isbn":"0-553-21311-3","price":8.99}]
     *16. $..book[?(@.author =~ /.*REES/i)] 获取booke下所有对象中author名字匹配到*REES的所有元素（不区分大小写）： [{"category":"reference","author":"Nigel Rees","title":"Sayings of the Century","price":8.95}]
     * @param args
     */
    public static void main(String[] args) {
        String json="{\"store\":{\"book\":[{\"category\":\"reference\",\"author\":\"Nigel Rees\",\"title\":\"Sayings of the Century\",\"price\":8.95},{\"category\":\"fiction\",\"author\":\"Evelyn Waugh\",\"title\":\"Sword of Honour\",\"price\":12.99},{\"category\":\"fiction\",\"author\":\"Herman Melville\",\"title\":\"Moby Dick\",\"isbn\":\"0-553-21311-3\",\"price\":8.99},{\"category\":\"fiction\",\"author\":\"J. R. R. Tolkien\",\"title\":\"The Lord of the Rings\",\"isbn\":\"0-395-19395-8\",\"price\":22.99}],\"bicycle\":{\"color\":\"red\",\"price\":19.95}},\"expensive\":10}";

        DocumentContext parse = JsonPath.parse(json);
        List a1 = parse.read("$.store.book[*].author");
        System.out.println(a1);
        String a2 = parse.read("$.store.book[0].author");
        System.out.println(a2);
        List a3 = parse.read("$..author");
        System.out.println(a3);
        List a4 = parse.read("$.store.*");
        System.out.println(a4);
        List a5=parse.read("$.store..price");
        System.out.println(a5);
        List a6=parse.read("$..book[2]");
        System.out.println(a6);
        List a7=parse.read("$..book[-1]");
        System.out.println(a7);
        List a8=parse.read("$..book[0,1]");
        System.out.println(a8);
        List a9=parse.read("$..book[:2]");
        System.out.println(a9);
        List a10=parse.read("$..book[1:2]");
        System.out.println(a10);
        List a11=parse.read("$..book[-2:]");
        System.out.println(a11);
        List a12=parse.read("$..book[1:]");
        System.out.println(a12);
        List a13=parse.read("$..book[?(@.isbn)]");
        System.out.println(a13);
        List a14=parse.read("$.store.book[?(@.price < 10)]");
        System.out.println(a14);
        List a15=parse.read("$..book[?(@.price <= $['expensive'])]");
        System.out.println(a15);
        List a16=parse.read("$..book[?(@.author =~ /.*REES/i)]");
        System.out.println(a16);

    }
}
