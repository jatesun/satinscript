package com.jatesun.satinscript.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;

import java.math.BigDecimal;
import java.net.MalformedURLException;

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

    @PostConstruct
    public void init() {
        try {
            client = new BitcoinJSONRPCClient(getConnectUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * getAddress
     *
     * @return
     */
    public void getAddress() {
        BigDecimal balance = client.getBalance();
        System.out.println(balance);
    }

    private String getConnectUrl() {
        String url = "http://" + user + ":" + password + "@" + host + ":" + port + "/wallet/" + wallet + "";
        return url;
    }
}
