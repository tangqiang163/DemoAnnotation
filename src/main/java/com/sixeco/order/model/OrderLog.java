package com.sixeco.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * order_log表
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String mainOrderNo;

    private LocalDateTime createTime;

    private Long createBy;

    private String orderData;
}
