package com.jinfang.page;

import lombok.Data;

/**
 * @description: 分页
 * @author: Gjm
 * @create: 2021-01-08 11:44
 **/
@Data
public class PageEntity {
    //页码
    private Integer page;
    //每页显示
    private Integer limit = 10;

}
