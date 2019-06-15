package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.base;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers.adapters.Listeners;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class FollowListBaseRecyclerViewAdapter
        extends RecyclerView.Adapter<FollowListBaseRecyclerViewAdapter.FollowListBaseViewHolder> {

    private static final String TAG = String.valueOf(FollowListBaseRecyclerViewAdapter.class);

    private final Listeners.FollowersListListener followersListListener;
    private final IUserRepository userRepository;
    private List<User> dataset;
    private String modelUserId;
    private List<Disposable> adapterDisposables;

    public FollowListBaseRecyclerViewAdapter(List<User> dataset,
                                             String modelUserId,
                                             IUserRepository userRepository,
                                             Listeners.FollowersListListener followersListListener) {
        this.dataset = dataset;
        this.modelUserId = modelUserId;
        this.userRepository = userRepository;
        this.followersListListener = followersListListener;
        adapterDisposables = new ArrayList<>();
    }

    @NonNull
    @Override
    public FollowListBaseRecyclerViewAdapter.FollowListBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_item_followers_list, parent, false);
        return new FollowListBaseRecyclerViewAdapter.FollowListBaseViewHolder(view, followersListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowListBaseRecyclerViewAdapter.FollowListBaseViewHolder holder, int position) {
        User user = dataset.get(position);
        Picasso.get()
                .load(user.getProfilePictureUri())
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(holder.getProfileImage());
        holder.getUserName().setText(user.getName());
        holder.getUserEmail().setText(user.getEmail());

        adapterDisposables.add(userRepository.isFollowing(userRepository.getCurrentUserId(), user.getId())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isFollowing) {
                        if (!user.getId().equals(userRepository.getCurrentUserId())) {
                            if (isFollowing) {
                                holder.getFollowButton().setVisibility(View.GONE);
                                if (holder.getFollowingButton().getVisibility() != View.VISIBLE) {
                                    ActivityUtils.animateViewWithFade(holder.getFollowingButton(), View.VISIBLE, 1f, 300);
                                }
                            } else {
                                holder.getFollowingButton().setVisibility(View.GONE);
                                if (holder.getFollowButton().getVisibility() != View.VISIBLE) {
                                    ActivityUtils.animateViewWithFade(holder.getFollowButton(), View.VISIBLE, 1f, 300);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage(), e);
                    }
                }));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setDataset(List<User> dataset) {
        this.dataset = dataset;
        this.notifyDataSetChanged();
    }

    public class FollowListBaseViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView userName;
        private TextView userEmail;
        private AppCompatButton followingButton;
        private AppCompatButton followButton;
        private MaterialCardView cardView;

        public FollowListBaseViewHolder(@NonNull View itemView, Listeners.FollowersListListener followersListListener) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.user_profile_image);
            userName = itemView.findViewById(R.id.item_following_list_user_name);
            userEmail = itemView.findViewById(R.id.item_following_list_user_email);
            followButton = itemView.findViewById(R.id.item_following_list_button_follow);
            followingButton = itemView.findViewById(R.id.item_following_list_button_following);
            cardView = itemView.findViewById(R.id.single_user_item_followers_list_card);

            // Setup listeners
            cardView.setOnClickListener(click ->
                    followersListListener.onCardClicked(dataset.get(getAdapterPosition())));
            followButton.setOnClickListener(click ->
                    followersListListener.onFollowButtonClicked(dataset.get(getAdapterPosition()).getId(), getAdapterPosition()));
            followingButton.setOnClickListener(click ->
                    followersListListener.onFollowingButtonClicked(dataset.get(getAdapterPosition()).getId(), getAdapterPosition()));
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
