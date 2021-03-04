package com.lxl.service.impl;

import com.lxl.mapper.CommentMapper;
import com.lxl.pojo.Comment;
import com.lxl.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author : lxl
 * @create : 2021/2/26 13:52
 * @describe:
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    //通过blogId查询出评论列表
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //查询到一级评论，没有父级的(第一层)
        List<Comment> comments=commentMapper.findByBlogIdAndParentCommentNull(blogId);
        //查询设置他们的子级评论
        for (Comment comment : comments) {
            comment.setReplyComments(commentMapper.findByParentId(comment.getId()));
        }
        return eachComment(comments);
    }
    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }
    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                tempReplys.add(reply1);
                //查询并设置出二级评论的子评论
                reply1.setReplyComments(commentMapper.findByParentId(reply1.getId()));
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
//        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                //添加 reply子代的回复评论设置进去
                reply.setReplyComments(commentMapper.findByParentId(reply.getId()));
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }

    //保存评论
    @Transactional
    @Override
    public int saveComment(Comment comment) {
        Long parentCommentId=comment.getParentComment().getId();
        if (parentCommentId!=-1){
            comment.setParentComment(commentMapper.findById(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());

        return commentMapper.save(comment);
    }

    @Override
    public int findAll() {
        return commentMapper.findAllCount();
    }

}
