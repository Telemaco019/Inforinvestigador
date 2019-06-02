package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class FollowersRecyclerAdapter extends RecyclerView.Adapter<FollowersRecyclerAdapter.FollowersViewHolder> {
    private List<User> dataset;

    public FollowersRecyclerAdapter(List<User> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public FollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_item_following_list, parent, false);

        return new FollowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowersViewHolder holder, int position) {
        User user = dataset.get(position);
        Picasso.get()
                .load(user.getProfilePictureUri())
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(holder.getProfileImage());
        holder.getUserName().setText(user.getName());
        holder.getUserEmail().setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setDataset(List<User> dataset) {
        this.dataset = dataset;
        this.notifyDataSetChanged();
    }

    public class FollowersViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView userName;
        private TextView userEmail;
        private AppCompatButton followingButton;
        private AppCompatButton followButton;

        public FollowersViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.user_profile_image);
            userName = itemView.findViewById(R.id.item_following_list_user_name);
            userEmail = itemView.findViewById(R.id.item_following_list_user_email);
            followButton = itemView.findViewById(R.id.item_following_list_button_follow);
            followingButton = itemView.findViewById(R.id.item_following_list_button_following);
        }

        public CircleImageView getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(CircleImageView profileImage) {
            this.profileImage = profileImage;
        }

        public TextView getUserName() {
            return userName;
        }

        public void setUserName(TextView userName) {
            this.userName = userName;
        }

        public TextView getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(TextView userEmail) {
            this.userEmail = userEmail;
        }

        public AppCompatButton getFollowingButton() {
            return followingButton;
        }

        public void setFollowingButton(AppCompatButton followingButton) {
            this.followingButton = followingButton;
        }

        public AppCompatButton getFollowButton() {
            return followButton;
        }

        public void setFollowButton(AppCompatButton followButton) {
            this.followButton = followButton;
        }
    }
}
