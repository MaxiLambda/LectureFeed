package com.lecturefeed.entity;

import javax.persistence.*;

@Entity
public class Beer{
    private String name;
    private Integer id;

    public Beer(String name) {
        this.name = name;
    }

    public Beer() {

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Beer [name=" + name + "]";
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    public Integer getId() {
        return id;
    }
}
