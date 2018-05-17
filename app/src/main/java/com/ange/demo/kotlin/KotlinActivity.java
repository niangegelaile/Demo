package com.ange.demo.kotlin;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ange.demo.R;
import com.ange.demo.databinding.ActivityKotlinBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ange on 2018/5/16.
 */

public class KotlinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityKotlinBinding mBinder = DataBindingUtil.setContentView(this, R.layout.activity_kotlin);
        List<Person> persons=new ArrayList<>();
        for(int i=0;i<100;i++){
            persons.add(new Person("li"+i,R.drawable.rocket_anim));
        }

        mBinder.setPersons(persons);

    }
}
