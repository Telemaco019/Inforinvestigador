package com.unibs.zanotti.inforinvestigador.comments;

import com.unibs.zanotti.inforinvestigador.BasePresenter;
import com.unibs.zanotti.inforinvestigador.BaseView;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;

public interface ReplyCommentContract {
    interface Presenter extends BasePresenter {
        void stop();
        void addReply(Comment parentComment, String replyBody);
    }

    interface View extends BaseView<Presenter> {

    }
}
