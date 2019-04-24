package com.sixeco.order.model.dto;

import com.sixeco.order.model.OrderExtra;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * MainOrderDTO
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@ApiModel(value = "主订单DTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainOrderDTO {

    @NotBlank(message = "产品ID不能为空")
    private Integer productId;

    @NotBlank(message = "产品配置ID不能为空")
    private Integer productItemId;

    @NotBlank(message = "用户id不能为空")
    private String userIdTdc;

    private String purchaserId;

    @NotBlank(message = "购买人姓名不能为空")
    private String purchaserName;

    private String source;

    private Integer equipment;

    @Pattern(regexp = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$",
            message = "手机号格式错误")
    private String mobile;

    @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$",
            message = "身份证号格式错误")
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

    private String mergerFlag;

    @NotBlank(message = "支付方式不能为空")
    private Integer payType;

    // 类型1保障2保险
    private Integer suiteType;
    // 保险或保障ID
    private Integer suiteId;

    private OrderExtra orderExtra;

    // 精品推荐下单json字符串
    private String paramJson2;

    @Size(min = 1, message = "至少有一个子订单")
    private List<SubOrderDTO> subOrders;
}
