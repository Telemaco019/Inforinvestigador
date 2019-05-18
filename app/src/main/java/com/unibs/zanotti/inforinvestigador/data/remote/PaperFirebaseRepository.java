package com.unibs.zanotti.inforinvestigador.data.remote;

import android.util.Log;
import com.google.firebase.firestore.*;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.remote.model.Collections;
import com.unibs.zanotti.inforinvestigador.data.remote.model.CommentEntity;
import com.unibs.zanotti.inforinvestigador.data.remote.model.PaperEntity;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import com.unibs.zanotti.inforinvestigador.utils.FirebaseUtils;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

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
                    Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_SINGLE_READ_SUCCESS, "paper", paperId));
                    emitter.onSuccess(fromEntity(Objects.requireNonNull(documentSnapshot.toObject(PaperEntity.class))));
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_READ_ERROR, "paper", e.toString()));
                    emitter.onError(e);
                })
                .addOnCompleteListener(task -> emitter.onComplete())
        );
    }

    @Override
    public Observable<Paper> getPapers() {
        return Observable.create(emitter -> firestoreDb.collection(Collections.PAPERS)
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
                })
        );
    }

    @Override
    public Single<Paper> saveUpdatePaper(final Paper paper) {
        return Single.create(emitter -> {
            CollectionReference paperCollection = firestoreDb.collection(Collections.PAPERS);

            PaperEntity paperEntity = new PaperEntity(
                    paper.getPaperId(),
                    paper.getPaperTitle(),
                    paper.getPaperAuthors(),
                    paper.getPaperDate(),
                    paper.getPaperDoi(),
                    paper.getPaperCitations(),
                    paper.getPaperTopics(),
                    paper.getPaperAbstract(),
                    paper.getPaperPublisher(),
                    paper.getSharingUserId(),
                    paper.getSharingUserComment()
            );

            if (StringUtils.isBlank(paper.getPaperId())) {
                paperEntity.setId(paperCollection.document().getId());
            }

            firestoreDb.collection(Collections.PAPERS)
                    .document(paperEntity.getId())
                    .set(paperEntity)
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
    public Single<Comment> saveUpdateComment(Comment comment) {
        return Single.create(emitter -> {
            CommentEntity commentEntity = new CommentEntity(
                    comment.getBody(),
                    comment.getAuthor(),
                    comment.getScore(),
                    comment.getId(),
                    DateUtils.fromLocalDateTimeToEpochMills(comment.getDateTime())
            );

            CollectionReference collection = firestoreDb.collection(Collections.PAPERS).document(comment.getPaperId()).collection(Collections.COMMENTS);

            if (StringUtils.isBlank(commentEntity.getId())) {
                commentEntity.setId(collection.document().getId());
                comment.setId(commentEntity.getId());
            }

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
    public Observable<Comment> getCommentsRealTime(String paperId) {
        return Observable.create(emitter -> {
            ListenerRegistration registration = firestoreDb.collection(String.format("%s/%s/%s",
                    Collections.PAPERS,
                    paperId,
                    Collections.COMMENTS))
                    .orderBy("score", Query.Direction.ASCENDING)
                    .orderBy("epochTimestampMillis", Query.Direction.ASCENDING) // TODO: order also by rating
                    .addSnapshotListener((snapshot, e) -> {
                        if (e != null) {
                            Log.e(TAG, "listen:error", e);
                            return;
                        }
                        if (snapshot != null) {
                            List<DocumentChange> documentChanges = snapshot.getDocumentChanges();
                            for (DocumentChange change : documentChanges) {
                                CommentEntity entity = change.getDocument().toObject(CommentEntity.class);
                                emitter.onNext(fromEntity(entity, paperId));
                            }
                        }
                    });
            emitter.setCancellable(registration::remove);
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
                paperEntity.getPaperCitations() == null ? 0 : paperEntity.getPaperCitations(),
                paperEntity.getPaperTopics(),
                paperEntity.getPaperAbstract(),
                paperEntity.getPaperPublisher(),
                paperEntity.getSharingUserId(),
                paperEntity.getSharingUserComment()
        );
    }

    @NotNull
    private Comment fromEntity(CommentEntity commentEntity, String paperId) {
        return new Comment(commentEntity.getBody(),
                commentEntity.getAuthor(),
                commentEntity.getScore(),
                commentEntity.getId(),
                DateUtils.fromEpochTimestampMillis(commentEntity.getEpochTimestampMillis()),
                paperId);
    }
}
