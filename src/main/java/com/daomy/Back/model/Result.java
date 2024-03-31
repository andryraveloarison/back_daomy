package com.daomy.Back.model;

public class Result {
    String name;
    int point;

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }

    public Result(String name, int point){
        this.name=name;
        this.point=point;
    }
}
