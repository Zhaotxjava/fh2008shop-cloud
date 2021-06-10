package com.fh.shop.api.cate.biz;

import com.alibaba.fastjson.JSON;

import com.fh.shop.api.cate.mapper.ICateMapper;
import com.fh.shop.api.cate.po.Cate;

import com.fh.common.ServerResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service("cateService")
@Transactional(rollbackFor = Exception.class)
public class ICateServiceImpl implements ICateService {

    @Autowired
    private ICateMapper cateMapper;

    @Override
    public ServerResponse queryList() {



        List<Cate> cateList = cateMapper.selectList(null);

        return ServerResponse.success(cateList);
    }
}
