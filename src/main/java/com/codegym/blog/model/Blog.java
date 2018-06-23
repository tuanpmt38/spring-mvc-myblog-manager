package com.codegym.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@JsonIgnoreProperties("category")
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    private String summary;

    @Lob
    private String content;

    private String feature;

    private LocalDate createDate = LocalDate.now();

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name= "category_id")
    private Category category;

    public Blog(){}

    public Blog(Long id, String title, String summary, String content, String feature, LocalDate createDate, Category category) {
        this.id =id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.feature = feature;
        this.createDate = createDate;
        this.category = category;
    }

    public Blog(String title, String summary, String content, String feature, LocalDate createDate, Category category) {
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.feature = feature;
        this.createDate = createDate;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    @Override
    public String toString() {
        return String.format("Blog[id=%d, title=%s, content = %s]", id, title, content);
    }
}
