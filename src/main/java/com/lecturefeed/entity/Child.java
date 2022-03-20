package com.lecturefeed.entity;

import javax.persistence.*;

@Entity
public class Child {
    private String name;

    @Id
    @GeneratedValue
    private Integer id;

    public Child(String name) {
        this.name = name;
    }

    public Child() {}

    @ManyToOne
    @JoinColumn(name="parent_id", nullable=false)
    private Parent parent;

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Child [name=" + name + "]";
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }


}
