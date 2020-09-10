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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class onBackFrag extends DialogFragment {

    Bitmap bitmap;
    public onBackFrag(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    Button save,leave;
    ImageButton cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.on_back_frag , container , false);

        save = v.findViewById(R.id.save);
        leave = v.findViewById(R.id.leave);
        cancel = v.findViewById(R.id.reject2);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImage(bitmap , v.getContext());
                getActivity().finish();
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
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

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = 700;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
