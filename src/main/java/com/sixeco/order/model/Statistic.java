package com.sixeco.order.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * statistic表
 * @author: Zhanghe
 * @date: 2019/4/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Statistic implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String accountNo;

    private BigDecimal successAmount;

    private BigDecimal orderAmount;

    private BigDecimal accountAmount;

    private BigDecimal serviceCharge;

    private String buyerName;

    private String dealerName;

    private String singer;

    private LocalDateTime signTime;

    private String payChannel;

    private String orderType;

    private String subOrderNo;

    private String orderState;

    private String buyerIdCard;

    private String orderSource;

    private String orderCheck;

    private LocalDateTime checkTime;

    private String mainOrderNo;

    private String areaName;

    private String outTradeNo;

    private String storeName;

    private Integer type;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    @TableLogic
    private Boolean isDeleted;
}
