package com.example.zhangjiawen.daily.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangjiawen on 2017/5/1.
 */
public class NewsEntity implements Serializable {
    private static final long serialVersionUID = -7316863953889550877L;

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;

    private SectionEntity section;
    private int type;
    private int id;
    private List<?> js;
    private List<String> css;

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public SectionEntity getSection() {
        return section;
    }

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

    public void setJs(List<?> js) {
        this.js = js;
    }

    public List<?> getJs() {
        return js;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public List<String> getCss() {
        return css;
    }

    public static class SectionEntity implements Serializable{
        private static final long serialVersionUID = -490671162324767183L;
        private String thumbnail;
        private int id;
        private String name;

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
