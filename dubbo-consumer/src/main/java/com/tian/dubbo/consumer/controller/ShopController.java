package com.tian.dubbo.consumer.controller;

import com.tian.dubbo.consumer.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * describe:
 *
 * @author mac 田北平
 * @date 2019/08/22 14:32
 */
@RestController
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping("getShopName")
    public String getShopName(){
        return shopService.getShopName();
    }


}
