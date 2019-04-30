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
import io.reactivex.Single;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    public Optional<Paper> getPaper(String paperId) {
        return Optional.empty();
    }

    @Override
    public List<Paper> getPapers() {
        // TODO
        return Arrays.asList(new Paper(
                "abc123",
                "Title of the paper 1",
                Arrays.asList("Author 1", "Author 2", "Author 3"),
                "Mar 2019",
                "DOI",
                100,
                Arrays.asList("Topic 1", "Topic 2", "Topic 3"),
                "This is the abstract of the paper",
                "Publisher of the paper",
                "1l",
                "Comment of the user who shared the paper")
        );
    }

    @Override
    public List<Comment> getComments(long paperId) {
        return null;
    }

    @Override
    public Single<Paper> savePaper(final Paper paper) {
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
            paperEntity.setPaperId(paperCollection.document().getId());
        }

        return Single.fromCallable(() -> {
            firestoreDb.collection(Collections.PAPERS)
                    .document(paperEntity.getId())
                    .set(paperEntity)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "added document paper with id " + paperEntity.getId()))
                    .addOnFailureListener(e -> Log.d(TAG, "failed to add document paper: " + e));
            return paper;
        });
    }

    @Override
    public Single<Comment> addComment(String paperId, Comment comment) {
        CommentEntity commentEntity = new CommentEntity(
                comment.getBody(),
                comment.getAuthor(),
                comment.getScore(),
                comment.getId(),
                comment.getChildren().stream().map(Comment::getId).collect(Collectors.toList()));

        CollectionReference collection = firestoreDb.collection(Collections.PAPERS).document(paperId).collection(Collections.COMMENTS);
        if (StringUtils.isBlank(commentEntity.getId())) {
            commentEntity.setId(collection.document().getId());
        }

        return Single.fromCallable(() -> {
            collection.document(commentEntity.getId())
                    .set(commentEntity)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "added to Firestore Comment with id " + commentEntity.getId()))
                    .addOnFailureListener(e -> Log.e(TAG, "failed to add Comment to Firestore: " + e));

            // Persist children comments
            if (comment.getChildren().size() > 0) {
                comment.getChildren().forEach(c -> this.addComment(paperId, c));
            }

            return comment;
        });
    }
}
