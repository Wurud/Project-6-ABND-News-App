package com.example.wurudaldibasi.newsapp;

public class Article {

    public String title;
    public String section;
    public String date;
    public String authorName;
    public String url;

    public Article(String title, String section, String date, String authorName, String url) {
        this.title = title;
        this.section = section;
        this.date = date;
        this.authorName = authorName;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
