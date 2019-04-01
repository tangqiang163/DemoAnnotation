package com.sixeco.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * sub_order表
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer mainOrderNo;

    private Integer subOrderNo;

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

    private Date createTime;

    private Date updateTime;

    private Long createBy;

    private Long updateBy;

    private Boolean isDeleted;
}
