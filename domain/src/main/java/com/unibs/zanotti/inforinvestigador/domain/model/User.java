package com.unibs.zanotti.inforinvestigador.domain.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

public class User implements Parcelable {
    private String id;
    private String email;
    private String name;
    private Uri profilePictureUri;
    private LocalDateTime creationDateTime;
    private String phone;
    private String location;
    private int sharesNumber;
    private int followingNumber;
    private int followersNumber;
    /**
     * If true, then the user has been verified (e.g. through email verification). False by default.
     */
    private boolean verified;
    private String instanceId;

    public User(String id,
                String email,
                String name,
                String phone,
                String location,
                int sharesNumber,
                int followingNumber,
                int followersNumber,
                Uri profilePictureUri,
                LocalDateTime creationDateTime,
                String instanceId) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.location = location;
        this.sharesNumber = sharesNumber;
        this.followersNumber = followersNumber;
        this.followingNumber = followingNumber;
        this.profilePictureUri = profilePictureUri;
        this.creationDateTime = creationDateTime;
        this.instanceId = instanceId;
        this.verified = false;
    }

    private User(Parcel in) {
        id = in.readString();
        email = in.readString();
        name = in.readString();
        phone = in.readString();
        location = in.readString();
        sharesNumber = in.readInt();
        followersNumber = in.readInt();
        followingNumber = in.readInt();
        profilePictureUri = in.readParcelable(Uri.class.getClassLoader());
        verified = in.readByte() != 0;
        creationDateTime = DateUtils.fromEpochTimestampMillis(in.readLong());
        instanceId = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(location);
        dest.writeInt(sharesNumber);
        dest.writeInt(followersNumber);
        dest.writeInt(followingNumber);
        dest.writeParcelable(profilePictureUri, flags);
        dest.writeByte((byte) (verified ? 1 : 0));
        dest.writeLong(DateUtils.fromLocalDateTimeToEpochMills(creationDateTime));
        dest.writeString(instanceId);
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
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
