package com.ange.demo.kotlin;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ange.demo.R;
import com.ange.demo.databinding.TestItemBinding;

import java.util.List;

/**
 * Created by ange on 2018/5/16.
 */
public class TestBindingAdapter extends RecyclerView.Adapter<TestBindingHolder> {

    private List<Person> data;

    private Context context;

    public TestBindingAdapter(Context context, List<Person> list) {
        this.context = context;
        this.data = list;
    }

    @Override
    public TestBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TestItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.test_item, parent, false);
        return new TestBindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(TestBindingHolder holder, int position) {
        holder.getBinding().setPerson(data.get(position));
        // 立刻刷新界面
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}

