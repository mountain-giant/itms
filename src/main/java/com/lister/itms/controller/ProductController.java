package com.lister.itms.controller;

import com.lister.itms.annotations.RequestDesc;
import com.lister.itms.annotations.ViewDesc;
import com.lister.itms.biz.ProductBiz;
import com.lister.itms.form.ProductForm;
import com.lister.itms.modal.ResponseResult;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.DropDownVO;
import com.lister.itms.vo.ProductVo;
import com.lister.itms.vo.ProjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Lister.
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductBiz productBiz;

    /**
     * 进入产品列表页面
     * @return
     */
    @ViewDesc("产品列表")
    @RequestMapping("/index")
    public String toview(){
        return "/productmanager/list";
    }

    /**
     * 进入新增产品页面
     * @param model
     * @return
     */
    @ViewDesc
    @RequestMapping("/addView")
    public String addView(Model model){
        model.addAttribute("product",new ProjectVo());
        model.addAttribute("title","添加产品");
        return "/productmanager/form";
    }

    /**
     * 进入编辑产品页面
     * @param model
     * @param productVo
     * @return
     */
    @ViewDesc
    @RequestMapping("/updateView")
    public String updateView(Model model, ProductVo productVo){
        productVo = productBiz.getProduct(productVo.getId());
        model.addAttribute("product",productVo);
        model.addAttribute("title","编辑产品");
        return "/productmanager/form";
    }

    /**
     * 新增产品
     * @param productForm
     * @return
     */
    @ResponseBody
    @RequestDesc("新增产品")
    @RequestMapping("/create")
    public ResponseResult createProduct(@Valid ProductForm productForm, BindingResult bindingResult){
        super.hasParamsErrot(bindingResult);
        productForm.setCreateBy(getUserInfo().getName());
        productBiz.createProduct(productForm);
        return ResponseResult.createResult();
    }

    /**
     * 修改产品
     * @param productForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestDesc("修改产品")
    @RequestMapping("/update")
    public ResponseResult<Boolean> updateProject(@Valid ProductForm productForm, BindingResult bindingResult){
        super.hasParamsErrot(bindingResult);
        productForm.setUpdateBy(getUserInfo().getName());
        productBiz.updateProduct(productForm);
        return ResponseResult.createResult();
    }

    /**
     * 删除产品
     * @param productForm
     * @return
     */
    @ResponseBody
    @RequestMapping("/del")
    @RequestDesc("删除产品")
    public ResponseResult<Boolean> del(ProductForm productForm){
        productForm.setUpdateBy(getUserInfo().getName());
        productBiz.delProduct(productForm);
        return ResponseResult.createResult();
    }

    /**
     * 查询所有产品
     * @param productVo
     * @return
     */
    @RequestMapping("/list")
    public @ResponseBody ResponseResult<List<ProductVo>> listProduct(ProductVo productVo){
        ResponseResult<List<ProductVo>> result = new ResponseResult<>();
        List<ProductVo> productVoList = productBiz.getAllProduct(null);
        if(!CollectionUtils.isEmpty(productVoList) ){
            result.setData(productVoList);
        }
        return result;
    }

    /**
     * 分页查询产品列表
     * @param page
     * @param product
     * @return
     */
    @ResponseBody
    @RequestMapping("/page")
    public MyPageInfo<ProductVo> page(MyPageInfo page, ProductVo product){
        return productBiz.page(page,product);
    }

    /**
     * 查询所有的产品，为了查询下拉框
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAllProduct")
    public List<DropDownVO> getAllProduct(){
        return productBiz.getAllProductForDropDownVO();
    }
}
