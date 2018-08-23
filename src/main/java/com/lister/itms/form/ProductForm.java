package com.lister.itms.form;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;


/**
 * Created by Lister on 2017/6/16.
 */
@Data
public class ProductForm {

    private Long id;

    @NotEmpty(message = "产品名称必填")
    @Length(max = 20,min = 2,message = "产品名称长度最多20个字符,最少2个字符")
    private String productName;

    @NotEmpty(message = "产品描述必填")
    @Length(max = 500,message = "产品描述长度最多500个字符")
    private String productDesc;

    private String createBy;

    private String updateBy;

}
