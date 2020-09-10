package com.example.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class pictureAdd extends DialogFragment {

    picInt inter;
    pictureAdd(picInt inter){
        this.inter = inter;
    }

    ImageButton cam , reject;
    TextView choosimg;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.picture_add , container , false);

        cam = v.findViewById(R.id.cam);
        choosimg = v.findViewById(R.id.chooseimg);
        reject = v.findViewById(R.id.reject4);

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.camchosen();
                dismiss();
            }
        });

        choosimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.picchosen();
                dismiss();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.setback();
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = 900;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

}
