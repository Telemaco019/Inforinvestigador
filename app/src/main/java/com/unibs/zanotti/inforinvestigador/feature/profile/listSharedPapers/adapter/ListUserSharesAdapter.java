package com.unibs.zanotti.inforinvestigador.feature.profile.listSharedPapers.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.model.FeedPaper;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class ListUserSharesAdapter extends RecyclerView.Adapter<ListUserSharesAdapter.MyViewHolder> {

    private List<FeedPaper> dataset;
    private OnPaperShareListener listener;

    public ListUserSharesAdapter(List<FeedPaper> dataset, OnPaperShareListener listener) {
        this.dataset = dataset;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_shared_paper, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FeedPaper paperShare = dataset.get(position);
        String paperTitle = StringUtils.isBlank(paperShare.getPaperTitle()) ? StringUtils.BLANK : paperShare.getPaperTitle();
        String paperAuthors = paperShare.getPaperAuthors().isEmpty() ? StringUtils.BLANK : String.join(", ", paperShare.getPaperAuthors());
        String paperDate = StringUtils.isBlank(paperShare.getPaperDate()) ? StringUtils.BLANK : paperShare.getPaperDate();
        String paperTopics = paperShare.getPaperTopics().isEmpty() ? StringUtils.BLANK : String.join(", ", paperShare.getPaperTopics());
        String paperComment = StringUtils.isBlank(paperShare.getSharingUserComment()) ? StringUtils.BLANK : paperShare.getSharingUserComment();
        String sharingUser = StringUtils.isBlank(paperShare.getSharingUserName()) ? StringUtils.BLANK : paperShare.getSharingUserName();

        holder.getTvPaperTitle().setText(paperTitle);
        holder.getTvPaperAuthors().setText(paperAuthors);
        holder.getTvPaperDate().setText(paperDate);
        holder.getTvPaperTopics().setText(paperTopics);
        holder.getTvPaperComment().setText(paperComment);
        holder.getTvSharingUser().setText(sharingUser);
        Picasso.get()
                .load(dataset.get(position).getSharingUserProfilePicture())
                .placeholder(R.drawable.user_profle_pic_placeholder)
                .fit()
                .centerCrop()
                .into(holder.ivSharingUserProfilePicture);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setDataset(List<FeedPaper> sharedPapers) {
        this.dataset = sharedPapers;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvPaperTitle;
        private final TextView tvPaperAuthors;
        private final TextView tvPaperDate;
        private final TextView tvPaperTopics;
        private final TextView tvPaperComment;
        private final TextView tvSharingUser;
        private final CircleImageView ivSharingUserProfilePicture;

        MyViewHolder(@NonNull View view) {
            super(view);
            tvPaperTitle = view.findViewById(R.id.shared_paper_title);
            tvPaperAuthors = view.findViewById(R.id.shared_paper_authors);
            tvPaperDate = view.findViewById(R.id.shared_paper_date);
            tvPaperTopics = view.findViewById(R.id.shared_paper_topics);
            tvPaperComment = view.findViewById(R.id.shared_paper_comment);
            tvSharingUser = view.findViewById(R.id.shared_paper_sharing_user_name);
            ivSharingUserProfilePicture = view.findViewById(R.id.shared_paper_sharing_user_picture);
        }

        TextView getTvPaperTitle() {
            return tvPaperTitle;
        }

        TextView getTvPaperAuthors() {
            return tvPaperAuthors;
        }

        TextView getTvPaperDate() {
            return tvPaperDate;
        }

        TextView getTvPaperTopics() {
            return tvPaperTopics;
        }

        TextView getTvPaperComment() {
            return tvPaperComment;
        }

        TextView getTvSharingUser() {
            return tvSharingUser;
        }

        @Override
        public void onClick(View v) {
            listener.onPaperSharedClicked(dataset.get(getAdapterPosition()).getPaperId());
        }
    }

    public interface OnPaperShareListener {
        void onPaperSharedClicked(String paperId);
    }
}
