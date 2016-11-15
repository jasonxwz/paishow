package com.example.asus.paishow.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by ssnn on 2016/11/3.
 */

public class AndroidLifecycleUtils {

    public AndroidLifecycleUtils() {
    }

    public static boolean canLoadImage(Fragment fragment) {
        if(fragment == null) {
            return true;
        } else {
            FragmentActivity activity = fragment.getActivity();
            return canLoadImage((Activity)activity);
        }
    }

    public static boolean canLoadImage(Context context) {
        if(context == null) {
            return true;
        } else if(!(context instanceof Activity)) {
            return true;
        } else {
            Activity activity = (Activity)context;
            return canLoadImage(activity);
        }
    }

    public static boolean canLoadImage(Activity activity) {
        if(activity == null) {
            return true;
        } else {
            boolean destroyed = Build.VERSION.SDK_INT >= 17 && activity.isDestroyed();
            return !destroyed && !activity.isFinishing();
        }
    }
}
