package com.sixeco.order.module.order.controller;

import com.sixeco.order.base.context.PageInfo;
import com.sixeco.order.base.constant.RtnConstant;
import com.sixeco.order.base.context.RtnInfo;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.dto.MainOrderDTO;
import com.sixeco.order.model.dto.MainOrderUpdateDTO;
import com.sixeco.order.module.order.service.OrderService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    public RtnInfo add(@Validated MainOrderDTO mainOrderDTO, BindingResult result) {
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
    @ApiImplicitParam(name = "PageInfo", value = "分页对象", required = true, dataType = "PageInfo")
    @GetMapping("list")
    @ResponseBody
    public RtnInfo list(PageInfo<MainOrder> pageInfo, MainOrder mainOrder) {
        return RtnInfo.success(orderService.list(pageInfo, mainOrder));
    }

    @ApiOperation(value="获取订单详情", notes="")
    @ApiImplicitParam(name = "mainOrderNo", value = "订单号", required = true, dataType = "String")
    @GetMapping("detail")
    @ResponseBody
    public RtnInfo detail(@NotEmpty(message = "必须传入id") String mainOrderNo, BindingResult result) {
        //检验参数
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                return RtnInfo.error(RtnConstant.Code.PARAMS_LACK_CODE, error.getDefaultMessage());
            }
        }
        return RtnInfo.success(orderService.detail(mainOrderNo));
    }

    @ApiOperation(value = "修改订单状态", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "订单状态", required = true, dataType = "Integer")
    })
    @GetMapping("updateStatus")
    @ResponseBody
    public RtnInfo updateStatus(@NotNull(message = "必须传入id") Long id, @NotNull(message = "必须传入修改状态") Integer status, BindingResult result) {
        //检验参数
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                return RtnInfo.error(RtnConstant.Code.PARAMS_LACK_CODE, error.getDefaultMessage());
            }
        }
        return RtnInfo.success(orderService.updateStatus(id, status));
    }

    @ApiOperation(value = "修改订单", notes = "")
    @ApiImplicitParam(name = "MainOrderUpdateDTO", value = "订单更新DTO", required = true, dataType = "MainOrderUpdateDTO")
    @GetMapping("update")
    @ResponseBody
    public RtnInfo update(@Validated MainOrderUpdateDTO mainOrderUpdateDTO, BindingResult result) {
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
