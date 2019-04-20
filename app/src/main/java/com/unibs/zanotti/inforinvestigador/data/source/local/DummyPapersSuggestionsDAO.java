package com.unibs.zanotti.inforinvestigador.data.source.local;


import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.model.PaperSuggestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A dummy Data Access Object for the possible paper suggestion table
 */
public class DummyPapersSuggestionsDAO {
    public List<PaperSuggestion> getSuggestions() {
        List<PaperSuggestion> result = new ArrayList<>();
        Long paperId = 1l;
        String title = "This is the title of the paper";
        List<String> authors = Arrays.asList("Devis Bianchini", "Marina Zanella", "Pietro Baroni");
        String date = "Mar 2019 - ";
        List<String> topics = Arrays.asList("Informatics", "Science", "Math");
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

        paperId = 2l;
        title = "This is the title of the second paper";
        date = "Dec 2018 - ";
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

        paperId = 3l;
        title = "This is the title of the third paper";
        date = "May 2018 - ";
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


        paperId = 4l;
        title = "This is the title of the fourth paper";
        date = "May 2018 - ";
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
