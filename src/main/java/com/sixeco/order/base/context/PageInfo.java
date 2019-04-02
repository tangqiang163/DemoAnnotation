package com.sixeco.order.base.context;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页入参对象
 *
 * @author: Zhanghe
 * @date: 2019-04-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo<T> {

    private Integer pageNumber;

    private Integer pageSize;

    public Page<T> getPage() {
        return new Page<T>(pageNumber == null ? 1 : pageNumber , pageSize == null ? 10 : pageSize);
    }
}
