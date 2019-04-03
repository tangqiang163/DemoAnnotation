package com.sixeco.order.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * MainOrderUpdateDTO
 *
 * @author: Zhanghe
 * @date: 2019-04-03
 */
@ApiModel(value = "主订单更新DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainOrderUpdateDTO {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "电话号码")
    private String mobile;

    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    private List<SubOrderUpdateDTO> subOrders;
}
