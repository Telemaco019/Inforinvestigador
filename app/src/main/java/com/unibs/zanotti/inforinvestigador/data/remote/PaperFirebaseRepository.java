package com.unibs.zanotti.inforinvestigador.data.remote;

import android.net.Uri;
import android.util.Log;
import com.google.firebase.firestore.*;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.remote.model.Collections;
import com.unibs.zanotti.inforinvestigador.data.remote.model.CommentEntity;
import com.unibs.zanotti.inforinvestigador.data.remote.model.PaperEntity;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils;
import com.unibs.zanotti.inforinvestigador.utils.FirebaseUtils;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Cancellable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PaperFirebaseRepository implements IPaperRepository {
    private static final String TAG = String.valueOf(PaperFirebaseRepository.class);
    private static PaperFirebaseRepository INSTANCE = null;

    private FirebaseFirestore firestoreDb;

    private PaperFirebaseRepository(FirebaseFirestore firestoreDb) {
        this.firestoreDb = firestoreDb;
    }

    public static PaperFirebaseRepository getInstance(FirebaseFirestore firestoreDb) {
        if (INSTANCE == null) {
            INSTANCE = new PaperFirebaseRepository(firestoreDb);
        }
        return INSTANCE;
    }

    @Override
    public Maybe<Paper> getPaper(String paperId) {
        return Maybe.create(emitter -> firestoreDb.document(String.format("%s/%s", Collections.PAPERS, paperId))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        emitter.onSuccess(fromEntity(Objects.requireNonNull(documentSnapshot.toObject(PaperEntity.class))));
                        Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_SINGLE_READ_SUCCESS, "paper", paperId));
                    } else {
                        emitter.onComplete();
                        Log.e(TAG, String.format("Document with id %s does not exist", paperId));
                    }
                })
                .addOnFailureListener(e -> {
                    emitter.onError(e);
                    Log.e(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_READ_ERROR, "paper", e.toString()));
                })
                .addOnCompleteListener(task -> emitter.onComplete())
        );
    }

    @Override
    public Observable<Paper> getPapersSharedByUser(String userId) {
        return Observable.create(emitter -> firestoreDb.collection(Collections.PAPERS)
                .whereEqualTo(FirebaseUtils.FIRESTORE_DOCUMENT_PAPER_SHARING_USER_ID, userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> queryDocumentSnapshots.getDocuments()
                        .stream()
                        .map(d -> d.toObject(PaperEntity.class))
                        .filter(Objects::nonNull)
                        .map(this::fromEntity)
                        .forEach(paper -> {
                            Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_SINGLE_READ_SUCCESS, "paper", paper.getPaperId()));
                            emitter.onNext(paper);
                        }))
                .addOnFailureListener(e -> {
                    Log.e(TAG, String.format(TAG, FirebaseUtils.LOG_MSG_STANDARD_READ_ERROR, "paper", e.toString()));
                    emitter.onError(e);
                })
                .addOnCompleteListener(task -> {
                    Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_READ_SUCCESS, "papers"));
                    emitter.onComplete();
                }));
    }

    @Override
    public Observable<Paper> getPaperRecommendations(String userId) {
        return Observable.create(emitter -> firestoreDb.collection(Collections.PAPERS)
                .whereLessThan(FirebaseUtils.FIRESTORE_DOCUMENT_PAPER_SHARING_USER_ID, userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> queryDocumentSnapshots.getDocuments()
                        .stream()
                        .filter(d -> d.get(FirebaseUtils.FIRESTORE_DOCUMENT_PAPER_SHARING_USER_ID) != null)
                        .filter(d -> !d.get(FirebaseUtils.FIRESTORE_DOCUMENT_PAPER_SHARING_USER_ID).equals(userId))
                        .map(d -> d.toObject(PaperEntity.class))
                        .filter(Objects::nonNull)
                        .filter(paperEntity -> paperEntity.getUnsuggestedToUsersIds() == null ||
                                !paperEntity.getUnsuggestedToUsersIds().contains(userId))
                        .map(this::fromEntity)
                        .forEach(paper -> {
                            Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_SINGLE_READ_SUCCESS, "paper", paper.getPaperId()));
                            emitter.onNext(paper);
                        }))
                .addOnFailureListener(e -> {
                    Log.e(TAG, String.format(TAG, FirebaseUtils.LOG_MSG_STANDARD_READ_ERROR, "paper", e.toString()));
                    emitter.onError(e);
                })
                .addOnCompleteListener(task -> {
                    Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_READ_SUCCESS, "papers"));
                    emitter.onComplete();
                })
        );
    }

    @Override
    public Completable markPaperAsUnsuggestedToUser(String paperId, String userId) {
        return Completable.create(emitter -> firestoreDb.document(String.format("%s/%s", Collections.PAPERS, paperId))
                .update(FirebaseUtils.FIRESTORE_DOCUMENT_PAPER_UNSUGGESTED_TO_USERS_IDS, FieldValue.arrayUnion(userId))
                .addOnCompleteListener(aVoid -> {
                    if (aVoid.isSuccessful()) {
                        emitter.onComplete();
                    } else {
                        Log.e(TAG, aVoid.getException().getMessage(), aVoid.getException());
                        emitter.onError(aVoid.getException());
                    }
                }));
    }

    @Override
    public Single<Paper> savePaper(final Paper paper, String sharingUserId) {
        return Single.create(emitter -> {
            PaperEntity paperEntity = new PaperEntity(
                    paper.getPaperId(),
                    paper.getPaperTitle().trim(),
                    paper.getPaperAuthors(),
                    paper.getPaperDate(),
                    paper.getPaperDoi().trim(),
                    paper.getPaperCitations(),
                    paper.getPaperTopics(),
                    paper.getPaperAbstract().trim(),
                    paper.getPaperPublisher().trim(),
                    paper.getSharingUserId().trim(),
                    paper.getSharingUserComment().trim(),
                    paper.getPaperImages().stream().map(Uri::toString).collect(Collectors.toList()),
                    paper.getURL()
            );

            // Set autogenerated id
            DocumentReference paperDocument = firestoreDb.collection(Collections.PAPERS).document();
            paperEntity.setId(paperDocument.getId());
            paper.setPaperId(paperEntity.getId());

            DocumentReference sharingUserDoc = firestoreDb.collection(Collections.USERS).document(sharingUserId);

            WriteBatch writeBatch = firestoreDb.batch();
            writeBatch.set(paperDocument, paperEntity);
            // TODO: in future use a cloud function for updating the counter (more robust)
            writeBatch.update(sharingUserDoc, FirebaseUtils.FIRESTORE_DOCUMENT_USER_FIELD_SHARES_NUMBER, FieldValue.increment(1L));

            writeBatch.commit()
                    .addOnSuccessListener(documentReference -> {
                        Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_WRITE_SUCCESS, "paper", paperEntity.getId()));
                        emitter.onSuccess(paper);
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_SAVE_ERROR, "paper", paper.getPaperId(), e.toString()));
                        emitter.onError(e);
                    });
        });
    }

    @Override
    public Single<Comment> saveComment(Comment comment) {
        return Single.create(emitter -> {
            CommentEntity commentEntity = new CommentEntity(
                    comment.getBody(),
                    comment.getAuthorName(),
                    comment.getLikesCount(),
                    comment.getId(),
                    DateUtils.fromLocalDateTimeToEpochMills(comment.getDateTime()),
                    comment.getAuthorId());

            CollectionReference collection = firestoreDb.collection(Collections.PAPERS).document(comment.getPaperId()).collection(Collections.COMMENTS);

            // Set autogenerated id
            commentEntity.setId(collection.document().getId());
            comment.setId(commentEntity.getId());

            collection.document(commentEntity.getId())
                    .set(commentEntity)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, String.format("comment with id %s saved in Firestore", commentEntity.getId())))
                    .addOnFailureListener(e -> {
                        Log.e(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_SAVE_ERROR, "comment", comment.getId(), e));
                        emitter.onError(e);
                    });

            emitter.onSuccess(comment);
            Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_WRITE_SUCCESS, "comment", comment.getId()));
        });
    }

    @Override
    public Completable likeComment(String paperId, String commentId, String userId) {
        return Completable.create(emitter -> {
            String commentDocPath = String.format("%s/%s/%s/%s",
                    Collections.PAPERS,
                    paperId,
                    Collections.COMMENTS,
                    commentId);

            DocumentReference commentDoc = firestoreDb.document(commentDocPath);

            WriteBatch writeBatch = firestoreDb.batch();
            writeBatch.update(commentDoc, String.format("likes.%s", userId), Boolean.TRUE);
            // TODO: in future use a cloud function for updating the counter (more robust)
            writeBatch.update(commentDoc, "likesCount", FieldValue.increment(1L));

            writeBatch.commit()
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "error liking comment", e);
                        emitter.onError(e);
                    });
        });
    }

    @Override
    public Completable unlikeComment(String paperId, String commentId, String userId) {
        return Completable.create(emitter -> {
            String commentDocPath = String.format("%s/%s/%s/%s",
                    Collections.PAPERS,
                    paperId,
                    Collections.COMMENTS,
                    commentId);

            DocumentReference commentDoc = firestoreDb.document(commentDocPath);

            WriteBatch writeBatch = firestoreDb.batch();
            writeBatch.update(commentDoc, String.format("likes.%s", userId), Boolean.FALSE);
            // In future use a cloud function for updating the counter (more robust)
            writeBatch.update(commentDoc, "likesCount", FieldValue.increment(-1L));

            writeBatch.commit()
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "error unliking comment", e);
                        emitter.onError(e);
                    });
        });
    }

    @Override
    public Observable<Comment> getCommentsRealTime(String paperId, String currentUserId) {
        return Observable.create(emitter -> {
            ListenerRegistration registration = firestoreDb.collection(String.format("%s/%s/%s",
                    Collections.PAPERS,
                    paperId,
                    Collections.COMMENTS))
                    .orderBy("likesCount", Query.Direction.ASCENDING)
                    .orderBy("epochTimestampMillis", Query.Direction.ASCENDING)
                    .addSnapshotListener((snapshot, e) -> {
                        if (e != null) {
                            Log.e(TAG, "listen:error", e);
                            return;
                        }
                        if (snapshot != null) {
                            List<DocumentChange> documentChanges = snapshot.getDocumentChanges();
                            for (DocumentChange change : documentChanges) {
                                CommentEntity entity = change.getDocument().toObject(CommentEntity.class);
                                emitter.onNext(fromEntity(entity, paperId, currentUserId));
                            }
                        }
                    });
            emitter.setCancellable(new Cancellable() {
                @Override
                public void cancel() throws Exception {
                    registration.remove();
                }
            });
        });
    }

    @NotNull
    private Paper fromEntity(PaperEntity paperEntity) {
        return new Paper(
                paperEntity.getId(),
                paperEntity.getPaperTitle(),
                paperEntity.getPaperAuthors(),
                paperEntity.getPaperDate(),
                paperEntity.getPaperDoi(),
                paperEntity.getPaperCitations(),
                paperEntity.getPaperTopics(),
                paperEntity.getPaperAbstract(),
                paperEntity.getPaperPublisher(),
                paperEntity.getSharingUserId(),
                paperEntity.getSharingUserComment(),
                paperEntity.getPaperImages().stream().map(Uri::parse).collect(Collectors.toList()),
                paperEntity.getUrl()
        );
    }

    private Comment fromEntity(CommentEntity commentEntity, String paperId, String currentUserId) {
        Comment result = new Comment(commentEntity.getBody(),
                commentEntity.getAuthorName(),
                commentEntity.getLikesCount(),
                commentEntity.getId(),
                DateUtils.fromEpochTimestampMillis(commentEntity.getEpochTimestampMillis()),
                paperId,
                commentEntity.getAuthorId());
        result.setLikedByCurrentUser(commentEntity.getLikes().getOrDefault(currentUserId, false));
        return result;
    }
}
