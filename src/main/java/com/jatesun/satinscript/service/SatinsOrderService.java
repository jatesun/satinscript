package com.jatesun.satinscript.service;

import com.jatesun.satinscript.bean.SatinsOrder;
import com.jatesun.satinscript.dao.SatinsOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 订单服务类
 * 处理需要铭刻的订单，根据状态决定下一步相关操作
 */
@Service
public class SatinsOrderService {
    @Autowired
    private SatinsOrderMapper satinsOrderMapper;
    @Autowired
    private UploadService uploadService;

    /**
     * 新增或修改
     *
     * @param order
     */
    public void save(SatinsOrder order) {
        if (order.getId() == null || order.getId() == 0) {
            satinsOrderMapper.insert(order);
        } else {
            satinsOrderMapper.updateById(order);
        }
    }

    public String saveNewOrderInfo(Integer feeRate, Double payAmount,Double serviceFee, Long fileSize, String sessionId,String receiveAddress,String payAddress) {
        String orderId = UUID.randomUUID().toString();
        List<File> fileList = uploadService.getFileBySessionId(sessionId);
        for (File file : fileList) {
            SatinsOrder order = new SatinsOrder();
            order.setOrderId(orderId);
            order.setFeeRate(feeRate);
            order.setFilePath(file.getPath());
            order.setServiceFee(serviceFee);
            order.setTotalFee(payAmount);
            order.setPayStatus(false);
            order.setSendStatus(false);
            order.setPayAddress(payAddress);
            order.setReceiveAddress(receiveAddress);
            save(order);
        }
        return orderId;
    }


    public List<SatinsOrder> getByTransId(String transId) {
        Map<String, Object> map = new HashMap<>();
        map.put("trans_id", transId);
        List<SatinsOrder> orderList = satinsOrderMapper.selectByMap(map);
        return orderList;
    }

    public List<SatinsOrder> getByOrderId(String orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", orderId);
        List<SatinsOrder> orderList = satinsOrderMapper.selectByMap(map);
        return orderList;
    }


    public void deleteByTransId(String transId) {
        Map<String, Object> map = new HashMap<>();
        map.put("trans_id", transId);
        satinsOrderMapper.deleteByMap(map);
    }
}
