package com.xinlan.circleviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xinlan.discview.DiscView;

/**
 * Created by panyi on 2015/8/6.
 */
public class SubFragment extends Fragment implements View.OnClickListener {

    private int mLayoutId;
    private Button testBtn;
    private DiscView mDiscView;

    public static SubFragment newInstance(int layoutId) {
        SubFragment frg = new SubFragment();
        frg.mLayoutId = layoutId;
        return frg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(mLayoutId,null);
        testBtn = (Button)mainView.findViewById(R.id.btn);
        mDiscView = (DiscView)mainView.findViewById(R.id.disc_view);
        testBtn.setOnClickListener(this);
        return mainView;
    }

    @Override
    public void onClick(View v) {
        mDiscView.setValue(330);
    }
}//end class
