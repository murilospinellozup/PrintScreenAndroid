package com.example.murilo.printscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textPrint;
    private ImageView imageCopy;
    private Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();
    }

    private void setView() {
        textPrint = findViewById(R.id.textPrint);
        imageCopy = findViewById(R.id.imageCopy);
        btnShare = findViewById(R.id.btnShare);
    }

    public void letsPrint(View view) {
        imageCopy.setImageBitmap(PrintUtils.getBitmapFromView(textPrint));

        if (btnShare.getVisibility() != View.VISIBLE)
            btnShare.setVisibility(View.VISIBLE);
    }

    public void letShare(View view) {
        startShare();
    }

    private void startShare() {
        PrintUtils.printView(imageCopy, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PrintUtils.isRequestPermissionResult(requestCode, permissions, grantResults))
            startShare();
    }
}
