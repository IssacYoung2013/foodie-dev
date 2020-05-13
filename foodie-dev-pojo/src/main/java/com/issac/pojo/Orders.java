package com.issac.pojo;

import javax.persistence.*;
import java.util.Date;

public class Orders {
    @Id
    private Integer id;

    @Column(name = "userId")
    private Integer userid;

    private String number;

    private Date createtime;

    private String node;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return userId
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * @return number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * @return node
     */
    public String getNode() {
        return node;
    }

    /**
     * @param node
     */
    public void setNode(String node) {
        this.node = node;
    }
}