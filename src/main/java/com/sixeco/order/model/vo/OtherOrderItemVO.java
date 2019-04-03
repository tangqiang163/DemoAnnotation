package com.sixeco.order.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author: Zhanghe
 * @date: 2019-04-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtherOrderItemVO {

    private Long id;

    private String subOrderNo;

    private String itemNo;

    private Integer purchaseCount;

    private String productCode;

    private String productName;

    private BigDecimal productPrice;

    private BigDecimal productSellPrice;

    private String productAttr;

    private String shippingWay;

    private String orderLogisticsId;

    private BigDecimal taxOtherPrice;

    private String taxReason;

    private Boolean drawBillFlag;

    private String billCode;

    private String supplierCode;

    private String shopId;

    private String receiverName;

    private String receiverMobile;

    private String receiverCountry;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private Boolean discountFlag;

    private Integer accountType;

    private Integer integration;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;
}
