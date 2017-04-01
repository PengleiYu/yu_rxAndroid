package com.example.yupenglei.yu_rxandroid.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Locale;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by yupenglei on 17/3/31.
 */
@Accessors(prefix = "m")
public class AppInfoRich implements Comparable<Object> {

    @Setter
    private String mName = null;

    private Context mContext;
    @Getter
    private ResolveInfo mResolveInfo;
    @Getter
    private ComponentName mComponentName;
    @Getter
    private PackageInfo mPackageInfo;
    @Getter
    private Drawable mIcon;

    public AppInfoRich(Context context, ResolveInfo resolveInfo) {
        mContext = context;
        mResolveInfo = resolveInfo;
        mComponentName = new ComponentName(resolveInfo.activityInfo.packageName,
                resolveInfo.activityInfo.name);
        mIcon = resolveInfo.loadIcon(mContext.getPackageManager());
        try {
            mPackageInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getPackageName() {
        return mResolveInfo.activityInfo.packageName;
    }

    public String getName() {
        if (mName != null) {
            return mName;
        } else {
            try {
                return getNameFromResolveInfo(mResolveInfo);
            } catch (PackageManager.NameNotFoundException e) {
                return getPackageName();
            }
        }
    }

    public long getLastUpdateTime() {
        return mPackageInfo == null ? 0 : mPackageInfo.lastUpdateTime;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return getName().compareTo(((AppInfoRich) o).getName());
    }

    //// TODO: 17/3/31 resolveInfo
    private String getNameFromResolveInfo(ResolveInfo ri) throws PackageManager
            .NameNotFoundException {
        String name = ri.resolvePackageName;
        if (ri.activityInfo != null) {
            Resources res = mContext.getPackageManager().getResourcesForApplication(ri
                    .activityInfo.applicationInfo);
            Resources engRes = getEnglishRessources(res);

            if (ri.activityInfo.labelRes != 0) {
                name = engRes.getString(ri.activityInfo.labelRes);

                if (TextUtils.isEmpty(name)) {
                    name = res.getString(ri.activityInfo.labelRes);
                }

            } else {
                name = ri.activityInfo.applicationInfo.loadLabel(mContext.getPackageManager())
                        .toString();
            }
        }
        return name;
    }

    private Resources getEnglishRessources(Resources standardResources) {
        AssetManager assets = standardResources.getAssets();
        DisplayMetrics metrics = standardResources.getDisplayMetrics();
        Configuration config = new Configuration(standardResources.getConfiguration());
        config.locale = Locale.US;
        return new Resources(assets, metrics, config);
    }
}
