package com.ange.demo.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ange.demo.R;

/**
 * Created by ange on 2017/7/29.
 */

public class LifecycleFragment extends Fragment {

    TextView ftv,atv;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setFragmentLife("f:onAttach");
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFragmentLife("f:onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_lifecycle,container,false);
        ftv= (TextView) view.findViewById(R.id.tv_f_message);
        atv= (TextView) view.findViewById(R.id.tv_a_message);
        setFragmentLife("f:onCreateView");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setFragmentLife("f:onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        setFragmentLife("f:onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        setFragmentLife("f:onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        setFragmentLife("f:onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setFragmentLife("f:onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setFragmentLife("f:onDestroy");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        setFragmentLife("f:onDetach");
    }


    public void setFragmentLife(String msg){
//        if(ftv!=null){
//            ftv.setText(msg);
//        }
//        Log.d("TAG", "setFragmentLife: "+msg);

    }

    public void setActivityLife(String msg){
//        if(atv!=null){
//            atv.setText(msg);
//        }
        Log.d("TAG", "setActivityLife: "+msg);
    }






}
