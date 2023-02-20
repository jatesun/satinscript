package com.jatesun.satinscript;

import com.jatesun.satinscript.Bean.SatinsOrder;
import com.jatesun.satinscript.Dao.SatinsOrderMapper;
import com.jatesun.satinscript.service.SatinsOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SatinscriptApplicationTests {
    @Autowired
    private SatinsOrderMapper satinsOrderMapper;
    @Autowired
    private SatinsOrderService satinsOrderService;
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<SatinsOrder> orderList = satinsOrderMapper.selectList(null);
        System.out.println(orderList.size());
    }

    @Test
    public void testOrderService() {
        List<SatinsOrder> orderList = satinsOrderService.getByTransId("1");
        System.out.println(orderList.size());
    }

    @Test
    public void testdeleteService() {
         satinsOrderService.deleteByTransId("1");
    }

    @Test
    void contextLoads() {
    }

}
