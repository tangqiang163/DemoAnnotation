package com.sixeco.order.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * PaymentDTO
 *
 * @author: Zhanghe
 * @date: 2019-04-11
 */
@ApiModel(value = "支付回调DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    @ApiModelProperty(value = "订单号")
    @NotEmpty(message = "订单号不能为空")
    private String mainOrderNo;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "支付方式")
    private String channelType;

    @ApiModelProperty(value = "交易流水号")
    @NotEmpty(message = "交易流水号不能为空")
    private String outTradeNo;

    @ApiModelProperty(value = "订单类型")
    private Integer orderType;
}
