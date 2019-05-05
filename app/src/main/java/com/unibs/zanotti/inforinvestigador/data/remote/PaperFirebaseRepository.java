package com.unibs.zanotti.inforinvestigador.data.remote;

import android.util.Log;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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

import java.util.ArrayList;
import java.util.Iterator;
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
                .addOnFailureListener(e -> {
                    Log.e(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_READ_ERROR, "paper", e.toString()));
                    emitter.onError(e);
                })
                .addOnCompleteListener(task -> {
                    Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_SINGLE_READ_SUCCESS, "paper", paperId));
                    emitter.onComplete();
                })
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
                    DateUtils.fromLocalDateTimeToEpochMills(comment.getDateTime()),
                    comment.getChildren().stream().map(Comment::getId).collect(Collectors.toList())
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
    public Observable<Comment> getComments(String paperId) {
        return Observable.create(emitter ->
                firestoreDb.collection(String.format("%s/%s/%s",
                        Collections.PAPERS,
                        paperId,
                        Collections.COMMENTS))
                        .orderBy("epochTimestampMillis", Query.Direction.ASCENDING) // TODO: order also by rating
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            List<CommentEntity> commentEntities = queryDocumentSnapshots.getDocuments()
                                    .stream()
                                    .map(d -> d.toObject(CommentEntity.class))
                                    .collect(Collectors.toList());

                            List<Comment> comments = fromEntities(commentEntities, paperId);
                            comments.forEach(emitter::onNext);

                            Log.d(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_READ_SUCCESS, "comments"));
                            emitter.onComplete();
                        })
                        .addOnFailureListener(e -> {
                            emitter.onError(e);
                            Log.e(TAG, String.format(FirebaseUtils.LOG_MSG_STANDARD_READ_ERROR, "comment", e.toString()));
                        })
        );
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

    /**
     * Given as input a list of comment entities, return the corresponding list of {@link Comment comments},
     * each of which contains the respective nested tree structure of comment replies
     *
     * @param commentEntities
     * @param paperId         Id of the paper associated to the {@link Comment comments} that will be created
     * @return
     */
    private List<Comment> fromEntities(List<CommentEntity> commentEntities, String paperId) {
        List<Comment> result = new ArrayList<>();
        Iterator<CommentEntity> iterator = commentEntities.iterator();
        List<CommentEntity> addedChildren = new ArrayList<>();

        while (iterator.hasNext()) {
            CommentEntity entity = iterator.next();
            if (!addedChildren.contains(entity)) {

                List<CommentEntity> childrenCommentEntities = getChildrenCommentEntities(entity, commentEntities);
                addedChildren.addAll(childrenCommentEntities);
                Comment comment = new Comment(entity.getBody(),
                        entity.getAuthor(),
                        entity.getScore(),
                        entity.getId(),
                        DateUtils.fromEpochTimestampMillis(entity.getEpochTimestampMillis()),
                        paperId,
                        buildChildrenTree(paperId, childrenCommentEntities, commentEntities, addedChildren)
                );

                result.add(comment);
            }
        }
        return result;
    }

    private List<Comment> buildChildrenTree(String paperId, List<CommentEntity> children, List<CommentEntity> set, List<CommentEntity> addedChildren) {
        List<Comment> result = new ArrayList<>();

        for (CommentEntity child : children) {
            List<CommentEntity> childrenCommentEntities = getChildrenCommentEntities(child, set);
            addedChildren.addAll(childrenCommentEntities);
            result.add(new Comment(
                    child.getBody(),
                    child.getAuthor(),
                    child.getScore(),
                    child.getId(),
                    DateUtils.fromEpochTimestampMillis(child.getEpochTimestampMillis()),
                    paperId,
                    buildChildrenTree(paperId, childrenCommentEntities, set, addedChildren))
            );
        }

        return result;
    }


    /**
     * Given a comment entity and a list of comment entities, return the comment entities included in the list provided
     * as second argument that are children of the entity provided as first argument
     *
     * @param entity
     * @param commentEntities
     * @return
     */
    private List<CommentEntity> getChildrenCommentEntities(CommentEntity entity, List<CommentEntity> commentEntities) {
        List<CommentEntity> result = new ArrayList<>();
        for (String id : entity.getChildrenCommentIDs()) {
            commentEntities.stream().filter(e -> e.getId().equals(id)).forEach(result::add);
        }
        return result;
    }
}
