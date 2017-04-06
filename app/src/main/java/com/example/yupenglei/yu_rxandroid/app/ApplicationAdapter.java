package com.example.yupenglei.yu_rxandroid.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yupenglei.yu_rxandroid.R;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yupenglei on 17/3/31.
 */

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.VH> {
    private List<AppInfo> mAppInfoList;

    public ApplicationAdapter(List<AppInfo> appInfoList) {
        mAppInfoList = appInfoList;
    }

    public void addAppInfo(List<AppInfo> appInfoList) {
        mAppInfoList.clear();
        mAppInfoList.addAll(appInfoList);
        notifyDataSetChanged();
    }

    public void addAppInfo(AppInfo appInfo, int position) {
        if (position < 0) {
            position = 0;
        }
        mAppInfoList.add(position, appInfo);
        notifyItemChanged(position);
    }

    public void clear(){
        mAppInfoList.clear();
        notifyDataSetChanged();
    }
    public void addAppInfo(AppInfo appInfo) {
        mAppInfoList.add(appInfo);
        notifyItemChanged(mAppInfoList.size() - 1);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,
                parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        holder.mName.setText(mAppInfoList.get(position).getName());
        getBitmap(mAppInfoList.get(position).getIcon())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        holder.mIcon.setImageBitmap(bitmap);
                    }
                });

    }

    private Observable<Bitmap> getBitmap(final String filepath) {

        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppInfoList == null ? 0 : mAppInfoList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView mIcon;
        TextView mName;

        VH(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mName = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
