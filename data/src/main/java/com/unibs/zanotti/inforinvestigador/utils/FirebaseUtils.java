package com.unibs.zanotti.inforinvestigador.utils;

import com.unibs.zanotti.inforinvestigador.data.remote.PaperFirebaseRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;

import java.util.ArrayList;
import java.util.Arrays;

public class FirebaseUtils {
    public static void populatePapersCollection() {
        PaperFirebaseRepository paperFirebaseRepository = new PaperFirebaseRepository();
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
                1l,
                "Comment of the user who shared the paper",
                Arrays.asList(
                        new Comment("Body", "Author", 1, null, new ArrayList<>()),
                        new Comment("Body 2", "Author", 10, null, new ArrayList<>()))
        );

        paperFirebaseRepository.savePaper(paperToWrite);
    }
}
