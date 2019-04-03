package com.sixeco.order.model.dto;

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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainOrderUpdateDTO {

    @NotNull(message = "id不能为空")
    private Long id;

    private String mobile;

    private String idNumber;

    private List<SubOrderUpdateDTO> subOrders;
}
