package com.ange.demo.kotlin;

import android.support.v7.widget.RecyclerView;

import com.ange.demo.databinding.TestItemBinding;

/**
 * Created by ange on 2018/5/16.
 */

public class TestBindingHolder extends RecyclerView.ViewHolder{

    private TestItemBinding binding;

    public TestBindingHolder(TestItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public TestItemBinding getBinding() {
        return binding;
    }

    public void setBinding(TestItemBinding binding) {
        this.binding = binding;
    }
}
