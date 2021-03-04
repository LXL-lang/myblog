package com.lxl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Blog {
    private Long id;
    private String title;
    private String content;//博客内容
    private String firstPicture;//首图
    private String flag;//标记
    private Integer views;//浏览次数
    private boolean appreciation;//赞赏是否开启
    private boolean shareStatement;//转载声明是否开启
    private boolean commentabled;//评论是否开启
    private boolean published;//是否发布
    private boolean recommend;//是否推荐
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private Type type;//类别
    private Long typeId;
    private String description;//博客描述
    private List<Tag> tags;
    private String tagIds;//接受前端传过来的Ids字符串
    private User user;
    private Long userId;
    private List<Comment> comments;//一个blog可以包含多个评论
    public void init(){
        this.tagIds=tagsToIds(this.getTags());
    }
    //将数组转换为字符串分隔
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

}
