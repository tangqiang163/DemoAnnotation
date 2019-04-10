package com.sixeco.order.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * OtherOrderItemDTO
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@ApiModel(value = "其他商品DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtherOrderItemDTO {

    private Integer purchaseCount;

    private String productCode;

    private String productName;

    private String productAttr;

    private String shippingWay;

    private String orderLogisticsId;

    private BigDecimal taxOtherPrice;

    private String taxReason;

    private Boolean drawBillFlag;

    private String billCode;

    private String supplierCode;

    private String shopId;

    private Boolean discountFlag;

    private Integer accountType;

    private Integer integration;
}
