package com.jatesun.satinscript.service;

import com.jatesun.satinscript.bean.SatinsOrder;
import com.jatesun.satinscript.dao.SatinsOrderMapper;
import jakarta.annotation.PostConstruct;
import org.bitcoinj.core.Sha256Hash;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bitcoin core wallet java service
 */
@Service
public class BtcCoreService {
    @Value("${btc.rpc.user}")
    private String user;
    @Value("${btc.rpc.password}")
    private String password;
    @Value("${btc.rpc.host}")
    private String host;
    @Value("${btc.rpc.port}")
    private String port;
    @Value("${btc.rpc.wallet}")
    private String wallet;
    private BitcoinJSONRPCClient client;
    private final ZContext zmqContext = new ZContext();
    private final ZMQ.Socket subscriber = zmqContext.createSocket(ZMQ.SUB);
    @Autowired
    private SatinsOrderMapper satinsOrderMapper;
    @Autowired
    private SatInscriptService satInscriptService;

    @PostConstruct
    public void init() {
        try {
            client = new BitcoinJSONRPCClient(getConnectUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        subscriber.connect("tcp://127.0.0.1:28332");
        subscriber.subscribe("rawtx".getBytes());
//        new Thread(this::listenReceivedBtc).start();
    }

    /**
     * getAddress
     * 获取taproot地址
     *
     * @return
     */
    public String getAddress() {
        String address = client.getNewAddress("", "bech32m");
        return address;
    }

    @Scheduled(fixedRate = 300000)//五分钟执行一次
    public void listenAddressReceivedBtc() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("pay_status", false);
        List<SatinsOrder> orderList = satinsOrderMapper.selectByMap(queryMap);
        for (SatinsOrder order : orderList) {
            BigDecimal decimal = client.getReceivedByAddress(order.getPayAddress(), 1);
            if (decimal.doubleValue() >= order.getTotalFee()) {
                //支付完成，准备inscribe
                try {
                    satInscriptService.sendInscribeByOrder(order);
                } catch (Exception e) {
                    System.out.println("inscibe失败" + e.getMessage());
                }
            } else {
                System.out.println("监听支付（listenAddressReceivedBtc）：金额不对，用户支付金额少于订单金额");
            }
        }
//        for (SatinsOrder order : orderList) {
//            System.out.println(order.getOrderId());
//        }
    }

    /**
     * 监听比特币支付的回调函数
     */
    private void listenReceivedBtc() {
        String address = "";
        while (!Thread.currentThread().isInterrupted()) {
            ZMsg msg = ZMsg.recvMsg(subscriber);
            if (msg == null) {
                break;
            }
            String txHex = msg.getLast().toString();
            String txHash = client.sendRawTransaction(txHex);
            Sha256Hash txid = Sha256Hash.wrap(txHash);
            BitcoindRpcClient.Transaction transaction = client.getTransaction(txid.toString());
            System.out.println(transaction.address());
//            System.out.println(msg.getLast().toString());
//            String txHex = msg.getLast().toString();
//            BitcoindRpcClient.RawTransaction rawTransaction = client.decodeRawTransaction(txHex);
//            List<String> address = rawTransaction.vOut().size();
//            System.out.println("交易大小：" + rawTransaction.vOut().size());
//            System.out.println("txid:" + txid);
            msg.destroy();
        }
    }

    private String getConnectUrl() {
        String url = "http://" + user + ":" + password + "@" + host + ":" + port + "/wallet/" + wallet + "";
        return url;
    }
}
