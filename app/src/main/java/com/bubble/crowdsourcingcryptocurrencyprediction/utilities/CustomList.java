package com.bubble.crowdsourcingcryptocurrencyprediction.utilities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bubble.crowdsourcingcryptocurrencyprediction.R;
import com.bubble.crowdsourcingcryptocurrencyprediction.fragments.NewsFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final String[] imageId;
    private final String[] urls;
    public CustomList(Activity context,
                      String[] web, String[] imageId, String[] urls) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.urls = urls;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);
        Log.i("check!",imageId[position]);
        try {
            if(!imageId[position].isEmpty()) {
                ImageLoader imageLoader = ImageLoader.getInstance();
                DisplayImageOptions opts = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                        .defaultDisplayImageOptions(opts).build();
                ImageLoader.getInstance().init(config);
                imageLoader.displayImage(imageId[position].replace("\"",""), imageView);
                /*URL url = new URL(imageId[position]);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                imageView.setImageBitmap(bmp);
                Log.i("check!","check!");*/
            }
            //imageView.setImageResource(imageId[position]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowView;
    }

}
