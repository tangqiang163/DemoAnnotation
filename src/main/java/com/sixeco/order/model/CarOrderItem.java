package com.sixeco.order.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * car_other_item表
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "car_other_item")
public class CarOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String subOrderNo;

    private Integer purchaseCount;

    private String productCode;

    private String productName;

    private BigDecimal productPrice;

    private BigDecimal productSellPrice;

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

    private Date createTime;

    private Date updateTime;

    private Long createBy;

    private Long updateBy;

    private Boolean isDeleted;
}
