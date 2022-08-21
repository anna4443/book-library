package com.as.booklibraryapp.domain;

public class Book {

    private Long id;
    private String title;
    private String author;
    private int year_release;
    private String img;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear_release() {
        return year_release;
    }

    public void setYear_release(int year_release) {
        this.year_release = year_release;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Book() {
    }

    public Book(Long id, String title, String author, int year_release, String img) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year_release = year_release;
        this.img = img;
    }
}
