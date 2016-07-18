package com.gsfh.myteamwork.vmovie.bean;

/**
 * @ 董传亮
 * 为了获得第二页的视屏播放网址  然而分享网址也在这里
 * Created by admin on 2016/7/18.
 */
public class SeriesDetailVideoBean {
    /**
     * status : 0
     * msg : ok
     * data : {"title":"番外篇-绿幕搭建和拍摄技巧 上海温哥华电影学院","seriesid":"45","series_postid":"1792","video_link":"http://v.youku.com/v_show/id_XMTYyNDExOTkyNA==.html?f=26857029","episode":"85","count_comment":"21","share_link":{"sweibo":"http://www.vmovier.com/series/45/85?debug=1&_vfrom=VmovierApp_sweibo","weixin":"http://www.vmovier.com/series/45/85?debug=1&_vfrom=VmovierApp_weixin","qzone":"http://www.vmovier.com/series/45/85?debug=1&_vfrom=VmovierApp_qzone","qq":"http://www.vmovier.com/series/45/85?debug=1&_vfrom=VmovierApp_qq"},"qiniu_url":"http://bsy.qiniu.vmovier.vmoiver.com/5772693e88d92.mp4"}
     */

    private String status;
    private String msg;
    /**
     * title : 番外篇-绿幕搭建和拍摄技巧 上海温哥华电影学院
     * seriesid : 45
     * series_postid : 1792
     * video_link : http://v.youku.com/v_show/id_XMTYyNDExOTkyNA==.html?f=26857029
     * episode : 85
     * count_comment : 21
     * share_link : {"sweibo":"http://www.vmovier.com/series/45/85?debug=1&_vfrom=VmovierApp_sweibo","weixin":"http://www.vmovier.com/series/45/85?debug=1&_vfrom=VmovierApp_weixin","qzone":"http://www.vmovier.com/series/45/85?debug=1&_vfrom=VmovierApp_qzone","qq":"http://www.vmovier.com/series/45/85?debug=1&_vfrom=VmovierApp_qq"}
     * qiniu_url : http://bsy.qiniu.vmovier.vmoiver.com/5772693e88d92.mp4
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String title;
        private String seriesid;
        private String series_postid;
        private String video_link;
        private String episode;
        private String count_comment;
        /**
         * sweibo : http://www.vmovier.com/series/45/85?debug=1&_vfrom=VmovierApp_sweibo
         * weixin : http://www.vmovier.com/series/45/85?debug=1&_vfrom=VmovierApp_weixin
         * qzone : http://www.vmovier.com/series/45/85?debug=1&_vfrom=VmovierApp_qzone
         * qq : http://www.vmovier.com/series/45/85?debug=1&_vfrom=VmovierApp_qq
         */

        private ShareLinkBean share_link;
        private String qiniu_url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSeriesid() {
            return seriesid;
        }

        public void setSeriesid(String seriesid) {
            this.seriesid = seriesid;
        }

        public String getSeries_postid() {
            return series_postid;
        }

        public void setSeries_postid(String series_postid) {
            this.series_postid = series_postid;
        }

        public String getVideo_link() {
            return video_link;
        }

        public void setVideo_link(String video_link) {
            this.video_link = video_link;
        }

        public String getEpisode() {
            return episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }

        public String getCount_comment() {
            return count_comment;
        }

        public void setCount_comment(String count_comment) {
            this.count_comment = count_comment;
        }

        public ShareLinkBean getShare_link() {
            return share_link;
        }

        public void setShare_link(ShareLinkBean share_link) {
            this.share_link = share_link;
        }

        public String getQiniu_url() {
            return qiniu_url;
        }

        public void setQiniu_url(String qiniu_url) {
            this.qiniu_url = qiniu_url;
        }

        public static class ShareLinkBean {
            private String sweibo;
            private String weixin;
            private String qzone;
            private String qq;

            public String getSweibo() {
                return sweibo;
            }

            public void setSweibo(String sweibo) {
                this.sweibo = sweibo;
            }

            public String getWeixin() {
                return weixin;
            }

            public void setWeixin(String weixin) {
                this.weixin = weixin;
            }

            public String getQzone() {
                return qzone;
            }

            public void setQzone(String qzone) {
                this.qzone = qzone;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }
        }
    }
}
