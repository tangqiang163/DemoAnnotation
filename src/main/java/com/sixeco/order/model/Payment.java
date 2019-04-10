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
 * payment表
 * @author: Zhanghe
 * @date: 2019/4/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String mainOrderNo;

    private Integer channel;

    private Integer type;

    private BigDecimal amount;

    private Integer status;

    private String outTradeNo;

    private String message;

    private String comment;

    private String merOrderNo;

    private LocalDateTime paymentStartTime;

    private String buyerEmail;

    private String buyerId;

    private String posCode;

    private String storeCode;

    private LocalDateTime refundTime;

    private LocalDateTime refundStartTime;

    private String refundPaymentId;

    private Integer payType;

    private Integer posPayChannel;

    private String posPayNo;

    private String posPayDate;

    private Integer storeId;

    private String orderSubNo;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    @TableLogic
    private Boolean isDeleted;
}
