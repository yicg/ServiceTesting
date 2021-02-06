package com.testerhome.hogwarts.wework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: yicg
 * @Date: 2021/2/4 下午11:03
 * @Version: 1.0
 */
class WeworkConfigTest {


    @Test
    void load() {

        WeworkConfig.load("");
    }

    @Test
    void getInstance() {
        WeworkConfig.getInstance();
    }
}