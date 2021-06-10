package com.fh.shop.api.cate.po;

import lombok.Data;

@Data
public class Cate {

    private Long id;

    private String cateName;

    private Long fatherId;

    private Long typeId;

    private String typeName;//冗余字段;(另一张表的内容，但是为了提高sql的性能，加了冗余字段)一般不经常修改

}
