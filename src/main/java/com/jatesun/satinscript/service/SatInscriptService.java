package com.jatesun.satinscript.service;

import com.jatesun.satinscript.Bean.SatinsOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ordinal铭刻服务类
 */
@Service
public class SatInscriptService {
    @Autowired
    private SatinsOrderService satinsOrderService;

    /**
     * 主铭刻方法
     * 1、接收前台传来的file类
     * 2、将file组合为ord协议可以铭刻的类别
     * 3、调用
     *
     * @param orderId
     */
    public void satInscript(String orderId) throws IOException, InterruptedException {
        List<SatinsOrder> satinsOrders = satinsOrderService.getByOrderId(orderId);
        for (SatinsOrder order : satinsOrders) {
            //ord wallet inscribe FILE
            List<String> result = shellCommand(List.of("ord", "wallet", "inscribe", order.getFilePath()));
            // todo 返回结果处理，保存inscribid,
        }
    }

    /**
     * 根据orderId发送客户的ordinals
     *
     * @param orderId
     */
    public void sendInscript(String orderId) throws IOException, InterruptedException {
        List<SatinsOrder> satinsOrders = satinsOrderService.getByOrderId(orderId);
        for (SatinsOrder order : satinsOrders) {
            //ord wallet send --fee-rate 100 satoshi1abcde2fghi3jklm4nopqrs5tuvw6xyz7890 12345
            List<String> result = shellCommand(List.of("ord", "wallet", "send", "--fee-rate", order.getFeeRate().toString(), order.getReceiveAddress(), order.getInscriptionId()));
            //todo 处理后续，修改状态。
        }
    }

    /**
     * 主要的shell脚本方法
     *
     * @return
     */
    public List<String> shellCommand(List<String> command) throws IOException, InterruptedException {
        // 使用ProcessBuilder类创建进程
        ProcessBuilder pb = new ProcessBuilder(command);

        // 将进程的输出流重定向到Java程序中
        pb.redirectErrorStream(true);

        // 启动进程并等待进程执行完成
        Process process = pb.start();

        // 读取进程的输出流
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // 打印进程的输出流
        String line;
        List<String> outPut = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            outPut.add(line);
        }

        // 等待进程执行完成并获取返回值
        int exitCode = process.waitFor();
        System.out.println("Exit code: " + exitCode);
        return outPut;
    }

    public String shellCommandRString(List<String> command) throws IOException, InterruptedException {
        // 使用ProcessBuilder类创建进程
        ProcessBuilder pb = new ProcessBuilder(command);

        // 将进程的输出流重定向到Java程序中
        pb.redirectErrorStream(true);

        // 启动进程并等待进程执行完成
        Process process = pb.start();

        // 读取进程的输出流
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // 打印进程的输出流
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            stringBuilder.append(line);
        }

        // 等待进程执行完成并获取返回值
        int exitCode = process.waitFor();
        System.out.println("Exit code: " + exitCode);
        return stringBuilder.toString();
    }
}
