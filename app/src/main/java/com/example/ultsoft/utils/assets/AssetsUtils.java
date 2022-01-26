package com.example.ultsoft.utils.assets;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import com.example.ultsoft.utils.assets.item.AssetList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AssetsUtils {
    private Context context;

    public AssetsUtils(Context context) {
        this.context = context;
    }

    /*
    * This method return list of files from asset/images path
    * list with param:
    *    String fileName - file name
    *    Drawable skin - img
    * */
    public List<AssetList> getDrawableListFromAsset(){
        AssetManager myAssetManager = context.getAssets();
        List<AssetList> list = new ArrayList<>();

        try {
            String[] Files = myAssetManager.list("images"); // массив имён файлов
            for(String file : Files ){
                //i don't know what is that, but i see this 3 files in recycleView and in logs
                if(file.equals("android-logo-mask.png")
                    || file.equals("android-logo-shine.png")
                    || file.equals("clock_font.png")) {
                    continue;
                }else {
                    list.add(new AssetList(file, loadImageFromAsset(file)));
                }
            }

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * This method  return img from asset, need to know only path
    * */
    public Drawable loadImageFromAsset(String fileName) {
        try {
            // получаем входной поток
            InputStream ims = context.getAssets().open("images/"+fileName);
            // загружаем как Drawable
            Drawable img = Drawable.createFromStream(ims, null);
            return img;
        }
        catch(IOException ex) {
            return null;
        }
    }


}
