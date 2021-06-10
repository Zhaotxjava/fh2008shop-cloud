package com.fh.shop.api.sku.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.sku.po.Sku;
import com.fh.shop.api.sku.vo.UpdateSkuVo;
import org.apache.ibatis.annotations.Param;

public interface ISkuMapper extends BaseMapper<Sku> {


    int updateSkuStock(UpdateSkuVo x);

    void updateCount(@Param("skuId") Long skuId, @Param("skuCount") Long skuCount);

    void updateSales(@Param("skuId") Long skuId, @Param("skuCount") Long skuCount);
}
