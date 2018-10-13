package com.ange.demo;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ange.demo.databinding.MainItemBinding;
import com.ange.demo.databinding.TestItemBinding;

/**
 * Created by ange on 2018/5/16.
 */

public class MainBindingHolder extends RecyclerView.ViewHolder{

    private MainItemBinding binding;

    public MainBindingHolder(MainItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public MainItemBinding getBinding() {
        return binding;
    }

    public void setBinding(MainItemBinding binding) {
        this.binding = binding;
    }
}
