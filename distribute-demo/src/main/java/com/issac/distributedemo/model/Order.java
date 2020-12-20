package com.issac.distributedemo.model;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.id
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.order_status
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    private Integer orderStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.receiver_name
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    private String receiverName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.receiver_mobile
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    private String receiverMobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.order_amount
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    private BigDecimal orderAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.create_time
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.create_user
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.update_time
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.update_user
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    private String updateUser;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.id
     *
     * @return the value of order.id
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.id
     *
     * @param id the value for order.id
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.order_status
     *
     * @return the value of order.order_status
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.order_status
     *
     * @param orderStatus the value for order.order_status
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.receiver_name
     *
     * @return the value of order.receiver_name
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.receiver_name
     *
     * @param receiverName the value for order.receiver_name
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.receiver_mobile
     *
     * @return the value of order.receiver_mobile
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public String getReceiverMobile() {
        return receiverMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.receiver_mobile
     *
     * @param receiverMobile the value for order.receiver_mobile
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile == null ? null : receiverMobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.order_amount
     *
     * @return the value of order.order_amount
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.order_amount
     *
     * @param orderAmount the value for order.order_amount
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.create_time
     *
     * @return the value of order.create_time
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.create_time
     *
     * @param createTime the value for order.create_time
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.create_user
     *
     * @return the value of order.create_user
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.create_user
     *
     * @param createUser the value for order.create_user
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.update_time
     *
     * @return the value of order.update_time
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.update_time
     *
     * @param updateTime the value for order.update_time
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.update_user
     *
     * @return the value of order.update_user
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.update_user
     *
     * @param updateUser the value for order.update_user
     *
     * @mbg.generated Fri Aug 09 11:20:17 CST 2019
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}