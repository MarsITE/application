package com.marsas.application.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private String announce;

    private String fullText;

    private int views;

    public Post() {
    }

    public Post(String title, String announce, String fullText) {
        this.title = title;
        this.announce = announce;
        this.fullText = fullText;
    }
}
