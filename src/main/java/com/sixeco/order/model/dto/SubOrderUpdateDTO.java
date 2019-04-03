package com.sixeco.order.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SubOrderUpdateDTO
 *
 * @author: Zhanghe
 * @date: 2019-04-03
 */
@ApiModel(value = "子订单更新DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubOrderUpdateDTO {

    private Long id;

    private String merchantId;

    private String merchantName;

    private Integer payType;

    private String storeCode;

    private String storeName;

    private String receiverName;

    private String receiverMobile;

    private String receiverAddress;
}
