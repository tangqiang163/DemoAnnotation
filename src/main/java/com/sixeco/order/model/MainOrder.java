package com.sixeco.order.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * main_order表
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@ApiModel(value = "订单对象")
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

    private String userIdTdc;

    private String purchaserId;

    private String purchaserName;

    private String mobile;

    private String idNumber;

    private Integer payType;

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

    @TableLogic
    private Boolean isDeleted;

}
