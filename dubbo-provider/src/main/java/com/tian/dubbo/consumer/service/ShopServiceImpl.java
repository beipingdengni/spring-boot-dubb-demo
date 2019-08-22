package com.tian.dubbo.consumer.service;

import com.tian.dubbo.service.api.service.ShopService;
import org.apache.dubbo.config.annotation.Service;

/**
 * describe:
 *
 * @author mac 田北平
 * @date 2019/08/22 14:28
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Override
    public String getShopName(Integer shopId) {
        return " dubbo service  "+shopId;
    }
}
