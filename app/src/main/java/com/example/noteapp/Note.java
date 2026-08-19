package com.example.noteapp;
public class Note {
    public long id;
    public String title, content;
    public Note(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
