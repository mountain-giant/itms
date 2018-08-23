package com.lister.itms.dao.entity;

import java.util.Date;

public class ProductDO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column itms_product.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column itms_product.product_name
     *
     * @mbggenerated
     */
    private String productName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column itms_product.product_desc
     *
     * @mbggenerated
     */
    private String productDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column itms_product.is_deleted
     *
     * @mbggenerated
     */
    private String isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column itms_product.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column itms_product.create_by
     *
     * @mbggenerated
     */
    private String createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column itms_product.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column itms_product.update_by
     *
     * @mbggenerated
     */
    private String updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column itms_product.commons
     *
     * @mbggenerated
     */
    private String commons;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column itms_product.id
     *
     * @return the value of itms_product.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column itms_product.id
     *
     * @param id the value for itms_product.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column itms_product.product_name
     *
     * @return the value of itms_product.product_name
     *
     * @mbggenerated
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column itms_product.product_name
     *
     * @param productName the value for itms_product.product_name
     *
     * @mbggenerated
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column itms_product.product_desc
     *
     * @return the value of itms_product.product_desc
     *
     * @mbggenerated
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column itms_product.product_desc
     *
     * @param productDesc the value for itms_product.product_desc
     *
     * @mbggenerated
     */
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc == null ? null : productDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column itms_product.is_deleted
     *
     * @return the value of itms_product.is_deleted
     *
     * @mbggenerated
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column itms_product.is_deleted
     *
     * @param isDeleted the value for itms_product.is_deleted
     *
     * @mbggenerated
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column itms_product.create_time
     *
     * @return the value of itms_product.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column itms_product.create_time
     *
     * @param createTime the value for itms_product.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column itms_product.create_by
     *
     * @return the value of itms_product.create_by
     *
     * @mbggenerated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column itms_product.create_by
     *
     * @param createBy the value for itms_product.create_by
     *
     * @mbggenerated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column itms_product.update_time
     *
     * @return the value of itms_product.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column itms_product.update_time
     *
     * @param updateTime the value for itms_product.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column itms_product.update_by
     *
     * @return the value of itms_product.update_by
     *
     * @mbggenerated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column itms_product.update_by
     *
     * @param updateBy the value for itms_product.update_by
     *
     * @mbggenerated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column itms_product.commons
     *
     * @return the value of itms_product.commons
     *
     * @mbggenerated
     */
    public String getCommons() {
        return commons;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column itms_product.commons
     *
     * @param commons the value for itms_product.commons
     *
     * @mbggenerated
     */
    public void setCommons(String commons) {
        this.commons = commons == null ? null : commons.trim();
    }

    // 是否拥有
    private boolean isHave;

    public boolean getIsHave() {
        return isHave;
    }

    public void setIsHave(boolean have) {
        isHave = have;
    }
}
