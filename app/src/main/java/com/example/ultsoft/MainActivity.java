package com.example.ultsoft;

import static androidx.core.app.ActivityCompat.requestPermissions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ultsoft.ui.main.adapter.IPhotoAdapterListener;
import com.example.ultsoft.ui.main.adapter.PhotoAdapter;
import com.example.ultsoft.ui.main.item.PhotoItems;
import com.example.ultsoft.utils.PermissionUtils;
import com.example.ultsoft.utils.assets.AssetsUtils;
import com.example.ultsoft.utils.assets.item.AssetList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements IPhotoAdapterListener, View.OnClickListener {
    private RecyclerView rvPhoto;
    private PhotoAdapter photoAdapter;
    private ProgressBar pbPhoto;
    private Disposable photoDisposable = Disposables.empty();
    private ImageView  ivFullScreen;
    private SearchView svPhoto;

    private AssetsUtils assetsUtils;

    private List<PhotoItems> photoItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPhoto =  findViewById(R.id.rvPhoto);
        pbPhoto =  findViewById(R.id.pbPhoto);
        svPhoto = findViewById(R.id.svPhoto);
        ivFullScreen = findViewById(R.id.ivFullScreen);

        assetsUtils = new AssetsUtils(this);
        photoAdapter =  new PhotoAdapter(this,this, photoItems);

        ivFullScreen.setOnClickListener(this);

        // init activity
        initView();

        //init search
        initSearchView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivFullScreen:
                ivFullScreen.setVisibility(View.GONE);
                break;
        }
    }



    private void initView(){
        //after getting all photos from asset folder, set adapter
        photoDisposable = Completable.fromAction(() -> {
            //show progressBar
            pbPhoto.setVisibility(View.VISIBLE);
            //set photoItems from asset folder
            for(AssetList assetList : assetsUtils.getDrawableListFromAsset()){
                photoItems.add(new PhotoItems(assetList.getImage(),assetList.getImageName()));
            }

        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            //hide progressBar
                            pbPhoto.setVisibility(View.GONE);

                            //set adapter
                            rvPhoto.setAdapter(photoAdapter);
                        },
                        error -> {
                        }
                );

    }

    /*
    * This method initializes search view listener
     * */
    private void initSearchView(){

        svPhoto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // return list of items which contains the same symbols
            @Override
            public boolean onQueryTextChange(String newText) {
                photoAdapter.getFilter().filter(newText);
                return false;
            }
        });

    };


    @Override
    public void onStop() {
        super.onStop();
        //stop thread
        photoDisposable.dispose();
    }

    /*
     * show image on full screen
     * */
    @Override
    public void onImageViewClicked(Drawable image) {
        ivFullScreen.setVisibility(View.VISIBLE);
        ivFullScreen.setImageDrawable(image);
    }

    /*
    * This method request WRITE/READ_EXTERNAL_STORAGE
     * */
    @Override
    public void getExternalPermissions() {
        PermissionUtils.getExternalPermission(this);
    }
}