package com.tian.dubbo.consumer.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

/**
 * describe:
 *
 * @author mac 田北平
 * @date 2019/08/22 14:51
 */
@Component
public class ShopService {


    @Reference
    private com.tian.dubbo.service.api.service.ShopService shopService;

    public String getShopName(){
        return shopService.getShopName(new Random().nextInt());
    }

}
