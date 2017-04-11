package com.example.yupenglei.yu_rxandroid.fragment.ui.item8;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yupenglei.yu_rxandroid.R;
import com.example.yupenglei.yu_rxandroid.fragment.ui.item8.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yupenglei on 17/4/10.
 */

public class StackOverflowAdapter extends RecyclerView.Adapter<StackOverflowAdapter.VH> {
    private List<User> mUsers;
    private Context mContext;

    public StackOverflowAdapter(List<User> users, Context context) {
        mUsers = users;
        mContext = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_stack_overflow, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.setUser(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setUsers(List<User> users) {
        mUsers.clear();
        mUsers.addAll(users);
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.user_image)
        ImageView mUserImage;
        @BindView(R.id.user_weather)
        ImageView mUserWeather;
        @BindView(R.id.user_name)
        TextView mUserName;
        @BindView(R.id.user_location)
        TextView mUserLocation;
        @BindView(R.id.user_reputation)
        TextView mUserReputation;

        VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setUser(User user) {
            Log.e(">>>", String.format("city: %s", user.getLocation()));
            mUserName.setText(user.getDisplayName());
            mUserLocation.setText(user.getLocation());
            mUserReputation.setText(String.valueOf(user.getReputation()));
            Glide.with(mContext).load(user.getProfileImage()).into(mUserImage);
        }
    }
}
