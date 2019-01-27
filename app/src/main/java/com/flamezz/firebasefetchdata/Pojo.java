package com.flamezz.firebasefetchdata;

import android.os.Parcelable;

import java.io.Serializable;

public class Pojo implements Serializable{


    private String name;
    private int order;
    private String image;



    Pojo(String name,int order,String image)
    {
        this.name=name;
        this.order=order;
        this.image=image;
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "name='" + name + '\'' +
                ", order='" + order + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public Pojo() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

