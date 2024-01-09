package com.example;

import jakarta.persistence.*;

@Entity
@Table(name = "trees")
class TreeEntity {
    @Id
    public int id;
    @Column(name = "parent_id")
    public int parentId;

    public TreeEntity(int id, int parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public TreeEntity() {

    }


    public int getId() {return this.id;}
    public int getParentId() {return this.parentId;}
}