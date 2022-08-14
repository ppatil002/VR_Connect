package com.example.vrconnect.teamvelociracers;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;


public class Tab2 extends Fragment {

    PDFView pdfView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.tab2content, container, false);
        pdfView = (PDFView) pdfView.findViewById(R.id.pdfView);
        pdfView.fromAsset("P.pdf").load();
        return rootView;
    }


}