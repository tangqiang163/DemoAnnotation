package com.sixeco.order.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeparatePayment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String mainOrderNo;

    private String orderSubNo;

    private BigDecimal payAmount;

    private Integer payStatus;

    private LocalDateTime paidTime;

    private Integer payOrder;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    @TableLogic
    private Boolean isDeleted;
}
