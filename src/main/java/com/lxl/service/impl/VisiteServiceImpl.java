package com.lxl.service.impl;

import com.lxl.mapper.VisiteMapper;
import com.lxl.service.VisiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author : lxl
 * @create : 2021/2/28 21:49
 * @describe:
 */
@Service
public class VisiteServiceImpl implements VisiteService {
    @Autowired
    private VisiteMapper visiteMapper;
    @Override
    public void increase() {
      visiteMapper.add(new Date());
    }
    public Long findAllVisite(){
       return visiteMapper.findAll();
    }
}
