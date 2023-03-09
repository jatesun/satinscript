package com.jatesun.satinscript.service;

import jakarta.annotation.PostConstruct;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Service;

import java.io.File;

//@Service
public class TmpBtcService {

    File walletDir = new File("/Users/jatesun/wallet/testWallet.wallet"); // 钱包文件存储路径
    private Wallet wallet;

    @PostConstruct
    public void init() throws UnreadableWalletException {

    }


    public String getBtcAddress() {
        Address address = wallet.freshReceiveAddress();
        String addressString = address.toString();
        System.out.println("New address: " + addressString);
        return addressString;
    }

//    public String getBtcAddress() {
//        if (wallet == null) {
//            throw new RuntimeException("Wallet is not ready yet");
//        }
//        final Address address = wallet.freshReceiveAddress();
//        return address.toString();
//    }
//        @PostConstruct
//public void initWallet() {
//    NetworkParameters params = TestNet3Params.get(); // 选择测试网络
//    WalletAppKit kit = new WalletAppKit(params, walletDir, walletName) {
//        @Override
//        protected void onSetupCompleted() {
//            // 等待钱包数据准备好
//            wallet = wallet();
//        }
//    };
//    kit.startAsync();
//    kit.awaitRunning();
//    wallet = kit.wallet();
//}
}
