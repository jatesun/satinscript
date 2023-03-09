package com.jatesun.satinscript.controller;

import com.jatesun.satinscript.bean.SatinsOrder;
import com.jatesun.satinscript.dto.PayData;
import com.jatesun.satinscript.service.BtcTranService;
import com.jatesun.satinscript.service.Greeting;
import com.jatesun.satinscript.service.SatinsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class SatinscriptController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private SatinsOrderService satinsOrderService;
//    @Autowired
//    private BtcTranService btcTranService;

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/getOrder")
    @CrossOrigin
    public List<SatinsOrder> getByOrderId(@RequestParam(value = "orderId", defaultValue = "") String orderId) {
        return satinsOrderService.getByOrderId(orderId);
    }

//    @GetMapping("/getBtcAddress")
//    @CrossOrigin
//    public String getBtcAddress(@RequestParam(value = "orderId", defaultValue = "") String orderId) {
//        System.out.println("调用");
//        PayData payData = new PayData();
//        payData.setReceiveAddress(btcTranService.getBtcAddress());
//        return payData.getReceiveAddress();
//    }
}
