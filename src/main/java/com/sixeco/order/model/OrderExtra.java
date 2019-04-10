package com.sixeco.order.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * order_extra表
 * @author: Zhanghe
 * @date: 2019/4/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String subOrderNo;

    private String manufacturer;

    private String bankName;

    private String bankBeneficiaryName;

    private String bankAccount;

    private Integer gender;

    private LocalDateTime idCardStart;

    private LocalDateTime idCardEnd;

    private Integer idType;

    private String idCardImgFront;

    private String idCardImgContrary;

    private Integer useDriversLicense;

    private String drivingLicenceImgFront;

    private String drivingLicenceImgContrary;

    private String residenceImgFront;

    private String residenceImgContrary;

    private String travelLicenseImgFront;

    private String travelLicenseImgContrary;

    private String oldToNew;

    private String salesCode;

    private String receiverName;

    private String receiverMobile;

    private String businessLicenseImgFront;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createBy;

    private Long updateBy;

    @TableLogic
    private Boolean isDeleted;
}
