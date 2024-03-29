package com.jatesun.satinscript.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class SatinsOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String transId;

    private String orderId;

    private String inscriptionId;

    private String filePath;

    private Double transFee;

    private Integer feeRate;

    private Double serviceFee;

    private Double totalFee;

    private Boolean payStatus;

    private Boolean sendStatus;

    private String receiveAddress;//客户接收的地址

    private String payAddress;//用户需要支付的地址
}
