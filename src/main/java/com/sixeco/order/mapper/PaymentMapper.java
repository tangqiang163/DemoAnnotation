package com.sixeco.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sixeco.order.model.Payment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper extends BaseMapper<Payment> {
}
