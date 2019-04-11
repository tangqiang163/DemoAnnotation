package com.sixeco.order.module.order.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.base.Strings;
import com.sixeco.order.base.annotation.*;
import com.sixeco.order.base.constant.Constants;
import com.sixeco.order.base.context.PageInfo;
import com.sixeco.order.base.constant.RtnConstant;
import com.sixeco.order.base.context.RtnInfo;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.dto.MainOrderDTO;
import com.sixeco.order.model.dto.MainOrderUpdateDTO;
import com.sixeco.order.module.discount.service.DiscountService;
import com.sixeco.order.module.order.service.OrderService;
import com.sun.xml.internal.bind.v2.TODO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private DiscountService discountService;

    @ApiOperation(value="新增订单")
    @PostMapping("add")
    @ResponseBody
    @InsertOrderLog
    @NoRepeatSubmit
    @DecryptRequest
    public RtnInfo add(@Validated @RequestBody MainOrderDTO mainOrderDTO, BindingResult result) {
        log.info("====下单对象信息[{}]", mainOrderDTO.toString());

        /******************    校验参数      ****************/
        /******************   第一步 校验字段      ****************/
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            for (ObjectError error : errorList) {
                return RtnInfo.error(RtnConstant.Code.PARAMS_LACK_CODE, error.getDefaultMessage());
            }
        }
        // 校验合并下单
        if (!Constants.MERGER_ORDER.equals(mainOrderDTO.getMergerFlag())) {
            return RtnInfo.error(RtnConstant.Code.PARAM_ERROR, "当前APP版本为低版本，请更新至最新版本！");
        }

        //校验交强险是否非必选逻辑
        Boolean flag = false;
        if (mainOrderDTO.getPayType() == 2) {
            if ( (mainOrderDTO.getSuiteId() == null || mainOrderDTO.getSuiteType() == null) && flag ) {
                return RtnInfo.error(RtnConstant.Code.PARAM_ERROR, "分期支付请选择保险保障套餐");
            }
        }

        // TODO  --------->> 需确认(1)
        //校验身份证,获取性别
//        if(mainOrderDTO.getOrderExtra().getGender() == null){
//            Result result = this.checkSex(dto.getIdCard());
//            if(result.getCode()==200){
//                Map<String,Object> map = (Map<String, Object>) result.getData();
//                Integer sexCode = MapUtils.getInteger(map,"sexCode");
//                dto.setSex(sexCode);
//            }else {
//                //身份证有误默认性别(男)
//                dto.setSex(1);
//            }
//        }

        // TODO  --------->> 需确认(2)
        // 如果是正常下单,需要校验,如果是意向订单,不要校验
//        if (dto == null  || dto.getSex() == null || dto.getProvinceId() == null || dto.getCityId() == null
//                || dto.getCode() == null || dto.getStoreId() == null || dto.getPayType() == null
//                ||  dto.getIdCardImgFront() == null || dto.getIdCardImgContrary() == null) {
//            if(null == dto.getIntentOrderNo() ){//如果是正常下单,需要校验,如果是意向订单,不要校验
//                return new Result(ResultCode.PARAM_LOSS);
//            }
//        }

        /******************    第二步： 活动+优惠券校验     ****************/
        // 调用商城活动接口
        RtnInfo rtnInfo = discountService.checkDiscount();


        /******************    第三步： 精品推荐下单校验     ****************/
        // 调用精品下单接口校验
        RtnInfo rtnInfo1 = orderService.checkHighQuality(mainOrderDTO.getParamJson2());

        /******************    第四步： 相关业务处理     ****************/
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
