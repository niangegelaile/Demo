package com.ange.demo.kotlin;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by ange on 2018/5/16.
 */

public class Utility {
    @BindingAdapter("bind:image")
    public static void loadImage(ImageView image, int resId){
        image.setImageResource(resId);
    }

    @BindingAdapter("bind:data")
    public static void setData(RecyclerView recyclerView, List<Person> data){
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new TestBindingAdapter(recyclerView.getContext(), data));
    }

}
