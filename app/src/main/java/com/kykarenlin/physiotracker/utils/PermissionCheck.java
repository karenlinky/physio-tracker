package com.kykarenlin.physiotracker.utils;

import static com.kykarenlin.physiotracker.MainActivity.NOTIFICATION_PERMISSION_CODE;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper.TrackerNotifPreferences;

import java.util.ArrayList;
import java.util.Arrays;

public class PermissionCheck {
    private static PermissionCheck permissionCheck;
    private Context context;

    private FragmentActivity fragmentActivity;

    private PermissionCheck(Context context, FragmentActivity fragmentActivity) {
        this.context = context;
        this.fragmentActivity = fragmentActivity;
    }
    public synchronized static PermissionCheck getInstance(Context context, FragmentActivity fragmentActivity) {
        if (permissionCheck == null) {
            permissionCheck = new PermissionCheck(context, fragmentActivity);
        }
        return permissionCheck;
    }

    public boolean getPermissions(String[] permissions, int requestCode, String message) {
        ArrayList<String> arrPermissionNotGranted = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                arrPermissionNotGranted.add(permission);
            }
        }
        final String[] permissionsNotGranted = Arrays.copyOf(arrPermissionNotGranted.toArray(), arrPermissionNotGranted.toArray().length, String[].class);
        if (arrPermissionNotGranted.size() > 0) {
            boolean shouldShowRationale = false;
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this.fragmentActivity, permission)) {
                    shouldShowRationale = true;
                    break;
                }
            }
            if (shouldShowRationale) {
            new AlertDialog.Builder(context)
                .setTitle("Permission Required")
                .setMessage(message)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(fragmentActivity, permissionsNotGranted, requestCode);
                    }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
            } else {
                ActivityCompat.requestPermissions(this.fragmentActivity, permissionsNotGranted, requestCode);
            }
            return false;
        }
        return true;
    }
}
