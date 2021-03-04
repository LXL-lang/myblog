package com.lxl.mapper;

import com.lxl.pojo.Comment;

import java.util.List;

/**
 * @Author : lxl
 * @create : 2021/2/26 13:54
 * @describe:
 */
public interface CommentMapper {
    int save(Comment comment);
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId);

    Comment findById(Long id);

    List<Comment> findByParentId(Long parentId);

    int findAllCount();

}
