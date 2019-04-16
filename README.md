# Print Screen Android
Create Bitmap from some view and share in any image app receiver

# PrintUtils.printView(imageView, activity);
get Bitmap from imageView setted, and put content in memory, after this, show options to share the image

    PrintUtils.printView(imageCopy, this);
    

# PrintUtils.isRequestPermissionResult(requestCode, permissions, grantResults)
When permission will agree, verify the result. if always it's okay, try share again.

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PrintUtils.isRequestPermissionResult(requestCode, permissions, grantResults))
            startShare();
    }
