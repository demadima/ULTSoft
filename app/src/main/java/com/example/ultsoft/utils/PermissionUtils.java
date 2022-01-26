package com.example.ultsoft.utils;

import static com.example.ultsoft.utils.Constants.Permissions.REQUEST_CODE_EXTERNAL_PERMISSION;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class PermissionUtils {

    /*
    * This method check  is permission  are allowed or not
     * */
    public static boolean isExternalPermissionOn(Context context){
        int permissionReadStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionReadStatus != PackageManager.PERMISSION_GRANTED
                && permissionWriteStatus != PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;
        }
    }

    /*
     * This method request WRITE/READ_EXTERNAL_STORAGE
     * */
    public static void getExternalPermission(Activity activity) {
        activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_EXTERNAL_PERMISSION);
    }

}
