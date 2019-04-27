package com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl;

public class DummyPaperShareLocalDao { //implements IDao<> {
    /*
    @Override
    public void save(PaperShare paperShare) {

    }

    @Override
    public void update(PaperShare paperShare, String[] params) {

    }

    @Override
    public void delete(PaperShare paperShare) {

    }

    @Override
    public List<PaperShare> getAll() {
        List<PaperShare> result = new ArrayList<>();
        Long paperShareId = 1l;
        Long paperId = 1l;
        String title = "This is the title of the paper";
        List<String> authors = Arrays.asList("Devis Bianchini", "Marina Zanella", "Pietro Baroni");
        String date = "Mar 2019 - ";
        List<String> topics = Arrays.asList("Informatics", "Science", "Math");
        String comment = "This is the comment made by the use user who shared the paper";
        String sharingUser = "Mario Relha";
        int sharingProfilePicture = R.drawable.test_researcher_7;

        PaperShare share_1 =
                new PaperShare(
                        paperShareId,
                        paperId,
                        title,
                        authors,
                        date,
                        topics,
                        comment,
                        sharingUser,
                        sharingProfilePicture,
                        null
                );
        result.add(share_1);

        paperShareId = 2l;
        paperId = 2l;
        title = "This is the title of the second paper";
        date = "Dec 2018 - ";
        comment = "This is the comment made by the use user who shared the paper";
        sharingUser = "Maria Piras";
        sharingProfilePicture = R.drawable.test_researcher_1;
        result.add(
                new PaperShare(
                        paperShareId,
                        paperId,
                        title,
                        authors,
                        date,
                        topics,
                        comment,
                        sharingUser,
                        sharingProfilePicture,
                        null
                )
        );

        paperShareId = 3l;
        paperId = 3l;
        title = "This is the title of the third paper";
        date = "May 2018 - ";
        comment = "This is the comment made by the use user who shared the paper";
        sharingUser = "Teresa Sardinha";
        sharingProfilePicture = R.drawable.test_researcher_5;
        result.add(
                new PaperShare(
                        paperShareId,
                        paperId,
                        title,
                        authors,
                        date,
                        topics,
                        comment,
                        sharingUser,
                        sharingProfilePicture,
                        null
                )
        );


        paperShareId = 4l;
        paperId = 4l;
        title = "This is the title of the fourth paper";
        date = "May 2018 - ";
        comment = "This is the comment made by the use user who shared the paper";
        sharingUser = "Leonor Freitas";
        sharingProfilePicture = R.drawable.test_researcher_2;
        result.add(
                new PaperShare(
                        paperShareId,
                        paperId,
                        title,
                        authors,
                        date,
                        topics,
                        comment,
                        sharingUser,
                        sharingProfilePicture,
                        null
                )
        );

        return result;
    }

    @Override
    public Optional<PaperShare> get(long id) {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("asd","sads",12,1l,new ArrayList<>()));

        return Optional.of(new PaperShare(
                1l,
                1l,
                "Title",
                new ArrayList<String>(),
                "ssd",
                new ArrayList<String>(),
                "sd",
                "asd",
                12,
                comments
        ));
    }
    */
}
