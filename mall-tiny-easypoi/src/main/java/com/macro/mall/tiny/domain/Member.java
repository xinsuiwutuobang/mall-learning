package com.macro.mall.tiny.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.*;

import java.util.Date;

/**
 * 购物会员
 * Created by macro on 2021/10/12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Member {
    @Excel(name = "ID", width = 10)
    private Long id;
    /*
    * needMerge 是否需要纵向合并单元格(用于含有list中,单个的单元格,合并list创建的多个row)
    * */
    @Excel(name = "用户名", width = 20, needMerge = true)
    private String username;
    private String password;
    @Excel(name = "昵称", width = 20, needMerge = true)
    private String nickname;
    @Excel(name = "出生日期", width = 20, format = "yyyy-MM-dd")
    private Date birthday;
    /*
    * desensitizationRule 数据脱敏规则
    * */
    @Excel(name = "手机号", width = 20, needMerge = true, desensitizationRule = "3_4")
    private String phone;
    private String icon;
    /*
    * replace 值得替换 导出是{a_id,b_id} 导入反过来,所以只用写一
    * */
    @Excel(name = "性别", width = 10, replace = {"男_0", "女_1"})
    private Integer gender;
}
