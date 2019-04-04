package com.sixeco.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sixeco.order.model.SubOrder;
import com.sixeco.order.model.vo.CarOrderItemVO;
import com.sixeco.order.model.vo.OtherOrderItemVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 子订单Mapper
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Mapper
public interface SubOrderMapper extends BaseMapper<SubOrder> {

    void insertSubOrder(SubOrder subOrder);

    List<CarOrderItemVO> carListBySubOrderNo(String subOrderNo);

    List<OtherOrderItemVO> otherListBySubOrderNo(String subOrderNo);
}
