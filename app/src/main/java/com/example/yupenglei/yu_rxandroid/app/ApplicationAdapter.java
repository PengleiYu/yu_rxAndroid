package com.example.yupenglei.yu_rxandroid.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yupenglei.yu_rxandroid.R;

import java.util.List;

/**
 * Created by yupenglei on 17/3/31.
 */

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.VH> {
    private List<AppInfo> mAppInfoList;

    public ApplicationAdapter(List<AppInfo> appInfoList) {
        mAppInfoList = appInfoList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,
                parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mAppInfoList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        public ImageView mIcon;
        public TextView mName;

        public VH(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mName = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
