package com.bigdata.logmonitor.bean;

import java.util.Date;

public class App {
    private Integer id;

    private String name;

    private String describ;

    private Integer isonline;

    private Integer typeid;

    private Date createdate;

    private Date updateadate;

    private String createuser;

    private String updateuser;

    private String userid;

    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", describ='" + describ + '\'' +
                ", isonline=" + isonline +
                ", typeid=" + typeid +
                ", createdate=" + createdate +
                ", updateadate=" + updateadate +
                ", createuser='" + createuser + '\'' +
                ", updateuser='" + updateuser + '\'' +
                ", userid='" + userid + '\'' +
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

    public Integer getIsonline() {
        return isonline;
    }

    public void setIsonline(Integer isonline) {
        this.isonline = isonline;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdateadate() {
        return updateadate;
    }

    public void setUpdateadate(Date updateadate) {
        this.updateadate = updateadate;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}