package com.sixeco.order.model.dto;

import java.math.BigDecimal;

/**
 * CarOrderItemDTO
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
public class CarOrderItemDTO {

    private Integer purchaseCount;

    private String productCode;

    private BigDecimal deposit;

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

    private Boolean discountFlag;
}
