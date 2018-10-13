package com.ange.demo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ange.demo.databinding.MainItemBinding;
import com.ange.demo.databinding.TestItemBinding;
import com.ange.demo.kotlin.Person;
import com.ange.demo.kotlin.TestBindingHolder;

import java.util.List;

/**
 * Created by ange on 2018/5/16.
 */
public class MainBindingAdapter extends RecyclerView.Adapter<MainBindingHolder> {

    private List<Catalog> data;

    private Context context;

    public Context getContext() {
        return context;
    }

    public MainBindingAdapter(Context context, List<Catalog> list) {
        this.context = context;
        this.data = list;
    }

    @Override
    public MainBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_item, parent, false);
        return new MainBindingHolder(binding);

    }

    @Override
    public void onBindViewHolder(MainBindingHolder holder, final int position) {
        holder.getBinding().setCatalog(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(position,data.get(position));
                }
            }
        });
        // 立刻刷新界面
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int pos,Catalog catalog);
    }
}

