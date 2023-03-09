package com.jatesun.satinscript.service;

import jakarta.annotation.PostConstruct;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * 比特币相关服务类
 */
@Service
public class BtcTranService {
    File walletDir = new File("/Users/jatesun/wallet/testWallet.wallet"); // 钱包文件存储路径
    private Wallet wallet;

    @PostConstruct
    public void initWallet() throws UnreadableWalletException {
        wallet = Wallet.loadFromFile(walletDir);
//        NetworkParameters params = TestNet3Params.get(); // 选择测试网络
//        WalletAppKit kit = new WalletAppKit(params, walletDir, walletName);
//        kit.startAsync();
//        kit.awaitRunning();
//        wallet = kit.wallet();
    }

    public String getBtcAddress() {
        Address address = wallet.freshReceiveAddress();
        String addressString = address.toString();
        System.out.println("New address: " + addressString);
        return addressString;
    }

//    @PostConstruct
//    public void initWallet() {
//        NetworkParameters params = TestNet3Params.get(); // 选择测试网络
//        walletAppKit = new WalletAppKit(params, walletDir, walletName) {
//            @Override
//            protected void onSetupCompleted() {
//                super.onSetupCompleted();
//                wallet = this.wallet();
////                // 生成新的比特币地址
////                final Address address = wallet.freshReceiveAddress();
////                System.out.println("address:" + address);
////                log.info("New Bitcoin Address: " + address.toString());
//
////                // 监听比特币支付通知
////                wallet.addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
////                    @Override
////                    public void onCoinsReceived(final Wallet wallet, final Transaction tx, final Coin prevBalance, final Coin newBalance) {
////                        final List<TransactionOutput> outputs = tx.getOutputs();
////                        for (TransactionOutput output : outputs) {
////                            if (output.isMineOrWatched(wallet)) {
////                                final Coin value = output.getValue();
////                                log.info("Payment Received: " + value.toFriendlyString());
////                                System.out.println("payment received:" + value.toFriendlyString());
////                                // TODO: 处理比特币支付通知
////                            }
////                        }
////                    }
////                });
//            }
//        };
//        walletAppKit.startAsync();
//        walletAppKit.awaitRunning();
//    }


}
