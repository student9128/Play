package com.kevin.play.manager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.kevin.play.R;
import com.kevin.play.base.BaseActivity;
import com.kevin.play.base.BaseDialog;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 2018/12/28<br/>
 * Blog:http://student9128.top/<br/>
 * Describe:<br/>
 */
public class PermissionManager {
    public static boolean hasPermission(Context context, String permissionName) {
        int permissionCheck = ContextCompat.checkSelfPermission(context, permissionName);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(final BaseActivity activity, final int requestCode, int rationaleResId, final String... permissionName) {
        List<String> shouldShowRationale = getShouldShowRequestPermissionRationale(activity, permissionName);
        if (shouldShowRationale != null && shouldShowRationale.size() > 0) {
            if (rationaleResId <= 0) {
                rationaleResId = R.string.permission_common_rationale;
            }
            activity.showDialog(-1, rationaleResId, new BaseDialog.DialogButtonClickListener() {
                @Override
                public void onLeftBtnClick(@NotNull DialogInterface dialog) {
                }

                @Override
                public void onRightBtnClick(@NotNull DialogInterface dialog) {
                    ActivityCompat.requestPermissions(activity, permissionName, requestCode);

                }
            });
        } else {
            ActivityCompat.requestPermissions(activity, permissionName, requestCode);
//            activity.startAppSettings();
        }

    }

    private static List<String> getShouldShowRequestPermissionRationale(@NonNull BaseActivity activity, String... permissionNames) {
        if (activity == null || permissionNames == null) {
            return null;
        }

        List<String> permissions = new ArrayList<>();
        for (String permission : permissionNames) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                permissions.add(permission);
            }
        }

        return permissions;
    }

}
