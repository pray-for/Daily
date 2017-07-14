package com.example.zhangjiawen.daily.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangjiawen on 2017/5/1.
 * 热点新闻实体类
 */
public class LatestNewsEntity extends BeforeNewsEntity implements Serializable {
    private static final long serialVersionUID = 4241003566054211379L;
    private List<TopStoriesEntity> top_stories;

    public void setTop_stories(List<TopStoriesEntity> top_stories) {
        this.top_stories = top_stories;
    }

    public List<TopStoriesEntity> getTop_stories() {
        return top_stories;
    }

    public static class TopStoriesEntity implements Serializable{
        private static final long serialVersionUID = -4646740135664156377L;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private String image;

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setImage(String images) {
            this.image = images;
        }

        public String getImage() {
            return image;
        }
    }
}
