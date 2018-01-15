package com.dcg.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by duchenguang on 2018/1/15.
 */

@Entity
@Table(name = "domain")
public class Domain implements Serializable {

    @Id
    private Long id;

    private String parent;

    private String name;

    public Domain() {
    }

    public Long getId() {
        return id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
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
        return "Domain{" +
                "id=" + id +
                ", parent='" + parent + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
