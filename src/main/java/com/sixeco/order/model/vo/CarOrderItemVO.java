package com.sixeco.order.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CarOrderItemVO
 *
 * @author: Zhanghe
 * @date: 2019-04-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarOrderItemVO {

    private Long id;

    private String subOrderNo;

    private String itemNo;

    private Integer type;

    private Integer purchaseCount;

    private String productCode;

    private String productName;

    private String productAttr;

    private BigDecimal deposit;

    private BigDecimal stateSubsidy;

    private BigDecimal localSubsidy;

    private BigDecimal manufacturerSubsidy;

    private String shippingWay;

    private BigDecimal taxOtherPrice;

    private String taxReason;

    private Boolean drawBillFlag;

    private String billCode;

    private String carVsn;

    private String carVin;

    private String carBodyColor;

    private String carRoofColor;

    private String carInteriorColor;

    private String carPartInfo;

    private String shopId;

    private String receiverName;

    private String receiverMobile;

    private String receiverCountry;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private Boolean discountFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;
}
