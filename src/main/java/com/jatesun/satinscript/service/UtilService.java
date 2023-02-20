package com.jatesun.satinscript.service;

import org.springframework.stereotype.Component;

/**
 * 工具服务
 */
@Component
public class UtilService {

    /**
     * 生成八位随机数
     * @param len
     * @return
     */
    public String getRandom(int len) {
        int rs = (int) ((Math.random() * 9 + 1) * Math.pow(10, len - 1));
        return String.valueOf(rs);
    }

}
