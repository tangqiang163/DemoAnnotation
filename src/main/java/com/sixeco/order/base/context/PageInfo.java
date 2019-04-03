package com.sixeco.order.base.context;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
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
@ApiModel(value = "分页对象")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo<T> {

    @ApiModelProperty(value = "页数", example = "1")
    private Integer pageNumber;

    @ApiModelProperty(value = "每页行数", example = "10")
    private Integer pageSize;

    public Page<T> getPage() {
        return new Page<T>(pageNumber == null ? 1 : pageNumber , pageSize == null ? 10 : pageSize);
    }
}
