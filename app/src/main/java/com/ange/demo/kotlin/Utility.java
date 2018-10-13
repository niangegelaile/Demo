package com.ange.demo.kotlin;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.ange.demo.Catalog;
import com.ange.demo.MainBindingAdapter;

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
    @BindingAdapter("bind:data_main")
    public static void setDataMain(RecyclerView recyclerView, List<Catalog> data){
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        final MainBindingAdapter adapter=new MainBindingAdapter(recyclerView.getContext(),data);
        adapter.setOnItemClickListener(new MainBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, Catalog catalog) {
                try {
                    Intent intent=new Intent(adapter.getContext(),Class.forName(catalog.getTarget()));
                    adapter.getContext().startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
