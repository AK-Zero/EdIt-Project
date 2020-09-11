package com.example.edit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MyCustomDialogFragment extends DialogFragment {

    Bitmap bmp;

    public MyCustomDialogFragment(Bitmap b){
        bmp = b;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ImageButton save,share;
    ImageView croppedimg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_custom_dialog, container, false);

        save = v.findViewById(R.id.save);
        share = v.findViewById(R.id.share2);
        croppedimg = v.findViewById(R.id.croppedimg);
        croppedimg.setImageBitmap(bmp);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImage(bmp , v.getContext());
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImageUri(saveImage(bmp));
            }
        });
        return v;
    }

    private Uri saveImage(Bitmap image) {
        File imagesFolder = new File(getActivity().getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(getContext(), "com.mydomain.fileprovider", file);

        } catch (IOException e) {
            Toast.makeText(getContext() , "IOException while trying to write file for sharing: " + e.getMessage() , Toast.LENGTH_LONG).show();        }
        return uri;
    }

    private void SaveImage(Bitmap finalBitmap , final Context ctx) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/SavedImages");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String fname = "Image-"+ Calendar.getInstance().getTime() +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(ctx, new String[] { file.toString() }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Toast.makeText( ctx , "Image Saved at : " + path, Toast.LENGTH_SHORT).show();
                    }
                });

        Toast.makeText(ctx , "Image saved in SavedImages..." , Toast.LENGTH_SHORT).show();
    }

    private void shareImageUri(Uri uri){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }
}