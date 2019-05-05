package com.unibs.zanotti.inforinvestigador.comments;

import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.ModelFactory;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public class ReplyCommentPresenter implements ReplyCommentContract.Presenter {
    private final IUserRepository userRepository;
    private final IPaperRepository paperRepository;
    private ReplyCommentContract.View mView;
    private CompositeDisposable disposables;

    public ReplyCommentPresenter(ReplyCommentContract.View mView,
                                 IPaperRepository paperRepository,
                                 IUserRepository userRepository) {
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
        this.mView = mView;
        this.mView.setPresenter(this);
        disposables = new CompositeDisposable();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        disposables.dispose();
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
}
