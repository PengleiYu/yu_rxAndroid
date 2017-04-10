package com.example.yupenglei.yu_rxandroid.fragment.ui.item7;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.yupenglei.yu_rxandroid.R;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by yupenglei on 17/4/10.
 */

public class NetWorkFragment extends Fragment {

    @BindView(R.id.arcProgress)
    ArcProgress mArcProgress;
    @BindView(R.id.download)
    Button mDownload;
    PublishSubject<Integer> mDownloadProgress;
    private Unbinder mUnbinder;
    private String mDestination;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_download, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);

        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void download() {
        changeButton(true);
        mDownloadProgress = PublishSubject.create();
        mDownloadProgress
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e("downloadProgress", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("downloadProgress", "onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e("downloadProgress", integer + "");
                        mArcProgress.setProgress(integer);
                    }
                });

        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                String url = "http://archive.blender.org/fileadmin/movies/softboy.avi";
                mDestination = "/sdcard/softboy.avi";
                boolean b = downloadFile(url, mDestination);
                if (b) {
                    subscriber.onNext(b);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable("Download failed!"));
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.e("download", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("download", "onError");
//                        Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT)
//                                .show();
                        changeButton(false);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        changeButton(false);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        File file = new File(mDestination);
                        intent.setDataAndType(Uri.fromFile(file), "video/avi");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });

    }

    private boolean downloadFile(String source, String destination) {
        boolean result = false;
        InputStream in = null;
        OutputStream out = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(source);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }
            int fileLength = connection.getContentLength();
            in = connection.getInputStream();
            out = new FileOutputStream(destination);

            byte[] buffer = new byte[4096];
            long total = 0;
            int count;
            while ((count = in.read(buffer)) != -1) {
                total += count;
                if (fileLength > 0) {
                    int currentProgress = (int) (total * 100 / fileLength);
                    mDownloadProgress.onNext(currentProgress);
                    Log.e("downloadFile", "progress " + currentProgress);
                }
                out.write(buffer, 0, count);
            }
            mDownloadProgress.onCompleted();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
            mDownloadProgress.onError(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                mDownloadProgress.onError(e);
            }
            if (connection != null) {
                connection.disconnect();
//                mDownloadProgress.onCompleted();
            }
        }
        return result;
    }

    private void changeButton(boolean downloading) {
        if (downloading) {
            mDownload.setText("Downloading");
            mDownload.setClickable(false);
            mArcProgress.setProgress(0);
        } else {
            mDownload.setText("Download");
            mDownload.setClickable(true);
        }
    }
}
