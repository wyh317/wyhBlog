package com.wyh.ssm.blog.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MyUtils 工具类测试
 */
@DisplayName("MyUtils 工具类测试")
class MyUtilsTest {

    @Test
    @DisplayName("MD5 加密 - 32 位")
    void strToMd5_32bit() {
        String result = MyUtils.strToMd5("123456");
        assertEquals("e10adc3949ba59abbe56e057f20f883e", result);
    }

    @Test
    @DisplayName("MD5 加密 - 空字符串")
    void strToMd5_EmptyString() {
        String result = MyUtils.strToMd5("");
        assertNull(result);
    }

    @Test
    @DisplayName("MD5 加密 - null")
    void strToMd5_Null() {
        String result = MyUtils.strToMd5(null);
        assertNull(result);
    }

    @Test
    @DisplayName("MD5 加密 - 中文字符")
    void strToMd5_Chinese() {
        String result = MyUtils.strToMd5("测试");
        assertNotNull(result);
        assertEquals(32, result.length());
    }

    @Test
    @DisplayName("MD5 加密 - 相同输入相同输出")
    void strToMd5_SameInputSameOutput() {
        String result1 = MyUtils.strToMd5("password");
        String result2 = MyUtils.strToMd5("password");
        assertEquals(result1, result2);
    }

    @Test
    @DisplayName("MD5 加密 - 不同输入不同输出")
    void strToMd5_DifferentInputDifferentOutput() {
        String result1 = MyUtils.strToMd5("password1");
        String result2 = MyUtils.strToMd5("password2");
        assertNotEquals(result1, result2);
    }
}
