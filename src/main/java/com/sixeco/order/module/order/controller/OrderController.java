package com.sixeco.order.module.order.controller;

import com.sixeco.order.base.context.PageInfo;
import com.sixeco.order.base.constant.RtnConstant;
import com.sixeco.order.base.context.RtnInfo;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.dto.MainOrderDTO;
import com.sixeco.order.module.order.service.OrderService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 订单接口
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value="新增订单", notes="")
    @ApiImplicitParam(name = "mainOrderDTO", value = "订单DTO", required = true, dataType = "mainOrderDTO")
    @PostMapping("add")
    @ResponseBody
    public RtnInfo add(@Valid MainOrderDTO mainOrderDTO, BindingResult result) {
        //检验参数
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                return RtnInfo.error(RtnConstant.Code.PARAMS_LACK_CODE, error.getDefaultMessage());
            }
        }
        return RtnInfo.success(orderService.add(mainOrderDTO));
    }

    @ApiOperation(value="获取订单列表", notes="")
    @GetMapping("list")
    @ResponseBody
    public RtnInfo list(PageInfo<MainOrder> pageInfo, MainOrder mainOrder) {
        return RtnInfo.success(orderService.list(pageInfo, mainOrder));
    }

    @ApiOperation(value="获取订单详情", notes="")
    @GetMapping("detail")
    @ResponseBody
    public RtnInfo detail(String mainOrderNo) {
        return RtnInfo.success(orderService.detail(mainOrderNo));
    }

}
