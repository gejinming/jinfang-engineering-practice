package com.jinfang.dto.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页请求
 *
 * @author Louis
 * @date Aug 19, 2018
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分页参数及查询条件")
public class PageRequest {

    @ApiModelProperty(value = "当前页码")
    private int pageNum = 1;

    @ApiModelProperty(value = "每页显示条数")
    private int pageSize = 10;

    @ApiModelProperty(value="查询条件")
    private Map<String, ColumnFilter> columnFilters = new HashMap<>();

    @ApiModelProperty(value="用于controller填充扩展条件传递", hidden=true)
    private Map<String, Object> extProps = new HashMap<>();

    public ColumnFilter getColumnFilter(String name) {
        return columnFilters.get(name);
    }

    public String getAttrValue(String name) {
        if(columnFilters.isEmpty()) {
            return "";
        }

        return columnFilters.get(name).getValue();
    }
}
