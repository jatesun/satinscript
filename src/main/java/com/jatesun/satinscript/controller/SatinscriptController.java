package com.jatesun.satinscript.controller;

import com.jatesun.satinscript.bean.SatinsOrder;
import com.jatesun.satinscript.dto.PayData;
import com.jatesun.satinscript.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class SatinscriptController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private SatinsOrderService satinsOrderService;
    @Autowired
    private BtcTranService btcTranService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private BtcCoreService btcCoreService;

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

    @GetMapping("/getBtcAddress")
    @CrossOrigin
    public String getBtcAddress(@RequestParam(value = "payAmount", defaultValue = "") Double payAmount,
                                @RequestParam(value = "network", defaultValue = "") Double network,
                                @RequestParam(value = "service", defaultValue = "") Double service,
                                @RequestParam(value = "fileSize", defaultValue = "") Long fileSize,
                                @RequestParam(value = "feeRate", defaultValue = "") Integer feeRate,
                                @RequestParam(value = "sessionId", defaultValue = "") String sessionId,
                                @RequestParam(value = "receiveAddress", defaultValue = "") String receiveAddress,
                                HttpServletRequest request) {
        System.out.println("参数：payamount:" + payAmount + ",filesize:" + fileSize + ",sessionId:" + sessionId + ",feerate:" + feeRate + "," + network + "," + service + "," + receiveAddress);
        Long innerFileSize = uploadService.getFileSizeBySessionId(sessionId);
        System.out.println("innersize:" + innerFileSize);
        if (innerFileSize.equals(fileSize)) {
            String payAddress = btcCoreService.getAddress();
            String orderId = satinsOrderService.saveNewOrderInfo(feeRate, payAmount, service, fileSize, sessionId, receiveAddress,payAddress);
            System.out.println("orderId:" + orderId);
            return payAddress + "," + orderId;
        } else {
            return "illegal request,please fresh website and retry.";
        }

    }
}
