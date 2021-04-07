package com.example.superheroapp;

public class Hero {
    private String superheroname;
    private int Svalue;
    private String coverImage;

    public  Hero(){}
    public Hero(String superheroname,int Svalue,String coverImage){
        this.superheroname = superheroname;
        this.Svalue = Svalue;
        this.coverImage = coverImage;

    }

    public String getSuperheroname() {
        return superheroname;
    }

    public void setSuperheroname(String superheroname) {
        this.superheroname = superheroname;
    }

    public int getSvalue() {
        return Svalue;
    }

    public void setSvalue(int svalue) {
        Svalue = svalue;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
