package com.ta.debam.deteksiekspresitugasakhirfinal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ta.debam.deteksiekspresitugasakhirfinal.R;

/**
 * Created by Debam on 4/29/2016.
 */
public class IntroFrag extends Fragment {
    private static final String PAGE = "page";
    private int mPage;

    public static IntroFrag newInstance(int page){
        IntroFrag in = new IntroFrag();
        Bundle b = new Bundle();

        b.putInt(PAGE, page);
        in.setArguments(b);

        return in;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!getArguments().containsKey("page"))
            throw new RuntimeException("ERROR PAGGING");

        mPage = getArguments().getInt(PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId;

        switch (mPage){
            case 0:
                layoutId = R.layout.intro1_fragment;
                break;
            case 1:
                layoutId = R.layout.intro2_fragment;
                break;
            case 2:
                layoutId = R.layout.intro3_fragment;
                break;
            default:
                layoutId = R.layout.intro4_fragment;

        }

        View v = getActivity().getLayoutInflater().inflate(layoutId,container,false);
        v.setTag(PAGE);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
