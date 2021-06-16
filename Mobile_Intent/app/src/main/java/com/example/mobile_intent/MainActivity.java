package com.example.mobile_intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.net.Uri.fromFile;

public class MainActivity extends AppCompatActivity {

    int REQUEST_CODE_CAMERA = 222;
    File photoFile = null;
    int CAPTURE_IMAGE_REQUEST = 1;
    String mCurrentPhotoPath;
    //Button buttonCamera;
    Button buttonShare;
    ImageView captureImage;
    Button buttonCaptureAndShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //define components
        //buttonCamera = (Button)findViewById(R.id.nextMatchButton);
        buttonShare = (Button)findViewById(R.id.shareButton);
        captureImage = (ImageView)findViewById(R.id.youWinImage);
        buttonCaptureAndShare = (Button) findViewById(R.id.shareAndCapture);

        buttonShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Share" , Toast.LENGTH_SHORT).show();

//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//                sendIntent.setType("text/plain");
//                Intent shareIntent = Intent.createChooser(sendIntent, null);
//                startActivity(shareIntent);

//                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("image/jpg");
//                final File photoFile = new File(getFilesDir(), "foo.jpg");
//                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
//                startActivity(Intent.createChooser(shareIntent, "Share image using"));
                onShareItem(v);
            }
        });

        buttonCaptureAndShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Capture and Share" , Toast.LENGTH_SHORT).show();
                captureImage();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            captureImage.setImageBitmap(myBitmap);
        }
        else
        {
            displayMessage(getBaseContext(),"Request cancelled or something went wrong.");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onShareItem(View v) {
        // Get access to bitmap image from view
        ImageView ivImage = (ImageView) findViewById(R.id.youWinImage);
        // Get access to the URI for the bitmap
        Uri bmpUri = FileProvider.getUriForFile(MainActivity.this, "com.vlemonn.blog.captureimage.fileprovider", photoFile);

        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // shareIntent.setType("text/plain");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            Toast.makeText(MainActivity.this, "bmp uri is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void captureImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        else
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {

                    photoFile = createImageFile();
                    displayMessage(getBaseContext(),photoFile.getAbsolutePath());
                    Log.i("Mayank",photoFile.getAbsolutePath());

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.vlemonn.blog.captureimage.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
                    }
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    displayMessage(getBaseContext(),ex.getMessage().toString());
                }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void displayMessage(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            }
        }

    }


}