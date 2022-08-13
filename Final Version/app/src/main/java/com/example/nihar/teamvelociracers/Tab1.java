package com.example.nihar.teamvelociracers;

/**
 * Created by nihar on 19/9/18.
 */
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nihar on 17/9/18.
 */

public class Tab1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1content, container, false);

        return rootView;
    }
}

