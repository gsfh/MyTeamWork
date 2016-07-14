package com.gsfh.myteamwork.vmovie.bean;

import java.util.List;

/**
 * Created by GSFH on 2016-7-13.
 */
public class MainBannerBean {


    /**
     * status : 0
     * msg : OK
     * data : [{"bannerid":"1046","title":"","image":"http://cs.vmoiver.com/Uploads/Banner/2016/07/13/578607cf74c82.jpg","description":"","addtime":"1468401619","extra":"{\"app_banner_type\":\"2\",\"app_banner_param\":\"49569\"}","end_time":"0","extra_data":{"app_banner_type":"2","app_banner_param":"49569","is_album":"0"}},{"bannerid":"1043","title":"","image":"http://cs.vmoiver.com/Uploads/Banner/2016/07/12/57845d7248b37.jpg","description":"","addtime":"1468292467","extra":"{\"app_banner_type\":\"2\",\"app_banner_param\":\"49517\"}","end_time":"0","extra_data":{"app_banner_type":"2","app_banner_param":"49517","is_album":"0"}},{"bannerid":"1042","title":"","image":"http://cs.vmoiver.com/Uploads/Banner/2016/07/08/577f2c2a4ee51.jpg","description":"","addtime":"1467952171","extra":"{\"app_banner_type\":\"2\",\"app_banner_param\":\"49497\"}","end_time":"0","extra_data":{"app_banner_type":"2","app_banner_param":"49497","is_album":"0"}},{"bannerid":"1040","title":"","image":"http://cs.vmoiver.com/Uploads/Banner/2016/07/07/577dd699e3943.jpg","description":"","addtime":"1467864731","extra":"{\"app_banner_type\":\"2\",\"app_banner_param\":\"49472\"}","end_time":"0","extra_data":{"app_banner_type":"2","app_banner_param":"49472","is_album":"0"}},{"bannerid":"1012","title":"","image":"http://cs.vmoiver.com/Uploads/Banner/2016/07/01/5775ed3b0f8a1.jpg","description":"","addtime":"1467346235","extra":"{\"app_banner_type\":\"2\",\"app_banner_param\":\"49468\"}","end_time":"0","extra_data":{"app_banner_type":"2","app_banner_param":"49468","is_album":"1"}}]
     */

    private String status;
    private String msg;
    /**
     * bannerid : 1046
     * title :
     * image : http://cs.vmoiver.com/Uploads/Banner/2016/07/13/578607cf74c82.jpg
     * description :
     * addtime : 1468401619
     * extra : {"app_banner_type":"2","app_banner_param":"49569"}
     * end_time : 0
     * extra_data : {"app_banner_type":"2","app_banner_param":"49569","is_album":"0"}
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
        private String bannerid;
        private String title;
        private String image;
        private String description;
        private String addtime;
        private String extra;
        private String end_time;
        /**
         * app_banner_type : 2
         * app_banner_param : 49569
         * is_album : 0
         */

        private ExtraDataBean extra_data;

        public String getBannerid() {
            return bannerid;
        }

        public void setBannerid(String bannerid) {
            this.bannerid = bannerid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public ExtraDataBean getExtra_data() {
            return extra_data;
        }

        public void setExtra_data(ExtraDataBean extra_data) {
            this.extra_data = extra_data;
        }

        public static class ExtraDataBean {
            private String app_banner_type;
            private String app_banner_param;
            private String is_album;

            public String getApp_banner_type() {
                return app_banner_type;
            }

            public void setApp_banner_type(String app_banner_type) {
                this.app_banner_type = app_banner_type;
            }

            public String getApp_banner_param() {
                return app_banner_param;
            }

            public void setApp_banner_param(String app_banner_param) {
                this.app_banner_param = app_banner_param;
            }

            public String getIs_album() {
                return is_album;
            }

            public void setIs_album(String is_album) {
                this.is_album = is_album;
            }
        }
    }
}
