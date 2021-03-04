package com.lxl.util;

import com.lxl.pojo.Tag;

import java.util.List;

public class ConvertUtils {

    //将数组转换为字符串分隔
    public static String ListToString(List<Tag> tags) {

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
    }


    }

