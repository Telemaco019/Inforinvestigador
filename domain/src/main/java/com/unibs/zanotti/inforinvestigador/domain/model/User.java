package com.unibs.zanotti.inforinvestigador.domain.model;

import android.net.Uri;

import java.time.LocalDateTime;

public class User {
    private String id;
    private String email;
    private String name;
    private Uri profilePictureUri;
    LocalDateTime creationDateTime;

    public User(String id,String email, String name, Uri profilePictureUri, LocalDateTime creationDateTime) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profilePictureUri = profilePictureUri;
        this.creationDateTime = creationDateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getProfilePictureUri() {
        return profilePictureUri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public void setProfilePictureUri(Uri profilePictureUri) {
        this.profilePictureUri = profilePictureUri;
    }
}
