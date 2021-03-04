package com.lxl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private String nickname;
    private String email;
    private String content;//内容
    private String avatar;//头像
    private Date createTime;
    private Blog blog;
    private List<Comment> replyComments=new ArrayList<>();//一个评论可能包含多个回复的子类评论(评论，回复评论)
    private Comment parentComment;//子类回复只能有一个父类
    private boolean adminComment;//标记是否是管理员的评论信息
}
