//package com.unibs.zanotti.inforinvestigador.data.local.dao.impl;
//
//import com.unibs.zanotti.inforinvestigador.data.local.dao.IPaperLocalDao;
//import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//public class DummyPaperLocalDao implements IPaperLocalDao {
//    @Override
//    public void save(Paper paper) {
//        // Do nothing
//    }
//
//    @Override
//    public void update(Paper paper, String[] params) {
//        // Do nothing
//    }
//
//    @Override
//    public void delete(Paper paper) {
//        // Do nothing
//    }
//
//    @Override
//    public List<Paper> getAll() {
//        ArrayList<Paper> papers = new ArrayList<>();
//        papers.add(new Paper("1",
//                "Title",
//                Arrays.asList("Author 1", "Author 2"),
//                "Mar 2019",
//                "11023ff0123",
//                23,
//                Arrays.asList("Topic 1", "Topic 2"),
//                "This is the long long long abstract",
//                "IEEE",
//                "1l",
//                "THis is the comment of the user who shared the paper"));
//
//        papers.add(new Paper("2",
//                "Title",
//                Arrays.asList("Author 1", "Author 2"),
//                "Mar 2019",
//                "11023ff0123",
//                23,
//                Arrays.asList("Topic 1", "Topic 2"),
//                "This is the long long long abstract",
//                "IEEE",
//                "1l",
//                "THis is the comment of the user who shared the paper"));
//
//        papers.add(new Paper("3",
//                "Title",
//                Arrays.asList("Author 1", "Author 2"),
//                "Mar 2019",
//                "11023ff0123",
//                23,
//                Arrays.asList("Topic 1", "Topic 2"),
//                "This is the long long long abstract",
//                "IEEE",
//                "1l",
//                "THis is the comment of the user who shared the paper"));
//        return papers;
//    }
//
//    @Override
//    public Optional<Paper> get(String id) {
//        Paper paper = new Paper("1",
//                "Title",
//                Arrays.asList("Author 1", "Author 2"),
//                "Mar 2019",
//                "11023ff0123",
//                23,
//                Arrays.asList("Topic 1", "Topic 2"),
//                "This is the long long long abstract",
//                "IEEE",
//                "1l",
//                "THis is the comment of the user who shared the paper");
//        return Optional.of(paper);
//    }
//}
