package com.example.androidhw3;

import com.example.swipetest.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SplashFragment extends Fragment {
	public static final String STUDENT_NUMBER = "90123456";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.splash_fragments, container, false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.student_number)).setText(args.getString(STUDENT_NUMBER));
        return rootView;
    }
}
