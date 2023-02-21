package com.jatesun.satinscript.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

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

    public Map<String,Object> parseJsonToMap(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(jsonString, Map.class);
        return map;
    }

}
