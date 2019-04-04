package com.sixeco.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sixeco.order.model.OtherOrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 其他商品明细Mapper
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Mapper
public interface OtherOrderItemMapper extends BaseMapper<OtherOrderItem> {

    void insertOtherOrderItem(OtherOrderItem otherOrderItem);
}
