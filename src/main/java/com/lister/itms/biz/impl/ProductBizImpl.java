package com.lister.itms.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lister.itms.biz.ProductBiz;
import com.lister.itms.dao.entity.ProductDO;
import com.lister.itms.dao.entity.ProjectDO;
import com.lister.itms.dao.mapper.ProductMapper;
import com.lister.itms.dao.mapper.ProjectMapper;
import com.lister.itms.exception.BizException;
import com.lister.itms.form.ProductForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.utils.DTOUtils;
import com.lister.itms.vo.DropDownVO;
import com.lister.itms.vo.ProductVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * 产品管理
 * Create By Lister
 */
@Service
public class ProductBizImpl implements ProductBiz {

    private static final Logger L = LoggerFactory.getLogger(ProductBizImpl.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProjectMapper projectMapper;
    
    /**
     * 检查产品名称是否存在
     * @param productName
     * @return
     */
    @Override
    public void checkProductExists(String productName, Long id) {
        if (productMapper.checkProductExists(productName.trim(), id) > 0){
            throw new BizException("产品名称已存在");
        }
    }

    /**
     * 创建产品
     * @param productForm
     * @return
     */
    @Override
    public void createProduct(ProductForm productForm) throws BizException {
        checkProductExists(productForm.getProductName(), productForm.getId());
        ProductDO productDO = DTOUtils.map(productForm, ProductDO.class);
        productDO.setCreateBy(productForm.getCreateBy());
        productDO.setUpdateBy(productForm.getCreateBy());
        productMapper.insert(productDO);
    }

    @Override
    public void updateProduct(ProductForm productForm) throws BizException {
        checkProductExists(productForm.getProductName(), productForm.getId());
        ProductDO productDO = DTOUtils.map(productForm, ProductDO.class);
        productDO.setUpdateTime(new Date());
        productDO.setUpdateBy(productForm.getUpdateBy());
        productMapper.updateByPrimaryKeySelective(productDO);
    }

    /**
     * 查询所有的产品，为了下拉框
     *
     * @return
     */
    @Override
    public List<DropDownVO> getAllProductForDropDownVO(){
        List<ProductDO> productDOS = productMapper.listAll(null);
        List<DropDownVO> dropDownVOS = new ArrayList<>();
        for (ProductDO productDO : productDOS) {
            dropDownVOS.add(new DropDownVO(productDO.getId()+"",productDO.getProductName()));
        }
        return dropDownVOS;
    }

    /**
     * 查询所有产品
     *
     * @return
     * @param userId
     */
    @Override
    public List<ProductVo> getAllProduct(Long userId) {
        List<ProductDO> productDOS = productMapper.listAll(userId);
        if(CollectionUtils.isEmpty(productDOS)){
            return Collections.emptyList();
        }
        return DTOUtils.map(productDOS, ProductVo.class);
    }

    /**
     * 分页查询产品
     *
     * @param pageInfo
     * @param product
     * @return
     */
    @Override
    public MyPageInfo<ProductVo> page(MyPageInfo pageInfo, ProductVo product) {
        ProductDO productDO = DTOUtils.map(product,ProductDO.class);
        PageHelper.startPage(pageInfo.getPageNumber(),pageInfo.getPageSize());
        Page<ProductDO> page = productMapper.listPage(productDO);
        List<ProductVo> list = DTOUtils.map(page.getResult(),ProductVo.class);
        pageInfo = MyPageInfo.create(page);
        pageInfo.setRows(list);
        return pageInfo;
    }

    /**
     * 删除产品
     *
     * @param productForm
     */
    @Override
    public void delProduct(ProductForm productForm) {
        int count = projectMapper.getProjectCountByProduct(productForm.getId());
        if (count > 0) {
            throw new BizException("该产品下已存在项目，不允许删除");
        }
        productMapper.deleteByPrimaryKey(productForm.getId());
    }

    /**
     * 产品详情
     *
     * @param id
     * @return
     */
    @Override
    public ProductVo getProduct(Long id) {
        ProductDO productDO = productMapper.selectByPrimaryKey(id);
        ProductVo productVo = DTOUtils.map(productDO,ProductVo.class);
        return productVo;
    }

    /**
     * 根据项目编号查询所有产品，将关联上的产品的ishave设置为true
     *
     * @param id
     * @return
     */
    @Override
    public List<ProductVo> getAllByProjectShowHave(Long id) {
        List<ProductDO> productDOS = productMapper.getAllByProjectShowHave(id);
        if(CollectionUtils.isEmpty(productDOS)){
            return Collections.emptyList();
        }
        return DTOUtils.map(productDOS, ProductVo.class);
    }
    
}
