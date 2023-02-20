package com.jatesun.satinscript;

import com.jatesun.satinscript.Bean.SatinsOrder;
import com.jatesun.satinscript.Dao.SatinsOrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SatinscriptApplicationTests {
    @Autowired
    private SatinsOrderMapper satinsOrderMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<SatinsOrder> orderList = satinsOrderMapper.selectList(null);
        System.out.println(orderList.size());
    }

    @Test
    void contextLoads() {
    }

}
