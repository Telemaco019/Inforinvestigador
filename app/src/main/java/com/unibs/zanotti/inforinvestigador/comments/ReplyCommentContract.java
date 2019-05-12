package com.unibs.zanotti.inforinvestigador.comments;

import com.unibs.zanotti.inforinvestigador.baseMVP.BaseContract;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;

public interface ReplyCommentContract {
    interface Presenter extends BaseContract.Presenter<View> {
        void addReply(Comment parentComment, String replyBody);
    }

    interface View extends BaseContract.View {

    }
}
