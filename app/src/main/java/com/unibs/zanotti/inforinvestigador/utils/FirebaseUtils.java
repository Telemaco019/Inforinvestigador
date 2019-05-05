package com.unibs.zanotti.inforinvestigador.utils;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unibs.zanotti.inforinvestigador.data.remote.PaperFirebaseRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;

import java.util.Arrays;

public class FirebaseUtils {
    public static final String LOG_MSG_STANDARD_SAVE_ERROR = "failed to save in Firestore %s with id %s:\n%s";
    public static final String LOG_MSG_STANDARD_READ_ERROR = "failed to read %s from Firestore:\n%s";
    public static final String LOG_MSG_STANDARD_SINGLE_READ_SUCCESS = "%s with id %s read from Firestore";
    public static final String LOG_MSG_STANDARD_READ_SUCCESS = "reading of %s from Firestore successfully completed";
    public static final String LOG_MSG_STANDARD_WRITE_SUCCESS = "%s with id %s saved in Firestore";

    public static void populatePapersCollection() {
        PaperFirebaseRepository paperFirebaseRepository = PaperFirebaseRepository.getInstance(FirebaseFirestore.getInstance());
        Paper paperToWrite = new Paper(
                "tcgwxDNtfKOwegm5auUJ",
                "Title of the paper 1",
                Arrays.asList("Author 1", "Author 2", "Author 3"),
                "Mar 2019",
                "DOI",
                100,
                Arrays.asList("Topic 1", "Topic 2", "Topic 3"),
                "This is the abstract of the paper",
                "Publisher of the paper",
                "AuREy18SlNOt9VBG8beLkwN2WNi2",
                "Comment of the user who shared the paper");
        paperFirebaseRepository.saveUpdatePaper(paperToWrite).subscribe();
    }
}
