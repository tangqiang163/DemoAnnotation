package com.sixeco.order.model.dto;

import com.sixeco.order.model.SubOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * MainOrderDTO
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainOrderDTO {

    private String authId;

    private String userIdTdc;

    private String purchaserId;

    private String purchaserName;

    private String source;

    private Integer equipment;

    @NotNull(message = "电话号码不能为空")
    private String mobile;

    @NotNull(message = "身份证号不能为空")
    private String idNumber;

    private Boolean splitFlag;

    private String splitPlan;

    private String remark;

    private List<SubOrder> subOrders;
}
