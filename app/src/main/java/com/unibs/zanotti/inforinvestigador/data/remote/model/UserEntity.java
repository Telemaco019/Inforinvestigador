package com.unibs.zanotti.inforinvestigador.data.remote.model;


public class UserEntity {
    private String id;
    private String email;
    private String profilePictureUri;
    private String name;
    private String location;
    private int sharesNumber;
    private int followingNumber;
    private int followersNumber;
    private long creationEpochTimestampMillis;
    private String phone;

    public UserEntity() {
        // Required by Firestore
    }

    public UserEntity(String id,
                      String email,
                      String name,
                      String phone,
                      String location,
                      int sharesNumber,
                      int followingNumber,
                      int followersNumber,
                      String profilePictureUri,
                      Long creationEpochTimestampMillis) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.location = location;
        this.sharesNumber = sharesNumber;
        this.followersNumber = followersNumber;
        this.followingNumber = followingNumber;
        this.phone = phone;
        this.profilePictureUri = profilePictureUri;
        this.creationEpochTimestampMillis = creationEpochTimestampMillis;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSharesNumber() {
        return sharesNumber;
    }

    public void setSharesNumber(int sharesNumber) {
        this.sharesNumber = sharesNumber;
    }

    public int getFollowingNumber() {
        return followingNumber;
    }

    public void setFollowingNumber(int followingNumber) {
        this.followingNumber = followingNumber;
    }

    public int getFollowersNumber() {
        return followersNumber;
    }

    public void setFollowersNumber(int followersNumber) {
        this.followersNumber = followersNumber;
    }
}
