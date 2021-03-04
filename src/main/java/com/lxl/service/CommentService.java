package com.lxl.service;

import com.lxl.pojo.Comment;

import java.util.List;

/**
 * @Author : lxl
 * @create : 2021/2/26 13:49
 * @describe:
 */
public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    int saveComment(Comment comment);

    int findAll();
}
