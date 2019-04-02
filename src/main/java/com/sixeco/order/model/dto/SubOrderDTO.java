package com.sixeco.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * SubOrderDTO
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubOrderDTO {

    private Integer orderType;

    private String merchantId;

    private String merchantName;

    private Integer payType;

    private String storeCode;

    private String storeName;

    private BigDecimal subOriginalAmount;

    private BigDecimal subAmount;

    private BigDecimal carriagePrice;

    private List<CarOrderItemDTO> carItems;

    private List<OtherOrderItemDTO> otherItems;

}
