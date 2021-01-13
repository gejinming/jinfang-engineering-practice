package com.jinfang.dto.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnFilter {

	@ApiModelProperty(value="列名定义")
	private String name;

	@ApiModelProperty(value="属性值")
	private String value;

}
