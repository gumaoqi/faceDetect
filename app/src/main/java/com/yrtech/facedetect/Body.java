package com.yrtech.facedetect;


public class Body {
    int type;

    String image_url;
    String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String ToString() {
        if (type == 1) {
            return "{\"type\":1,\"content\":\"" + content + "\"}";
        }

        return "{\"type\":0,\"image_url\":\"" + image_url + "\"}";
    }
}