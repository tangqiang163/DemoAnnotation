package com.sixeco.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "电话号码不能为空")
    private String mobile;

    @NotBlank(message = "身份证号不能为空")
    private String idNumber;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverName;

    private String receiverMobile;

    private String receiverAddress;

    private Boolean splitFlag;

    private String splitPlan;

    private String remark;

    @Size(min = 1, message = "至少有一个子订单")
    private List<SubOrderDTO> subOrders;
}
