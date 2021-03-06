package com.example.ultsoft.ui.main.adapter;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ultsoft.MainActivity;
import com.example.ultsoft.R;
import com.example.ultsoft.ui.main.item.PhotoItems;
import com.example.ultsoft.utils.ImageUtils;
import com.example.ultsoft.utils.PermissionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> implements Filterable {
    private final LayoutInflater inflater;
    private List<PhotoItems> photoItems;
    private List<PhotoItems> photoItemsFilter;
    private IPhotoAdapterListener listener;
    private Context context;

    public PhotoAdapter(Context context, IPhotoAdapterListener listener, List<PhotoItems> photoItems) {
        this.context = context;
        this.listener = listener;
        this.photoItems = photoItems;
        this.photoItemsFilter = photoItems;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PhotoItems photoItem = photoItems.get(position);
        //set photo img and name
        holder.ivImage.setImageDrawable(photoItem.getImage());
        holder.txtName.setText(photoItem.getImageName());

        //set fullscreen when image clicked
        holder.ivImage.setOnClickListener(view -> {
            listener.onImageViewClicked(photoItem.getImage());
        });

        //save image when button clicked
        holder.ibDownload.setOnClickListener(view -> {
            // if external permission on return - true
            //                       off return - false
            if(PermissionUtils.isExternalPermissionOn(context)){
                listener.getExternalPermissions();
            }else {
                ImageUtils.saveImage(photoItem.getImage(),photoItem.getImageName());
                Toast.makeText(context, context.getString(R.string.toast_txt_img_loaded), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return photoItems.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter =  new Filter() {

        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PhotoItems> filteredList = new ArrayList<>();
            // if - search line is empty return full list
            // else - return a list that contains coincidences
            if(constraint.toString().isEmpty()){
                filteredList.addAll(photoItemsFilter);
            }else {
                for(PhotoItems photoItem : photoItemsFilter){
                    if(photoItem.getImageName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(photoItem);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        //run on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            photoItems = (List<PhotoItems>) results.values;

            // update  recycle view
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView txtName;
        ImageButton ibDownload;

        ViewHolder(View view){
            super(view);
            ivImage = view.findViewById(R.id.ivImage);
            txtName = view.findViewById(R.id.txtName);
            ibDownload = view.findViewById(R.id.ibDownload);
        }
    }
}