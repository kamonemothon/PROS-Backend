package com.nemo.neplan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // User와 관련된 필드 선언
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    // File과 관련된 필드 선언
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "file_id")
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    private File file;
    String fileURL;
    String content;

    // Place와 관련된 필드 선언
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Place place;



    // 생성자, getter, setter 등 작성

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public File getFile() {
//        return file;
//    }ㅡ
//
//    public void setFile(File file) {
//        this.file = file;
//    }


    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }


}
