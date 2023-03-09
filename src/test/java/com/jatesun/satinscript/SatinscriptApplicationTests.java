package com.jatesun.satinscript;

import com.jatesun.satinscript.bean.SatinsOrder;
import com.jatesun.satinscript.dao.SatinsOrderMapper;
import com.jatesun.satinscript.service.BtcTranService;
import com.jatesun.satinscript.service.SatInscriptService;
import com.jatesun.satinscript.service.SatinsOrderService;
import com.jatesun.satinscript.service.UtilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SatinscriptApplicationTests {
    @Autowired
    private SatinsOrderMapper satinsOrderMapper;
    @Autowired
    private SatinsOrderService satinsOrderService;
    @Autowired
    private SatInscriptService satInscriptService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private BtcTranService btcTranService;

    @Test
    public void testGetBtcAddress() {
        btcTranService.getBtcAddress();
    }

    @Test
    public void testInscriptService() throws IOException, InterruptedException {
        //curl -s https://jsonplaceholder.typicode.com/todos/1
//        List<String> result = satInscriptService.shellCommand(List.of("java", "--version"));
//        List<String> result = satInscriptService.shellCommand(List.of("curl", "-s","https://jsonplaceholder.typicode.com/todos/1"));
        String result = satInscriptService.shellCommandRString(List.of("curl", "-s", "https://jsonplaceholder.typicode.com/todos/1"));
        Map<String, Object> mapresult = utilService.parseJsonToMap(result);
        for (String key : mapresult.keySet()) {
            System.out.println(key + " : " + mapresult.get(key));
        }

    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<SatinsOrder> orderList = satinsOrderMapper.selectList(null);
        System.out.println(orderList.get(0).toString());
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
