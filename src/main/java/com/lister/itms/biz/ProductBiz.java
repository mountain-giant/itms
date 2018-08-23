package com.lister.itms.biz;

import com.lister.itms.exception.BizException;
import com.lister.itms.form.ProductForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.DropDownVO;
import com.lister.itms.vo.ProductVo;

import java.util.List;

/**
 * 产品管理
 * Created by Lister.
 */
public interface ProductBiz {

    /**
     * 检查产品名称是否存在
     * @param productName
     * @return
     */
    void checkProductExists(String productName, Long id);

    /**
     * 创建产品
     * @param productForm
     * @return
     */
    void createProduct(ProductForm productForm) throws BizException;

    /**
     * 修改产品
     * @param productForm
     * @return
     */
    void updateProduct(ProductForm productForm) throws BizException;

    /**
     * 查询所有的产品，为了下拉框
     * @return
     */
    List<DropDownVO> getAllProductForDropDownVO();

    /**
     * 查询所有产品
     * @return
     * @param userId
     */
    List<ProductVo> getAllProduct(Long userId);

    /**
     * 分页查询产品
     * @param page
     * @param product
     * @return
     */
    MyPageInfo<ProductVo> page(MyPageInfo page, ProductVo product);

    /**
     * 删除产品
     * @param productForm
     */
    void delProduct(ProductForm productForm);

    /**
     * 产品详情
     * @param id
     * @return
     */
    ProductVo getProduct(Long id);

    /**
     * 根据项目编号查询所有产品，将关联上的产品的ishave设置为true
     * @param id
     * @return
     */
    List<ProductVo> getAllByProjectShowHave(Long id);
}
