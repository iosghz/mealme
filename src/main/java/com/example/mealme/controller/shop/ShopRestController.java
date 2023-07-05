package com.example.mealme.controller.shop;

import com.example.mealme.dto.ProductDto;
import com.example.mealme.dto.UserDto;
import com.example.mealme.service.shop.ShopService;
import com.example.mealme.vo.CartVo;
import com.example.mealme.vo.ProductVo;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shops/*")
@RequiredArgsConstructor
public class ShopRestController {
    private final ShopService shopService;

    @GetMapping("/allList")
    public List<ProductVo> allList() {
        List<ProductVo> selectAllPrice = shopService.selectAllPrice();

        return selectAllPrice;
    }

    @GetMapping("/reviewPriceList")
    public List<ProductVo> reviewPriceList() {
        List<ProductVo> selectReviewPrice = shopService.selectReviewPrice();

        return selectReviewPrice;
    }

    @GetMapping("/lowPriceList")
    public List<ProductVo> lowPriceList() {
        List<ProductVo> selectLowPrice = shopService.selectLowPrice();

        return selectLowPrice;
    }




}