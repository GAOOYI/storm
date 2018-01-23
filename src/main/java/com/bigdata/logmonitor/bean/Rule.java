package com.bigdata.logmonitor.bean;

import java.util.Date;

public class Rule {
    private Integer id;

    private String name;

    private String describ;

    private String keyword;

    private Integer isvalid;

    private Integer appid;

    private Date createdate;

    private Date updatedate;

    private String createuser;

    private String updateuser;

    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", describ='" + describ + '\'' +
                ", keyword='" + keyword + '\'' +
                ", isvalid=" + isvalid +
                ", appid=" + appid +
                ", createdate=" + createdate +
                ", updatedate=" + updatedate +
                ", createuser='" + createuser + '\'' +
                ", updateuser='" + updateuser + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ == null ? null : describ.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Integer getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }
}