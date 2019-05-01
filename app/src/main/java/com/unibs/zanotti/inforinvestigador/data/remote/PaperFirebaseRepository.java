package com.unibs.zanotti.inforinvestigador.data.remote;

import android.util.Log;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.remote.model.Collections;
import com.unibs.zanotti.inforinvestigador.data.remote.model.CommentEntity;
import com.unibs.zanotti.inforinvestigador.data.remote.model.PaperEntity;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
                .addOnSuccessListener(documentSnapshot -> emitter.onSuccess(
                        fromEntity(Objects.requireNonNull(documentSnapshot.toObject(PaperEntity.class))))
                )
                .addOnFailureListener(emitter::onError)
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
                        .forEach(emitter::onNext))
                .addOnFailureListener(emitter::onError)
                .addOnCompleteListener(task -> emitter.onComplete())
        );
    }

    @Override
    public Single<Paper> savePaper(final Paper paper) {
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
                                Log.d(TAG, "added document paper with id " + paperEntity.getId());
                                emitter.onSuccess(paper);
                            })
                            .addOnFailureListener(e -> {
                                Log.d(TAG, "failed to add document paper: " + e);
                                emitter.onError(e);
                            });
                }
        );
    }

    @Override
    public Single<Comment> addComment(String paperId, Comment comment) {
        return Single.create(emitter -> {
            CommentEntity commentEntity = new CommentEntity(
                    comment.getBody(),
                    comment.getAuthor(),
                    comment.getScore(),
                    comment.getId(),
                    comment.getChildren().stream().map(Comment::getId).collect(Collectors.toList())
            );

            CollectionReference collection = firestoreDb.collection(Collections.PAPERS).document(paperId).collection(Collections.COMMENTS);

            if (StringUtils.isBlank(commentEntity.getId())) {
                commentEntity.setId(collection.document().getId());
            }

            collection.document(commentEntity.getId())
                    .set(commentEntity)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "added to Firestore Comment with id " + commentEntity.getId()))
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "failed to add Comment to Firestore: " + e);
                        emitter.onError(e);
                    });

            emitter.onSuccess(comment);
        });
    }

    @Override
    public Single<List<Comment>> getComments(String paperId) {
        return Single.create(emitter -> {
            firestoreDb.collection(String.format("%s/%s/%s",
                    Collections.PAPERS,
                    paperId,
                    Collections.COMMENTS))
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<Comment> comments = new ArrayList<>();
                        queryDocumentSnapshots.getDocuments()
                                .stream()
                                .map(d -> d.toObject(CommentEntity.class))
                                .map(commentEntity -> new Comment(
                                        commentEntity.getBody(),
                                        commentEntity.getAuthor(),
                                        commentEntity.getScore(),
                                        commentEntity.getId(),
                                        new ArrayList<>()))
                                // buildChildrenCommentsTree(paperId, commentEntity.getChildrenCommentIDs())))
                                .forEach(comments::add);
                        emitter.onSuccess(comments);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    private List<Comment> buildChildrenCommentsTree(String paperId, List<String> childrenCommentIDs) {
        List<Comment> childrenComments = new ArrayList<>();
        for (String id : childrenCommentIDs) {
            firestoreDb.document(String.format("%s/%s/%s/%s",
                    Collections.PAPERS,
                    paperId,
                    Collections.COMMENTS,
                    id))
                    .get()
                    .addOnSuccessListener(doc -> {
                        CommentEntity childCommentEntity = doc.toObject(CommentEntity.class);
                        childrenComments.add(new Comment(
                                childCommentEntity.getBody(),
                                childCommentEntity.getAuthor(),
                                childCommentEntity.getScore(),
                                childCommentEntity.getId(),
                                this.buildChildrenCommentsTree(paperId, childCommentEntity.getChildrenCommentIDs())
                        ));
                    });
        }
        return childrenComments;
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
}
