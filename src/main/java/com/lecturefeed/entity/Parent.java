package com.lecturefeed.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Parent {

    private String name;

    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(targetEntity=Child.class, fetch=FetchType.EAGER)
    private List<Child> children;

    public Parent(String name) {
        this.name = name;
    }

    public Parent() {

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Parent [name=" + name + "]";
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
