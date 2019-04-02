package com.sixeco.order.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * SubOrderVO
 *
 * @author: Zhanghe
 * @date: 2019-04-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubOrderVO {

    private Long id;

    private String mainOrderNo;

    private String subOrderNo;

    private Integer orderType;

    private String merchantId;

    private String merchantName;

    private Integer payType;

    private String storeCode;

    private String storeName;

    private String receiverName;

    private String receiverMobile;

    private String receiverAddress;

    private BigDecimal subOriginalAmount;

    private BigDecimal subAmount;

    private BigDecimal carriagePrice;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private List<CarOrderItemVO> carOrderItems;

    private List<OtherOrderItemVO> otherOrderItems;
}
