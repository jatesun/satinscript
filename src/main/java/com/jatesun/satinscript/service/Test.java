package com.jatesun.satinscript.service;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Throwable {
//        String rpcuser = "jatesun";
//        String rpcpassword = "111Jatesun";
        // 连接到第一个钱包
//        String rpcUrl1 = "http://" + rpcuser + ":" + rpcpassword + "@localhost:8332";
        Test test = new Test();
        JsonRpcHttpClient client1 = test.getClient();
//        client1.invoke("getblockchaininfo", new Object[]{}, Map.class);
        client1.invoke("getbalance", new Object[]{}, Double.class);
//        JsonRpcHttpClient client1 = new JsonRpcHttpClient(new URL(rpcUrl1));
//        String address1 = client1.invoke("getnewaddress", new Object[]{}, String.class);
//        String address1 = (String) client1.invoke("getnewaddress", new Object[]{"", "legacy"}, Object.class).toString();
//        client1.invoke("getblockchaininfo", new Object[]{}, Map.class);
//        System.out.println("New address for wallet 1: " + address1);
//        String walletFile = "/path/to/wallet.dat";
    }


    public JsonRpcHttpClient getFreeClient() throws MalformedURLException {
        String rpcurl = "http://localhost:8332/";
        JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(rpcurl));
//        client.seta
//        client.setHostNameVerifier();
        return client;
    }

    //    // 比特币身份认证
    public JsonRpcHttpClient getClient() {
        String rpcuser = "__cookie__";
        String rpcpassword = "354d783668ad944331aea9d4cacc8adb02d619622197283035b035fb8c1a1cad";
        // 连接到第一个钱包
//        String rpcUrl1 = "http://" + rpcuser + ":" + rpcpassword + "@localhost:8332";
        JsonRpcHttpClient client = null;
        try {
//            String cred = Base64.encodeBytes((rpcuser + ":" + rpcpassword).getBytes());
            String cred = Base64.getEncoder().encodeToString((rpcuser + ":" + rpcpassword).getBytes());
            Map<String, String> headers = new HashMap<String, String>(1);
            headers.put("Authorization", "Basic " + cred);
            client = new JsonRpcHttpClient(new URL("http://" + "localhost" + ":" + "8332/wallet/ord"), headers);
//            client = new JsonRpcHttpClient(new URL("http://" + "localhost" + ":" + "8332"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }
}
