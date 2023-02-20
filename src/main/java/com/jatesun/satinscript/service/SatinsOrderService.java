package com.jatesun.satinscript.service;

import com.jatesun.satinscript.Bean.SatinsOrder;
import com.jatesun.satinscript.Dao.SatinsOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单服务类
 * 处理需要铭刻的订单，根据状态决定下一步相关操作
 */
@Service
public class SatinsOrderService {
    @Autowired
    private SatinsOrderMapper satinsOrderMapper;

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

    public List<SatinsOrder> getByTransId(String transId) {
        Map<String, Object> map = new HashMap<>();
        map.put("trans_id", transId);
        List<SatinsOrder> orderList = satinsOrderMapper.selectByMap(map);
        return orderList;
    }

    public void deleteByTransId(String transId) {
        Map<String, Object> map = new HashMap<>();
        map.put("trans_id", transId);
        satinsOrderMapper.deleteByMap(map);
    }
}
