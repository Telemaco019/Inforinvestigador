package com.unibs.zanotti.inforinvestigador.utils;

public class FirebaseUtils {

    public static final String LOG_MSG_STANDARD_SAVE_ERROR = "failed to save in Firestore %s with id %s:\n%s";
    public static final String LOG_MSG_STANDARD_READ_ERROR = "failed to read %s from Firestore:\n%s";
    public static final String LOG_MSG_STANDARD_SINGLE_READ_SUCCESS = "%s with id %s read from Firestore";
    public static final String LOG_MSG_STANDARD_READ_SUCCESS = "reading of %s from Firestore successfully completed";
    public static final String LOG_MSG_STANDARD_WRITE_SUCCESS = "%s with id %s saved in Firestore";

    public static final String PROFILE_PICTURES_EXTENSION = ".jpg";

    public static final String FIRESTORE_DOCUMENT_USER_FIELD_PROFILE_PICTURE_URI = "profilePictureUri";
    public static final String FIRESTORE_DOCUMENT_USER_FIELD_NAME = "name";
    public static final String FIRESTORE_DOCUMENT_USER_FIELD_PHONE = "phone";
    public static final String FIRESTORE_DOCUMENT_USER_FIELD_LOCATION = "location";
    public static final String FIRESTORE_DOCUMENT_USER_FIELD_ID = "id";
    public static final String FIRESTORE_DOCUMENT_USER_FIELD_EMAIL_VERIFIED = "emailVerified";
    public static final String FIRESTORE_DOCUMENT_USER_FIELD_TOKEN_INSTANCE_ID = "instanceId";
    public static final String FIRESTORE_DOCUMENT_USER_FIELD_SHARES_NUMBER = "sharesNumber";
    public static final String FIRESTORE_DOCUMENT_PAPER_SHARING_USER_ID = "sharingUserId";
    public static final String FIRESTORE_DOCUMENT_PAPER_UNSUGGESTED_TO_USERS_IDS = "unsuggestedToUsersIds";
    public static final String FIRESTORE_DOCUMENT_PAPER_SHARING_USER_COMMENT = "sharingUserComment";
    public static final String FIRESTORE_DOCUMENT_PAPER_LIBRARY_PAPER_IDS = "paperIds";

    public static final String STORAGE_REFERENCE_PATH_PROFILE_PICTURES = "profile_pictures";
}
