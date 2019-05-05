package com.unibs.zanotti.inforinvestigador.data.remote.model;


public class UserEntity {
    private String id;
    private String email;
    private String profilePictureUri;
    private String name;
    private long creationEpochTimestampMillis;

    public UserEntity() {
        // Required by Firestore
    }

    public UserEntity(String id,
                      String email,
                      String name,
                      String profilePictureUri,
                      Long creationEpochTimestampMillis) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profilePictureUri = profilePictureUri;
        this.creationEpochTimestampMillis = creationEpochTimestampMillis;
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

    public long getCreationEpochTimestampMillis() {
        return creationEpochTimestampMillis;
    }

    public void setCreationEpochTimestampMillis(long creationEpochTimestampMillis) {
        this.creationEpochTimestampMillis = creationEpochTimestampMillis;
    }
}
