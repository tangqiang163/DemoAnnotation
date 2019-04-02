package com.sixeco.order.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * MainOrderVO
 *
 * @author: Zhanghe
 * @date: 2019-04-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainOrderVO {

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

    private LocalDateTime settledTime;

    private Boolean splitFlag;

    private String splitPlan;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    private List<SubOrderVO> subOrders;
}
