package com.example.androidhw3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainPageFragment extends Fragment {
	public static final String DATE = "۱۳ آبان ۱۳۹۳";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mainpage_pager_fragments, container, false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.date)).setText(args.getString(DATE));
        return rootView;
    }
}
