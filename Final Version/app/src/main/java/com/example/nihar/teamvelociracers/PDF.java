package com.example.nihar.teamvelociracers;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class PDF extends AppCompatActivity {
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);


        PDFView pdfView = (PDFView)findViewById(R.id.pdfView);
        pdfView.fromAsset("P.pdf").load();
    }
}
