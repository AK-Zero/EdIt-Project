package com.example.edit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Main2Activity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener, picInt {
    ImageView img;
    myView imageeditor;
    ImageButton crop, doodle, save, share, blur, undo, textbox;
    EditText textfield;
    Button pickcolor, colorfilter;
    boolean cropstatr = false, cropstatc = false, doodlestat = false, pickcolorstat = false, blurstat = false, picstat = false;
    boolean boldstat = false, italicstat = false, understat = false;
    ImageButton leftturn, rightturn;
    float deg = 90;
    Bitmap resizedBitmap;
    SeekBar hue, contrast, brightness, saturation;
    ImageButton apply, reject;
    ImageButton bold, italic, underline, commit, rejecttext;
    HorizontalScrollView mainmenu;
    ConstraintLayout colormenu, textdeets;
    float huevalue = 0, brightnessvalue = 0, saturationvalue = 0;
    int contrastvalue = 0;
    TextView colorchanger;
    int colorchangerprev = Color.BLACK;
    Animation textboxanim, colorfilteranim, textboxanim1, colorfilteranim1;
    AlphaAnimation animation1;
    ImageButton picture;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        img = findViewById(R.id.imageView);
        crop = findViewById(R.id.crop);
        doodle = findViewById(R.id.doodle);
        save = findViewById(R.id.megasave);
        share = findViewById(R.id.share2);
        leftturn = findViewById(R.id.leftturn);
        rightturn = findViewById(R.id.rightturn);
        pickcolor = findViewById(R.id.pickcolor);
        blur = findViewById(R.id.blur);
        undo = findViewById(R.id.undo);
        colorfilter = findViewById(R.id.colorfilter);
        mainmenu = findViewById(R.id.mainmenu);
        textbox = findViewById(R.id.textbox);
        textfield = findViewById(R.id.textfield);
        bold = findViewById(R.id.bold);
        commit = findViewById(R.id.commit);
        rejecttext = findViewById(R.id.reject3);
        italic = findViewById(R.id.italic);
        underline = findViewById(R.id.underline);
        colorchanger = findViewById(R.id.color);
        picture = findViewById(R.id.picture);
        colormenu = (ConstraintLayout) findViewById(R.id.colormenu);
        textdeets = (ConstraintLayout) findViewById(R.id.textdeets);

        hue = findViewById(R.id.hue);
        contrast = findViewById(R.id.contrast);
        brightness = findViewById(R.id.brightness);
        saturation = findViewById(R.id.saturation);
        apply = findViewById(R.id.apply);
        reject = findViewById(R.id.reject);

        crop.setOnCreateContextMenuListener(this);
        Intent intent = getIntent();
        String imagepath = intent.getStringExtra("imageuri");
        imageeditor = findViewById(R.id.myV);
        imageeditor.setLayerType(View.LAYER_TYPE_SOFTWARE , null);
        imageeditor.setInter(this);
        Bitmap bitmap = null;
        bitmap = (Bitmap) intent.getExtras().get("photo");
        if (bitmap == null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(imagepath));
            } catch (IOException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        Matrix matrix = new Matrix();
        if (bitmap.getWidth() > 1100) {
            float scale = 0.8f;
            matrix.postScale(scale, 0.9f);
        }
        if (bitmap.getHeight() < 270) {
            float scale = 4.0f;
            matrix.postScale(scale, scale);
        }
        if (bitmap.getHeight() > 2700) {
            float scale = 0.45f;
            matrix.postScale(scale, scale);
        } else if (bitmap.getHeight() > 1400) {
            float scale = 0.6f;
            matrix.postScale(scale, scale);
        }
        resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                resizedBitmap.getWidth(),
                resizedBitmap.getHeight()
        );
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        params.bottomMargin = 250;
        imageeditor.setLayoutParams(params);
        imageeditor.setPath(resizedBitmap);
        Toast.makeText(Main2Activity.this , "Double tap to see zoomed picture..." , Toast.LENGTH_SHORT).show();

        crop.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (!cropstatc && !cropstatr) {
                    Toast.makeText(Main2Activity.this, "Long click for shape options...", Toast.LENGTH_SHORT).show();
                }
                if (cropstatr) {
                    Toast.makeText(Main2Activity.this , "Your cropped image..." , Toast.LENGTH_SHORT).show();
                    Bitmap bitmap1 = imageeditor.hidecrop();
                    DialogFragment dialogFragment = new MyCustomDialogFragment(bitmap1);
                    dialogFragment.show(getSupportFragmentManager(), "dialog");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        crop.setBackground(getDrawable(R.drawable.border));
                    }
                    cropstatr = !cropstatr;
                }
                if (cropstatc) {
                    Toast.makeText(Main2Activity.this, "Choose Background Color...", Toast.LENGTH_SHORT).show();
                    AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(Main2Activity.this, Color.BLACK, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                        @Override
                        public void onCancel(AmbilWarnaDialog dialog) {
                        }

                        @Override
                        public void onOk(AmbilWarnaDialog dialog, int color) {
                            Toast.makeText(Main2Activity.this , "Your cropped image..." , Toast.LENGTH_SHORT).show();
                            Bitmap bitmap1 = imageeditor.hidecirclecrop(color);
                            DialogFragment dialogFragment = new MyCustomDialogFragment(bitmap1);
                            dialogFragment.show(getSupportFragmentManager(), "dialog");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                crop.setBackground(getDrawable(R.drawable.border));
                            }
                            cropstatc = !cropstatc;
                        }
                    });
                    colorPicker.show();
                }
            }
        });

        doodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doodlestat) {
                    if (!cropstatr && !cropstatc && !blurstat && !pickcolorstat && !picstat) {
                        imageeditor.setdoodle();
                        Toast.makeText(Main2Activity.this, "Click again to disable...", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            doodle.setBackground(getDrawable(R.drawable.borderblue));
                        }
                        doodlestat = true;
                    }
                } else {
                    imageeditor.hidedoodle();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        doodle.setBackground(getDrawable(R.drawable.border));
                    }
                    doodlestat = false;
                }
                if (blurstat) {
                    Toast.makeText(Main2Activity.this, "Close blur first...", Toast.LENGTH_SHORT).show();
                }
                if (cropstatr || cropstatc) {
                    Toast.makeText(Main2Activity.this, "Close crop first...", Toast.LENGTH_SHORT).show();
                }
                if (pickcolorstat) {
                    Toast.makeText(Main2Activity.this, "Close SWAP COLOR first...", Toast.LENGTH_SHORT).show();
                }
                if (picstat) {
                    Toast.makeText(Main2Activity.this, "Close picture setter first...", Toast.LENGTH_SHORT).show();
                }

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bmp = imageeditor.empty;
                SaveImage(bmp);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bmp = imageeditor.empty;
                Uri x = saveImage(bmp);
                shareImageUri(x);
            }
        });
        leftturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doodlestat && !blurstat && !cropstatr && !cropstatc && !pickcolorstat && !picstat) {
                    Bitmap bmp = imageeditor.empty;
                    Matrix matrix = new Matrix();
                    float scalex = (float) bmp.getWidth() / (float) bmp.getHeight();
                    float scaley = (float) bmp.getHeight() / (float) bmp.getWidth();
                    matrix.postScale(scalex, scaley);
                    matrix.preRotate(-1 * deg);
                    resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                    imageeditor.setPath(resizedBitmap);
                }
                if (doodlestat) {
                    Toast.makeText(Main2Activity.this, "Close doodle brush first...", Toast.LENGTH_SHORT).show();
                }
                if (blurstat) {
                    Toast.makeText(Main2Activity.this, "Close blur first...", Toast.LENGTH_SHORT).show();
                }
                if (cropstatr || cropstatc) {
                    Toast.makeText(Main2Activity.this, "Close crop first...", Toast.LENGTH_SHORT).show();
                }
                if (pickcolorstat) {
                    Toast.makeText(Main2Activity.this, "Close SWAP COLOR first...", Toast.LENGTH_SHORT).show();
                }
                if (picstat) {
                    Toast.makeText(Main2Activity.this, "Close color setter first...", Toast.LENGTH_SHORT).show();
                }

            }
        });
        rightturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doodlestat && !blurstat && !cropstatr && !cropstatc && !pickcolorstat && !picstat) {
                    Bitmap bmp = imageeditor.empty;
                    Matrix matrix = new Matrix();
                    float scalex = (float) bmp.getWidth() / (float) bmp.getHeight();
                    float scaley = (float) bmp.getHeight() / (float) bmp.getWidth();
                    matrix.postScale(scalex, scaley);
                    matrix.preRotate(deg);
                    resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                    imageeditor.setPath(resizedBitmap);
                }
                if (doodlestat) {
                    Toast.makeText(Main2Activity.this, "Close doodle brush first...", Toast.LENGTH_SHORT).show();
                }
                if (blurstat) {
                    Toast.makeText(Main2Activity.this, "Close blur first...", Toast.LENGTH_SHORT).show();
                }
                if (cropstatr || cropstatc) {
                    Toast.makeText(Main2Activity.this, "Close crop first...", Toast.LENGTH_SHORT).show();
                }
                if (pickcolorstat) {
                    Toast.makeText(Main2Activity.this, "Close SWAP COLOR first...", Toast.LENGTH_SHORT).show();
                }
                if (picstat) {
                    Toast.makeText(Main2Activity.this, "Close color setter first...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pickcolor.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (!pickcolorstat) {
                    if (!cropstatc && !cropstatr && !doodlestat && !blurstat && !picstat) {
                        pickcolor.setText("STOP");
                        Toast.makeText(Main2Activity.this, "Choose pixel", Toast.LENGTH_SHORT).show();
                        pickcolor.setBackground(getDrawable(R.drawable.borderblue));
                        imageeditor.pickcolor();
                        pickcolorstat = true;
                    }
                } else {
                    pickcolor.setText("SWAP COLOR");
                    pickcolor.setBackground(getDrawable(R.drawable.border));
                    imageeditor.stoppickcolor();
                    pickcolorstat = false;
                }
                if (doodlestat) {
                    Toast.makeText(Main2Activity.this, "Close doodle brush first...", Toast.LENGTH_SHORT).show();
                }
                if (cropstatr || cropstatc) {
                    Toast.makeText(Main2Activity.this, "Close crop first...", Toast.LENGTH_SHORT).show();
                }
                if (blurstat) {
                    Toast.makeText(Main2Activity.this, "Close blur first...", Toast.LENGTH_SHORT).show();
                }
                if (picstat) {
                    Toast.makeText(Main2Activity.this, "Close picture setter first...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        blur.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (!blurstat) {
                    if (!doodlestat && !cropstatr && !cropstatc && !pickcolorstat && !picstat) {
                        Toast.makeText(Main2Activity.this, "Click again after choosing area to blur...", Toast.LENGTH_SHORT).show();
                        imageeditor.startblur();
                        blur.setBackground(getDrawable(R.drawable.borderblue));
                        blurstat = true;
                    }
                } else {
                    imageeditor.stopblur();
                    blur.setBackground(getDrawable(R.drawable.border));
                    blurstat = false;
                }
                if (doodlestat) {
                    Toast.makeText(Main2Activity.this, "Close doodle brush first...", Toast.LENGTH_SHORT).show();
                }
                if (cropstatr || cropstatc) {
                    Toast.makeText(Main2Activity.this, "Close crop first...", Toast.LENGTH_SHORT).show();
                }
                if (pickcolorstat) {
                    Toast.makeText(Main2Activity.this, "Close SWAP COLOR first...", Toast.LENGTH_SHORT).show();
                }
                if (picstat) {
                    Toast.makeText(Main2Activity.this, "Close picture setter first...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!blurstat && !picstat && !cropstatr && !cropstatc && !pickcolorstat) {
                    imageeditor.undo();
                }
            }
        });

        colorfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doodlestat && !cropstatr && !cropstatc && !pickcolorstat && !blurstat && !picstat) {
                    mainmenu.setVisibility(View.INVISIBLE);
                    undo.setVisibility(View.INVISIBLE);
                    share.setVisibility(View.INVISIBLE);
                    save.setVisibility(View.INVISIBLE);
                    colorfilter.setVisibility(View.INVISIBLE);
                    colormenu.setVisibility(View.VISIBLE);
                    colorfilteranim = AnimationUtils.loadAnimation(Main2Activity.this, R.anim.colorfilter);
                    colormenu.setAnimation(colorfilteranim);
                    imageeditor.setcolorfilter();
                    hue.setProgress(50);
                    contrast.setProgress(50);
                    saturation.setProgress(50);
                    brightness.setProgress(50);
                }
                if (doodlestat) {
                    Toast.makeText(Main2Activity.this, "Close doodle brush first...", Toast.LENGTH_SHORT).show();
                }
                if (blurstat) {
                    Toast.makeText(Main2Activity.this, "Close blur first...", Toast.LENGTH_SHORT).show();
                }
                if (cropstatr || cropstatc) {
                    Toast.makeText(Main2Activity.this, "Close crop first...", Toast.LENGTH_SHORT).show();
                }
                if (pickcolorstat) {
                    Toast.makeText(Main2Activity.this, "Close SWAP COLOR first...", Toast.LENGTH_SHORT).show();
                }
                if (picstat) {
                    Toast.makeText(Main2Activity.this, "Close picture setter first...", Toast.LENGTH_SHORT).show();
                }

            }
        });


        hue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                huevalue = -180 + ((float) (progress) / 100) * 360;
                imageeditor.oncolorfilterchanged(huevalue, brightnessvalue, contrastvalue, saturationvalue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        contrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                contrastvalue = -100 + 2 * progress;
                imageeditor.oncolorfilterchanged(huevalue, brightnessvalue, contrastvalue, saturationvalue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightnessvalue = -100 + ((float) (progress) / 100) * 200;
                imageeditor.oncolorfilterchanged(huevalue, brightnessvalue, contrastvalue, saturationvalue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                saturationvalue = -100 + ((float) (progress) / 100) * 200;
                imageeditor.oncolorfilterchanged(huevalue, brightnessvalue, contrastvalue, saturationvalue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorfilteranim1 = AnimationUtils.loadAnimation(Main2Activity.this, R.anim.colorfilter1);
                colormenu.setAnimation(colorfilteranim1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animation1 = new AlphaAnimation(0f, 1.0f);
                        animation1.setDuration(400);
                        colormenu.setVisibility(View.INVISIBLE);
                        mainmenu.setVisibility(View.VISIBLE);
                        mainmenu.setAnimation(animation1);
                        undo.setVisibility(View.VISIBLE);
                        undo.setAnimation(animation1);
                        share.setVisibility(View.VISIBLE);
                        share.setAnimation(animation1);
                        save.setVisibility(View.VISIBLE);
                        save.setAnimation(animation1);
                        colorfilter.setVisibility(View.VISIBLE);
                        colorfilter.setAnimation(animation1);
                    }
                }, 450);
                imageeditor.colorfilterapplied();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorfilteranim1 = AnimationUtils.loadAnimation(Main2Activity.this, R.anim.colorfilter1);
                colormenu.setAnimation(colorfilteranim1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animation1 = new AlphaAnimation(0f, 1.0f);
                        animation1.setDuration(400);
                        colormenu.setVisibility(View.INVISIBLE);
                        mainmenu.setVisibility(View.VISIBLE);
                        mainmenu.setAnimation(animation1);
                        undo.setVisibility(View.VISIBLE);
                        undo.setAnimation(animation1);
                        share.setVisibility(View.VISIBLE);
                        share.setAnimation(animation1);
                        save.setVisibility(View.VISIBLE);
                        save.setAnimation(animation1);
                        colorfilter.setVisibility(View.VISIBLE);
                        colorfilter.setAnimation(animation1);
                    }
                }, 450);
                imageeditor.colorfiltercancelled();
            }
        });


        textbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doodlestat && !cropstatr && !cropstatc && !pickcolorstat && !blurstat && !picstat) {
                    mainmenu.setVisibility(View.INVISIBLE);
                    undo.setVisibility(View.INVISIBLE);
                    share.setVisibility(View.INVISIBLE);
                    save.setVisibility(View.INVISIBLE);
                    textbox.setVisibility(View.INVISIBLE);
                    textdeets.setVisibility(View.VISIBLE);
                    textboxanim = AnimationUtils.loadAnimation(Main2Activity.this, R.anim.textbox);
                    textdeets.setAnimation(textboxanim);
                    imageeditor.setTextBox(colorchangerprev);
                    colorchanger.setBackgroundColor(colorchangerprev);
                }
                if (doodlestat) {
                    Toast.makeText(Main2Activity.this, "Close doodle brush first...", Toast.LENGTH_SHORT).show();
                }
                if (blurstat) {
                    Toast.makeText(Main2Activity.this, "Close blur first...", Toast.LENGTH_SHORT).show();
                }
                if (cropstatr || cropstatc) {
                    Toast.makeText(Main2Activity.this, "Close crop first...", Toast.LENGTH_SHORT).show();
                }
                if (pickcolorstat) {
                    Toast.makeText(Main2Activity.this, "Close SWAP COLOR first...", Toast.LENGTH_SHORT).show();
                }
                if (picstat) {
                    Toast.makeText(Main2Activity.this, "Close picture setter first...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        colorchanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main2Activity.this, "Choose Text Color...", Toast.LENGTH_SHORT).show();
                AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(Main2Activity.this, colorchangerprev, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        imageeditor.changetextcolor(color);
                        colorchanger.setBackgroundColor(color);
                        colorchangerprev = color;
                    }
                });
                colorPicker.show();
            }
        });
        textfield.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!textfield.getText().toString().isEmpty()) {
                        textfield.setHint(" ");
                    } else {
                        textfield.setHint("Enter Text");
                    }
                }
            }
        });
        textfield.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imageeditor.settextforfield(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boldstat) {
                    imageeditor.setbold();
                    bold.setBackgroundColor(Color.BLUE);
                } else {
                    imageeditor.hidebold();
                    bold.setBackgroundColor(Color.BLACK);
                }
                boldstat = !boldstat;
            }
        });
        italic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!italicstat) {
                    imageeditor.setitalic();
                    italic.setBackgroundColor(Color.BLUE);
                } else {
                    imageeditor.hideitalic();
                    italic.setBackgroundColor(Color.BLACK);
                }
                italicstat = !italicstat;
            }
        });
        underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!understat) {
                    imageeditor.setunderline();
                    underline.setBackgroundColor(Color.BLUE);
                } else {
                    imageeditor.hideunderline();
                    underline.setBackgroundColor(Color.BLACK);
                }
                understat = !understat;
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textboxanim1 = AnimationUtils.loadAnimation(Main2Activity.this, R.anim.textbox1);
                textdeets.setAnimation(textboxanim1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textdeets.setVisibility(View.INVISIBLE);
                        mainmenu.setVisibility(View.VISIBLE);
                        undo.setVisibility(View.VISIBLE);
                        share.setVisibility(View.VISIBLE);
                        save.setVisibility(View.VISIBLE);
                        textbox.setVisibility(View.VISIBLE);
                        animation1 = new AlphaAnimation(0f, 1.0f);
                        animation1.setDuration(400);
                        mainmenu.setAnimation(animation1);
                        undo.setAnimation(animation1);
                        share.setAnimation(animation1);
                        save.setAnimation(animation1);
                        textbox.setAnimation(animation1);

                    }
                }, 450);
                imageeditor.textapplied(textfield.getText().toString());
                textfield.setText("");
                hideKeyBoard();
            }
        });

        rejecttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textboxanim1 = AnimationUtils.loadAnimation(Main2Activity.this, R.anim.textbox1);
                textdeets.setAnimation(textboxanim1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textdeets.setVisibility(View.INVISIBLE);
                        mainmenu.setVisibility(View.VISIBLE);
                        undo.setVisibility(View.VISIBLE);
                        share.setVisibility(View.VISIBLE);
                        save.setVisibility(View.VISIBLE);
                        textbox.setVisibility(View.VISIBLE);
                        animation1 = new AlphaAnimation(0f, 1.0f);
                        animation1.setDuration(400);
                        mainmenu.setAnimation(animation1);
                        undo.setAnimation(animation1);
                        share.setAnimation(animation1);
                        save.setAnimation(animation1);
                        textbox.setAnimation(animation1);
                    }
                }, 450);
                imageeditor.textrejected();
                textfield.setText("");
                hideKeyBoard();
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (!picstat) {
                    if (!doodlestat && !cropstatr && !cropstatc && !pickcolorstat && !blurstat) {
                        DialogFragment dialogFragment = new pictureAdd(Main2Activity.this);
                        dialogFragment.show(getSupportFragmentManager(), "picadd");
                        dialogFragment.setCancelable(false);
                        picture.setBackground(getDrawable(R.drawable.borderblue));
                        picstat = true;
                    }
                } else {
                    imageeditor.cancelimageadd();
                    picture.setBackground(getDrawable(R.drawable.border));
                    picstat = false;
                }
                if (doodlestat) {
                    Toast.makeText(Main2Activity.this, "Close doodle brush first...", Toast.LENGTH_SHORT).show();
                }
                if (blurstat) {
                    Toast.makeText(Main2Activity.this, "Close blur first...", Toast.LENGTH_SHORT).show();
                }
                if (cropstatr || cropstatc) {
                    Toast.makeText(Main2Activity.this, "Close crop first...", Toast.LENGTH_SHORT).show();
                }
                if (pickcolorstat) {
                    Toast.makeText(Main2Activity.this, "Close SWAP COLOR first...", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void hideKeyBoard() {
        View view1 = getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
    }

    private Uri saveImage(Bitmap image) {
        File imagesFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(this, "com.mydomain.fileprovider", file);

        } catch (IOException e) {
            Toast.makeText(this, "IOException while trying to write file for sharing: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }

    private void SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/SavedImages");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String fname = "Image-" + Calendar.getInstance().getTime() + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
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
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Toast.makeText(Main2Activity.this, "Image Saved at : " + path, Toast.LENGTH_SHORT).show();
                    }
                });
        Toast.makeText(this, "Image saved in SavedImages...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderIcon(R.drawable.ic_crop_black_24dp);
        menu.setHeaderTitle("Choose Crop Shape..");
        MenuItem crop1 = menu.add(Menu.NONE, 1, 1, "Rectangle");
        MenuItem crop2 = menu.add(Menu.NONE, 2, 2, "Circle");

        crop1.setOnMenuItemClickListener(this);
        crop2.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case 1: {
                rectcrop();
                return true;
            }
            case 2: {
                circlecrop();
                return true;
            }
        }
        return false;
    }

    public void rectcrop() {
        if (!cropstatr && !cropstatc && !doodlestat && !blurstat && !pickcolorstat && !picstat) {
            imageeditor.setcrop();
            Toast.makeText(Main2Activity.this, "Click again to see cropped picture...", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                crop.setBackground(getDrawable(R.drawable.borderblue));
            }
            cropstatr = !cropstatr;
        }
        if (doodlestat) {
            Toast.makeText(Main2Activity.this, "Close doodle brush first...", Toast.LENGTH_SHORT).show();
        }
        if (blurstat) {
            Toast.makeText(Main2Activity.this, "Close blur first...", Toast.LENGTH_SHORT).show();
        }
        if (pickcolorstat) {
            Toast.makeText(Main2Activity.this, "Close SWAP COLOR first...", Toast.LENGTH_SHORT).show();
        }
        if (picstat) {
            Toast.makeText(Main2Activity.this, "Close picture setter first...", Toast.LENGTH_SHORT).show();
        }
        if(cropstatc){
            Toast.makeText(Main2Activity.this, "Close circular crop first...", Toast.LENGTH_SHORT).show();
        }
    }

    public void circlecrop() {
        if (!cropstatc && !cropstatr && !doodlestat && !blurstat && !pickcolorstat && !picstat) {
            imageeditor.setcirclecrop();
            Toast.makeText(Main2Activity.this, "Click again to see cropped picture...", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                crop.setBackground(getDrawable(R.drawable.borderblue));
            }
            cropstatc = !cropstatc;
        }
        if (doodlestat) {
            Toast.makeText(Main2Activity.this, "Close doodle brush first...", Toast.LENGTH_SHORT).show();
        }
        if (blurstat) {
            Toast.makeText(Main2Activity.this, "Close blur first...", Toast.LENGTH_SHORT).show();
        }
        if (pickcolorstat) {
            Toast.makeText(Main2Activity.this, "Close SWAP COLOR first...", Toast.LENGTH_SHORT).show();
        }
        if (picstat) {
            Toast.makeText(Main2Activity.this, "Close picture setter first...", Toast.LENGTH_SHORT).show();
        }
        if(cropstatr){
            Toast.makeText(Main2Activity.this, "Close rectangular crop first...", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareImageUri(Uri uri) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Bitmap bitmap1 = imageeditor.empty;
        DialogFragment dialogFragment = new onBackFrag(bitmap1);
        dialogFragment.setCancelable(false);
        dialogFragment.show(getSupportFragmentManager(), "back");
    }

    @Override
    public void picchosen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void camchosen() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 2);
    }

    @Override
    public void setback() {
        picstat = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            picture.setBackground(getDrawable(R.drawable.border));
        }
    }

    @Override
    public void onZoom(Bitmap bmp) {
        DialogFragment dialogFragment = new MyCustomDialogFragment(bmp);
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri mImageUri = data.getData();
            Bitmap bitmapx = null;
            try {
                bitmapx = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mImageUri.toString()));
            } catch (IOException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
            Matrix matrix = new Matrix();
            int x = 0;
            if (bitmapx.getWidth() > 1000) {
                float scale = 0.25f;
                matrix.postScale(scale, 0.2f);
                x++;
            }
            if(bitmapx.getWidth()>1400 && bitmapx.getHeight()>2500 && x==0){
                float scale = 0.1f;
                matrix.postScale(scale, scale);
                x++;
            }
            if (bitmapx.getHeight() < 270 && x == 0) {
                float scale = 1.0f;
                matrix.postScale(scale, scale);
                x++;
            }
            if (bitmapx.getHeight() > 2700 && x == 0) {
                float scale = 0.1f;
                matrix.postScale(scale, scale);
            } else if (bitmapx.getHeight() > 1400 && x == 0) {
                float scale = 0.2f;
                matrix.postScale(scale, scale);
            } else if (x == 0) {
                float scale = 0.4f;
                matrix.postScale(scale, scale);
            }
            Bitmap resizedBitmapx = Bitmap.createBitmap(
                    bitmapx, 0, 0, bitmapx.getWidth(), bitmapx.getHeight(), matrix, false);
            imageeditor.setImageAdd(resizedBitmapx);
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Bitmap bitmapx = (Bitmap) data.getExtras().get("data");
            Matrix matrix = new Matrix();
            int x = 0;
            if (bitmapx.getWidth() > 1000) {
                float scale = 0.25f;
                matrix.postScale(scale, 0.2f);
                x++;
            }

            if(bitmapx.getWidth()>1500 && bitmapx.getHeight()>2500 && x==0){
                float scale = 0.1f;
                matrix.postScale(scale, scale);
                x++;
            }
            if (bitmapx.getHeight() < 270 && x == 0) {
                float scale = 1.0f;
                matrix.postScale(scale, scale);
                x++;
            }
            if (bitmapx.getHeight() > 2700 && x == 0) {
                float scale = 0.1f;
                matrix.postScale(scale, scale);
            } else if (bitmapx.getHeight() > 1400 && x == 0) {
                float scale = 0.2f;
                matrix.postScale(scale, scale);
            } else if (x == 0) {
                float scale = 0.4f;
                matrix.postScale(scale, scale);
            }
            Bitmap resizedBitmapx = Bitmap.createBitmap(
                    bitmapx, 0, 0, bitmapx.getWidth(), bitmapx.getHeight(), matrix, false);
            imageeditor.setImageAdd(resizedBitmapx);
        }
        if(data == null){
            picstat = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                picture.setBackground(getDrawable(R.drawable.border));
            }
        }
    }
}

