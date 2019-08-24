package com.unibs.zanotti.inforinvestigador.services.retrofit.impl;

import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import com.unibs.zanotti.inforinvestigador.domain.model.crossref.Example;
import com.unibs.zanotti.inforinvestigador.domain.model.crossref.Message;
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import com.unibs.zanotti.inforinvestigador.services.retrofit.ICrossrefController;
import com.unibs.zanotti.inforinvestigador.services.retrofit.ICrossrefService;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class CrossrefService implements ICrossrefService {
    private static final String BASE_URL = "https://api.crossref.org/";
    private Retrofit retrofit;

    @Override
    public Single<Paper> getPaper(String doi) {
        ICrossrefController controller = getRetrofitInstance().create(ICrossrefController.class);
        return controller.getPaper(doi).map(this::convertExampleToPaper);
    }

    private Paper convertExampleToPaper(Example example) {
        Paper paper = new Paper();
        Message message = example.getMessage();

        // Add  authors
        if (message.getAuthor() != null) {
            message.getAuthor().forEach(author -> {
                if (StringUtils.isNotBlank(author.getGiven()) && StringUtils.isNotBlank(author.getFamily())) {
                    paper.addPaperAuthor(String.format("%s %s", author.getGiven(), author.getFamily()));
                }
            });
        }
        // Add DOI
        paper.setPaperDoi(message.getDOI());
        // Add citations
        if (message.getReferenceCount() != null) {
            paper.setPaperCitations(Math.toIntExact(message.getReferenceCount()));
        }
        // Add title
        if (message.getTitle() != null && !message.getTitle().isEmpty()) {
            paper.setPaperTitle(message.getTitle().get(0));
        }
        // Add date
        if (message.getCreated().getTimestamp() != null) {
            paper.setPaperDate(DateUtils.fromEpochTimestampMillis(message.getCreated().getTimestamp()).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        } else if (message.getCreated().getDateTime() != null) {
            paper.setPaperDate(message.getCreated().getDateTime());
        }
        // Add publisher
        if (StringUtils.isNotBlank(message.getPublisher())) {
            paper.setPaperPublisher(message.getPublisher());
        }
        // Add abstract
        if (StringUtils.isNotBlank(message.getAbstract())) {
            paper.setPaperAbstract(message.getAbstract());
        }
        // Add source
        if (message.getLink() != null && !message.getLink().isEmpty()) {
            paper.setURL(message.getLink().get(0).getURL());
        }

        return paper;
    }

    private Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
