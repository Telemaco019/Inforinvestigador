package com.unibs.zanotti.inforinvestigador.data.remote.model;


import java.time.LocalDateTime;

public class UserEntity {
    private String id;
    private String email;
    private String profilePictureUri;
    private String name;
    private LocalDateTime creationDateTime;
    /**
     * Kept in sync with the firebase authentication through cloud functions
     */
    private boolean verified;

    public UserEntity() {
        // Required by Firestore
    }

    public UserEntity(String id,
                      String email,
                      String name,
                      String profilePictureUri,
                      LocalDateTime creationDateTime,
                      boolean verified) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profilePictureUri = profilePictureUri;
        this.creationDateTime = creationDateTime;
        this.verified = verified;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureUri() {
        return profilePictureUri;
    }

    public void setProfilePictureUri(String profilePictureUri) {
        this.profilePictureUri = profilePictureUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
