package com.nemo.neplan.model;

import javax.persistence.*;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "original_filename")
    private String original_filename="unknown";
    private String fullPath;

    @PrePersist
    protected void onCreate() {
        if (original_filename == null) {
            original_filename = "unknown"; // Set a default value
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", original_filename='" + original_filename + '\'' +
                ", fullPath='" + fullPath + '\'' +
                '}';
    }

    public String getOriginal_filename() {
        return original_filename;
    }

    public void setOriginal_filename(String original_filename) {
        this.original_filename = original_filename;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
