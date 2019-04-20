package com.unibs.zanotti.inforinvestigador.data.source.local;


import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.model.PaperSuggestion;

import java.util.ArrayList;
import java.util.List;

/**
 * A dummy Data Access Object for the possible paper suggestion table
 */
public class DummyPapersSuggestionsDAO {
    public List<PaperSuggestion> getSuggestions() {
        List<PaperSuggestion> result = new ArrayList<>();
        String  paperId = "1";
        String title = "This is the title of the paper";
        String authors = "Devis Bianchini, Marina Zanella, Pietro Baroni";
        String date = "Mar 2019 - ";
        String[] topics = new String[]{"Informatics", "Science", "Math"};
        String comment = "This is the comment made by the use user who shared the paper";
        String sharingUser = "Mario Relha";
        int sharingProfilePicture = R.drawable.test_researcher_7;

        PaperSuggestion suggestion_1 =
                new PaperSuggestion(
                        paperId,
                        title,
                        authors,
                        date,
                        topics,
                        comment,
                        sharingUser,
                        sharingProfilePicture
                );
        result.add(suggestion_1);

        paperId = "2";
        title = "This is the title of the second paper";
        authors = "Pippo Baudo, Nicola Adami, Marina Zanella";
        date = "Dec 2018 - ";
        topics = new String[]{"Informatics", "Science", "Math"};
        comment = "This is the comment made by the use user who shared the paper";
        sharingUser = "Maria Piras";
        sharingProfilePicture = R.drawable.test_researcher_1;
        result.add(
                new PaperSuggestion(
                        paperId,
                        title,
                        authors,
                        date,
                        topics,
                        comment,
                        sharingUser,
                        sharingProfilePicture
                )
        );

        paperId = "3";
        title = "This is the title of the third paper";
        authors = "Pippo Baudo, Nicola Adami, Marina Zanella";
        date = "May 2018 - ";
        topics = new String[]{"History", "Science", "Informatics", "Geography"};
        comment = "This is the comment made by the use user who shared the paper";
        sharingUser = "Teresa Sardinha";
        sharingProfilePicture = R.drawable.test_researcher_5;
        result.add(
                new PaperSuggestion(
                        paperId,
                        title,
                        authors,
                        date,
                        topics,
                        comment,
                        sharingUser,
                        sharingProfilePicture
                )
        );


        paperId = "4";
        title = "This is the title of the fourth paper";
        authors = "Pippo Baudo, Nicola Adami, Marina Zanella";
        date = "May 2018 - ";
        topics = new String[]{"History", "Science", "Informatics", "Geography"};
        comment = "This is the comment made by the use user who shared the paper";
        sharingUser = "Leonor Freitas";
        sharingProfilePicture = R.drawable.test_researcher_2;
        result.add(
                new PaperSuggestion(
                        paperId,
                        title,
                        authors,
                        date,
                        topics,
                        comment,
                        sharingUser,
                        sharingProfilePicture
                )
        );

        return result;
    }
}
