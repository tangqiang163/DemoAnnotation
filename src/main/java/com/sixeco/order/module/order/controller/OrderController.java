package com.sixeco.order.module.order.controller;

import com.google.common.base.Strings;
import com.sixeco.order.base.annotation.*;
import com.sixeco.order.base.context.PageInfo;
import com.sixeco.order.base.constant.RtnConstant;
import com.sixeco.order.base.context.RtnInfo;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.dto.MainOrderDTO;
import com.sixeco.order.model.dto.MainOrderUpdateDTO;
import com.sixeco.order.module.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单接口
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Api(value = "订单接口")
@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value="新增订单")
    @PostMapping("add")
    @ResponseBody
    @InsertOrderLog
    @NoRepeatSubmit
    @DecryptRequest
    public RtnInfo add(@Validated @RequestBody MainOrderDTO mainOrderDTO, BindingResult result) {
        //检验参数
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                return RtnInfo.error(RtnConstant.Code.PARAMS_LACK_CODE, error.getDefaultMessage());
            }
        }
        return RtnInfo.success(orderService.add(mainOrderDTO));
    }

    @ApiOperation(value="获取订单列表", notes="分页")
    @GetMapping("list")
    @ResponseBody
    public RtnInfo list(PageInfo<MainOrder> pageInfo, MainOrder mainOrder) {
        return RtnInfo.success(orderService.list(pageInfo, mainOrder));
    }

    @ApiOperation(value="获取订单详情")
    @ApiImplicitParam(name = "mainOrderNo", value = "订单号", required = true, dataType = "String")
    @GetMapping("detail")
    @ResponseBody
    @EncryptResponse
    public RtnInfo detail(String mainOrderNo) {
        //检验参数
        if (Strings.isNullOrEmpty(mainOrderNo)) {
            return RtnInfo.error(RtnConstant.Code.PARAMS_LACK_CODE, "必须传入订单号");
        }
        return RtnInfo.success(orderService.detail(mainOrderNo));
    }

    @ApiOperation(value = "修改订单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "Long", example = "1"),
            @ApiImplicitParam(name = "status", value = "订单状态", required = true, dataType = "Integer", example = "1")
    })
    @GetMapping("updateStatus")
    @ResponseBody
    @DecryptRequest
    public RtnInfo updateStatus(Long id, Integer status) {
        //检验参数
        if (id == null) {
            return RtnInfo.error(RtnConstant.Code.PARAMS_LACK_CODE, "必须传入id");
        }
        if (status == null) {
            return RtnInfo.error(RtnConstant.Code.PARAMS_LACK_CODE, "必须传入修改状态");
        }
        return RtnInfo.success(orderService.updateStatus(id, status));
    }

    @ApiOperation(value = "修改订单")
    @GetMapping("update")
    @ResponseBody
    @UpdateOrderLog
    @DecryptRequest
    public RtnInfo update(@Validated @RequestBody MainOrderUpdateDTO mainOrderUpdateDTO, BindingResult result) {
        //检验参数
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                return RtnInfo.error(RtnConstant.Code.PARAMS_LACK_CODE, error.getDefaultMessage());
            }
        }
        return RtnInfo.success(orderService.update(mainOrderUpdateDTO));
    }

}
