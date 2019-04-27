package com.unibs.zanotti.inforinvestigador.data.remote;

import android.util.Log;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.data.remote.model.Collections;
import com.unibs.zanotti.inforinvestigador.data.remote.model.CommentEntity;
import com.unibs.zanotti.inforinvestigador.data.remote.model.PaperEntity;
import com.unibs.zanotti.inforinvestigador.domain.IPaperService;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaperFirebaseService implements IPaperService {
    private static final String TAG = String.valueOf(PaperFirebaseService.class);

    private FirebaseFirestore db;

    public PaperFirebaseService() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public Optional<Paper> getPaper(long paperId) {
        return Optional.empty();
    }

    @Override
    public List<Paper> getPapers() {
        return null;
    }

    @Override
    public List<Comment> getComments(long paperId) {
        return null;
    }

    public void savePaper(final Paper paper) {
        CollectionReference paperCollection = db.collection(Collections.PAPERS);

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

        db.collection(Collections.PAPERS)
                .document(paperEntity.getId())
                .set(paperEntity)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "added document paper with id " + paperEntity.getId()))
                .addOnFailureListener(e -> Log.d(TAG, "failed to add document paper: " + e));

        // FIXME: just for testing, supposed to be removed
        List<Comment> comments = paper.getComments();
        comments.stream().forEach(c -> this.saveComment(paperEntity.getId(), c));
    }

    // FIXME: supposed to become something like addComment
    private void saveComment(String paperId, Comment comment) {
        CommentEntity commentEntity = new CommentEntity(
                comment.getBody(),
                comment.getAuthor(),
                comment.getScore(),
                comment.getId(),
                comment.getChildren().stream().map(Comment::getId).collect(Collectors.toList()));

        CollectionReference collection = db.collection(Collections.PAPERS).document(paperId).collection(Collections.COMMENTS);
        if (StringUtils.isBlank(commentEntity.getId())) {
            commentEntity.setId(collection.document().getId());
        }

        collection.document(commentEntity.getId())
                .set(commentEntity)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "added document comment with id " + commentEntity.getId()))
                .addOnFailureListener(e -> Log.d(TAG, "failed to add document comment: " + e));

        if(comment.getChildren().size() > 0) {
            comment.getChildren().forEach(c -> this.saveComment(paperId,c));
        }
    }
}
