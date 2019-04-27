package com.unibs.zanotti.inforinvestigador.data.remote;

import android.util.Log;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.data.remote.model.Collections;
import com.unibs.zanotti.inforinvestigador.data.remote.model.PaperEntity;
import com.unibs.zanotti.inforinvestigador.domain.IPaperService;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;

import java.util.List;
import java.util.Optional;

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

    public void addPaper(final Paper paper) {
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

        if(paper.getPaperId() == null) {
            paperEntity.setPaperId(paperCollection.document().getId());
        }
        db.collection(Collections.PAPERS)
                .document(paperEntity.getId())
                .set(paperEntity)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "added document paper with id " + paperEntity.getId()))
                .addOnFailureListener(e -> Log.d(TAG, "failed to add document paper: " + e));
    }
}
