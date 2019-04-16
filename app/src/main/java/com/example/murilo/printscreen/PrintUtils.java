package com.example.murilo.printscreen;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class PrintUtils {

    private static final int REQUEST_PERMISSION = 12;
    private static final String PERMISSION_PAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    private static Uri getImageUri(Activity activity, Bitmap inImage) {
        if (checkPrintPermission(activity)) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), inImage, "Title", null);
            return Uri.parse(path);
        }

        return null;
    }

    private static Bitmap convertImageViewToBitmap(ImageView v) {
        return ((BitmapDrawable) v.getDrawable()).getBitmap();
    }

    private static boolean checkPrintPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23)
            if (ContextCompat.checkSelfPermission(activity, PERMISSION_PAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{PERMISSION_PAGE}, REQUEST_PERMISSION);
                return false;

            } else
                return true;
        else
            return true;
    }

    public static void printView(ImageView imageCopy, Activity activity) {

        Uri imageUri = getImageUri(activity, convertImageViewToBitmap(imageCopy));

        if (imageUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Print");
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setType("image/jpeg");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivity(Intent.createChooser(shareIntent, "send"));
        }
    }

    public static boolean isRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION)
            for (int i = 0; i < permissions.length; i++)
                if (permissions[i].equals(PERMISSION_PAGE)
                        && grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    return true;

        return false;
    }
}
