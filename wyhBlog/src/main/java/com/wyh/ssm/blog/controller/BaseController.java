package com.wyh.ssm.blog.controller;

import com.wyh.ssm.blog.dto.ApiResponse;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;

/**
 * Controller 基类
 */
public abstract class BaseController {

    /**
     * 初始化数据绑定，处理类型转换
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // 字符串处理
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : text.trim());
            }
        });

        // Timestamp 处理
        binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : Timestamp.valueOf(text));
            }
        });
    }
}
