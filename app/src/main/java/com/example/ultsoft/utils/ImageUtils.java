package com.example.ultsoft.utils;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageUtils {
    private static OutputStream osPhoto;

    /*
    * This method save image in the ULTSoftPhoto dir with AbsolutePath
     * */
    public static void saveImage(Drawable image, String imageName) {
        // Retrieve the image from the res folder
        BitmapDrawable drawable = (BitmapDrawable)image;
        Bitmap bitmap = drawable.getBitmap();

        // Create a new folder
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()
                + "/ULTSoftPhoto/");
        if (!dir.exists()){
            dir.mkdirs();
        }

        // Create a name for the saved image
        File file = new File(dir,imageName);

        try {
            osPhoto = new FileOutputStream(file);
            // Compress into jpeg format image from 0% - 100%
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, osPhoto);
            osPhoto.flush();
            osPhoto.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
