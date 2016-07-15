package com.gsfh.myteamwork.vmovie.bean;

import java.util.List;

/**
 * @ 董传亮
 * 系列页面的详情
 * Created by admin on 2016/7/15.
 */
public class SeriesDetailBean {

    /**
     * status : 0
     * msg : ok
     * data : {"seriesid":"99","title":"造物集第四季","image":"http://cs.vmoiver.com/Uploads/Series/2016-07-11/5783563c85ad3.jpg","content":"爱摄影，走到哪儿都要带着相机的爸爸；爱臭美，喜欢鼓捣手工护肤品的妈妈；爱撒娇，除了睡觉就是粘人的金毛小公举，一个温暖快乐的三口之家，通过影像记录下日常的手作点滴，取天然花草、原味香料和各种有机食材，经过质朴而简单有趣的手工，作出全家人养护肌肤、疗愈身心的日常用品。一段段抽离忙碌生活的独处时光；一件件带着温暖和自然气息的手作小物，串联成《造物集》中那些恰到好处的幸福光影。","weekly":"每周三更新","count_follow":"719","isfollow":"0","share_link":"http://www.vmovier.com/series/99?_vfrom=VmovierApp","is_end":"0","update_to":"2","tag_name":"生活","post_num_per_seg":"10","posts":[{"from_to":"1-2","list":[{"series_postid":"1797","number":"2","title":"清凉止汗膏","addtime":"2016.07.13","duration":"133","thumbnail":"http://cs.vmoiver.com/Uploads/Series/2016-07-13/5785fe4665e9c.jpeg","source_link":"http://v.youku.com/v_show/id_XMTY0MjA1NjgyNA==.html?from=y1.7-1.2"},{"series_postid":"1795","number":"1","title":"夏日美肌散粉","addtime":"2016.07.06","duration":"148","thumbnail":"http://cs.vmoiver.com/Uploads/Series/2016-07-11/5783099e29cc6.jpeg","source_link":"http://v.youku.com/v_show/id_XMTYzMzg1ODU0NA==.html?from=y1.7-1.2"}]}]}
     */

    private String status;
    private String msg;
    /**
     * seriesid : 99
     * title : 造物集第四季
     * image : http://cs.vmoiver.com/Uploads/Series/2016-07-11/5783563c85ad3.jpg
     * content : 爱摄影，走到哪儿都要带着相机的爸爸；爱臭美，喜欢鼓捣手工护肤品的妈妈；爱撒娇，除了睡觉就是粘人的金毛小公举，一个温暖快乐的三口之家，通过影像记录下日常的手作点滴，取天然花草、原味香料和各种有机食材，经过质朴而简单有趣的手工，作出全家人养护肌肤、疗愈身心的日常用品。一段段抽离忙碌生活的独处时光；一件件带着温暖和自然气息的手作小物，串联成《造物集》中那些恰到好处的幸福光影。
     * weekly : 每周三更新
     * count_follow : 719
     * isfollow : 0
     * share_link : http://www.vmovier.com/series/99?_vfrom=VmovierApp
     * is_end : 0
     * update_to : 2
     * tag_name : 生活
     * post_num_per_seg : 10
     * posts : [{"from_to":"1-2","list":[{"series_postid":"1797","number":"2","title":"清凉止汗膏","addtime":"2016.07.13","duration":"133","thumbnail":"http://cs.vmoiver.com/Uploads/Series/2016-07-13/5785fe4665e9c.jpeg","source_link":"http://v.youku.com/v_show/id_XMTY0MjA1NjgyNA==.html?from=y1.7-1.2"},{"series_postid":"1795","number":"1","title":"夏日美肌散粉","addtime":"2016.07.06","duration":"148","thumbnail":"http://cs.vmoiver.com/Uploads/Series/2016-07-11/5783099e29cc6.jpeg","source_link":"http://v.youku.com/v_show/id_XMTYzMzg1ODU0NA==.html?from=y1.7-1.2"}]}]
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
        private String seriesid;
        private String title;
        private String image;
        private String content;
        private String weekly;
        private String count_follow;
        private String isfollow;
        private String share_link;
        private String is_end;
        private String update_to;
        private String tag_name;
        private String post_num_per_seg;
        /**
         * from_to : 1-2
         * list : [{"series_postid":"1797","number":"2","title":"清凉止汗膏","addtime":"2016.07.13","duration":"133","thumbnail":"http://cs.vmoiver.com/Uploads/Series/2016-07-13/5785fe4665e9c.jpeg","source_link":"http://v.youku.com/v_show/id_XMTY0MjA1NjgyNA==.html?from=y1.7-1.2"},{"series_postid":"1795","number":"1","title":"夏日美肌散粉","addtime":"2016.07.06","duration":"148","thumbnail":"http://cs.vmoiver.com/Uploads/Series/2016-07-11/5783099e29cc6.jpeg","source_link":"http://v.youku.com/v_show/id_XMTYzMzg1ODU0NA==.html?from=y1.7-1.2"}]
         */

        private List<PostsBean> posts;

        public String getSeriesid() {
            return seriesid;
        }

        public void setSeriesid(String seriesid) {
            this.seriesid = seriesid;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getWeekly() {
            return weekly;
        }

        public void setWeekly(String weekly) {
            this.weekly = weekly;
        }

        public String getCount_follow() {
            return count_follow;
        }

        public void setCount_follow(String count_follow) {
            this.count_follow = count_follow;
        }

        public String getIsfollow() {
            return isfollow;
        }

        public void setIsfollow(String isfollow) {
            this.isfollow = isfollow;
        }

        public String getShare_link() {
            return share_link;
        }

        public void setShare_link(String share_link) {
            this.share_link = share_link;
        }

        public String getIs_end() {
            return is_end;
        }

        public void setIs_end(String is_end) {
            this.is_end = is_end;
        }

        public String getUpdate_to() {
            return update_to;
        }

        public void setUpdate_to(String update_to) {
            this.update_to = update_to;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public String getPost_num_per_seg() {
            return post_num_per_seg;
        }

        public void setPost_num_per_seg(String post_num_per_seg) {
            this.post_num_per_seg = post_num_per_seg;
        }

        public List<PostsBean> getPosts() {
            return posts;
        }

        public void setPosts(List<PostsBean> posts) {
            this.posts = posts;
        }

        public static class PostsBean {
            private String from_to;
            /**
             * series_postid : 1797
             * number : 2
             * title : 清凉止汗膏
             * addtime : 2016.07.13
             * duration : 133
             * thumbnail : http://cs.vmoiver.com/Uploads/Series/2016-07-13/5785fe4665e9c.jpeg
             * source_link : http://v.youku.com/v_show/id_XMTY0MjA1NjgyNA==.html?from=y1.7-1.2
             */

            private List<ListBean> list;

            public String getFrom_to() {
                return from_to;
            }

            public void setFrom_to(String from_to) {
                this.from_to = from_to;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                private String series_postid;
                private String number;
                private String title;
                private String addtime;
                private String duration;
                private String thumbnail;
                private String source_link;

                public String getSeries_postid() {
                    return series_postid;
                }

                public void setSeries_postid(String series_postid) {
                    this.series_postid = series_postid;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getAddtime() {
                    return addtime;
                }

                public void setAddtime(String addtime) {
                    this.addtime = addtime;
                }

                public String getDuration() {
                    return duration;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public String getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(String thumbnail) {
                    this.thumbnail = thumbnail;
                }

                public String getSource_link() {
                    return source_link;
                }

                public void setSource_link(String source_link) {
                    this.source_link = source_link;
                }
            }
        }
    }
}
