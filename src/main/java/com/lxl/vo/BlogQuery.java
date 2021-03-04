package com.lxl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义实体接受查询条件
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlogQuery {
    private String title;
    private Long typeId;
    private boolean recommend;
    private boolean published;//是否发布，判断是不是草稿后判断此字段的状态
    private boolean draft; //判断博客是否是草稿状态，后台进行查询草稿功能
}
