package br.com.mac.projetoacacia.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;


@Entity
public class Nota {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String content;

    @NonNull
    private String userId;

    public Nota(final String title, final String content, final String id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }
    @Ignore
    public Nota(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }
}

