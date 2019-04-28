package com.unibs.zanotti.inforinvestigador.utils;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.data.remote.PaperFirebaseRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;

import java.util.Arrays;

public class FirebaseUtils {
    public static void populatePapersCollection() {
        PaperFirebaseRepository paperFirebaseRepository = PaperFirebaseRepository.getInstance(FirebaseFirestore.getInstance());
        Paper paperToWrite = new Paper(
                null,
                "Title of the paper 1",
                Arrays.asList("Author 1", "Author 2", "Author 3"),
                "Mar 2019",
                "DOI",
                100,
                Arrays.asList("Topic 1", "Topic 2", "Topic 3"),
                "This is the abstract of the paper",
                "Publisher of the paper",
                "1l",
                "Comment of the user who shared the paper");
        paperFirebaseRepository.savePaper(paperToWrite);
    }
}
