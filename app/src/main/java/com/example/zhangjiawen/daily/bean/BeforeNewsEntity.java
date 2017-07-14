package com.example.zhangjiawen.daily.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangjiawen on 2017/5/1.
 * 过往新闻实体类
 */
public class BeforeNewsEntity implements Serializable {

    private static final long serialVersionUID = -8394147349420778074L;
    private String date;
    private List<StoriesEntity> stories;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public static class StoriesEntity implements Serializable{
        private static final long serialVersionUID = -8394147349420778074L;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;
        private String date;

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

        public void setImages(List<String> images) {
            this.images = images;
        }

        public List<String> getImages() {
            return images;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
        }
    }

}
