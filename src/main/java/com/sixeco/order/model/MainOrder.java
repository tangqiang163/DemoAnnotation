package com.sixeco.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * main_order表
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String mainOrderNo;

    private String source;

    private Integer equipment;

    private Integer orderStatus;

    private Integer productCount;

    private BigDecimal originalAmount;

    private BigDecimal amount;

    private String userIdTdc;

    private String purchaserId;

    private String purchaserName;

    private String mobile;

    private String idNumber;

    private Boolean settlementStatus;

    private Date settledTime;

    private Boolean splitFlag;

    private String splitPlan;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Long createBy;

    private Long updateBy;

    private Boolean isDeleted;

}
