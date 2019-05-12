package com.unibs.zanotti.inforinvestigador.comments;

import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.ModelFactory;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ReplyCommentPresenter extends BasePresenter<ReplyCommentContract.View>
        implements ReplyCommentContract.Presenter {

    private final IUserRepository userRepository;
    private final IPaperRepository paperRepository;

    public ReplyCommentPresenter(IPaperRepository paperRepository, IUserRepository userRepository) {
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addReply(Comment parentComment, String replyBody) {
        String currentUserId = userRepository.getCurrentUserId();
        disposables.add(userRepository.getUser(currentUserId)
                .flatMapSingle(user -> {
                    Comment replyComment = ModelFactory.createComment(parentComment.getPaperId(), replyBody, user.getName());
                    return paperRepository.saveUpdateComment(replyComment);
                })
                .flatMap(replyComment -> {
                    parentComment.addChildren(replyComment);
                    return paperRepository.saveUpdateComment(parentComment);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Comment>() {
                    @Override
                    public void onSuccess(Comment comment) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                })
        );
    }

    @Override
    public void onPresenterCreated() {
        // NO OP
    }

    @Override
    public void onStart() {

    }
}
