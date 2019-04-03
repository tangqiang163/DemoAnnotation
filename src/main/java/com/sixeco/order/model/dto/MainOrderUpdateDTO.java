package com.sixeco.order.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @ApiModelProperty(value = "id", required = true, example = "1")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "电话号码")
    @Pattern(regexp = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$",
            message = "手机号格式错误")
    private String mobile;

    @ApiModelProperty(value = "身份证号")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$",
            message = "身份证号格式错误")
    private String idNumber;

    private List<SubOrderUpdateDTO> subOrders;
}
