package com.gsfh.myteamwork.vmovie.bean;

import java.util.List;

/**
 * Created by admin on 2016/7/13.
 */
public class BackStageTitleBean {
    /**
     * status : 0
     * msg : OK
     * data : [{"cateid":"2","catename":"全部"},{"cateid":"47","catename":"电影自习室"},{"cateid":"53","catename":"电影会客厅"},{"cateid":"4","catename":"拍摄"},{"cateid":"26","catename":"综述"},{"cateid":"30","catename":"后期"},{"cateid":"31","catename":"器材"}]
     */

    private String status;
    private String msg;
    /**
     * cateid : 2
     * catename : 全部
     */

    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String cateid;
        private String catename;

        public String getCateid() {
            return cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
        }

        public String getCatename() {
            return catename;
        }

        public void setCatename(String catename) {
            this.catename = catename;
        }
    }
}
