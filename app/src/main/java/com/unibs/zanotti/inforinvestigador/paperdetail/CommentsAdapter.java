package com.unibs.zanotti.inforinvestigador.paperdetail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;

class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    private CommentLikeListener commentLikeListener;
    private List<Comment> dataset;

    class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_comment_author;
        private TextView tv_comment_body;
        private TextView tv_comment_score;
        private TextView tv_comment_date;
        private ImageView ib_like;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_comment_author = itemView.findViewById(R.id.tv_comment_author);
            tv_comment_body = itemView.findViewById(R.id.tv_comment_body);
            tv_comment_score = itemView.findViewById(R.id.tv_comment_score);
            tv_comment_date = itemView.findViewById(R.id.tv_comment_date);
            ib_like = itemView.findViewById(R.id.ib_comment_like);
        }

        TextView getTv_comment_author() {
            return tv_comment_author;
        }

        TextView getTv_comment_body() {
            return tv_comment_body;
        }

        TextView getTv_comment_score() {
            return tv_comment_score;
        }

        TextView getTv_comment_date() {
            return tv_comment_date;
        }

        public ImageView getIb_like() {
            return ib_like;
        }

        public void setIb_like(ImageView ib_like) {
            this.ib_like = ib_like;
        }
    }

    public CommentsAdapter(List<Comment> dataset, CommentLikeListener commentLikeListener) {
        this.dataset = dataset;
        this.commentLikeListener = commentLikeListener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = dataset.get(position);
        holder.getTv_comment_author().setText(comment.getAuthor());
        holder.getTv_comment_body().setText(comment.getBody());
        holder.getTv_comment_score().setText(String.valueOf(comment.getLikesCount()));
        holder.getTv_comment_date().setText(DateUtils.elapsedTime(comment.getDateTime(), LocalDateTime.now(), "now"));

        holder.getIb_like().setActivated(comment.isLikedByCurrentUser());
        holder.getIb_like().setOnClickListener(e -> commentLikeListener.onLikeClicked(comment));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    /**
     * Update the dataset of the adapter with the comment provided as argument. After updating the dataset, update the
     * view accordingly as well.
     * <p>
     * If the dataset already contains a comment with the same id of the comment provided as argument, then update
     * the former comment with the new one, otherwise add the new comment to the dataset.
     * </p>
     *
     * @param comment
     */
    public void updateDataset(Comment comment) {
        for (int i = 0; i < dataset.size(); i++) {
            if (dataset.get(i).getId().equals(comment.getId())) {
                dataset.set(i, comment);
                this.notifyItemChanged(i);
                return;
            }
        }
        dataset.add(0, comment);
        this.notifyItemInserted(0);
    }

    interface CommentLikeListener {
        void onLikeClicked(Comment comment);
    }
}
