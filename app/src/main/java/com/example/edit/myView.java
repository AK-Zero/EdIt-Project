package com.example.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class myView extends View {

    String imagepath;
    Bitmap bitmap, bitpixel, empty;
    boolean stat = false;
    Paint p1, p2, p3;
    TextPaint text;
    float x, y, lx, ly, cx, cy, r;
    int statint = 1, statint2 = 1, eng = 0;
    boolean cropstat = false, circlecropstat = false, doodlestat = false, pickcolorstat = false, blurstat = false, textstat = false, picstat = false , drawablestat = false;
    int width, height, xo = 0, yo = 0, yo1 = 0;
    double d11 = 0;
    Path path;
    Canvas canvas;
    Integer doodlecolor = Color.BLACK;
    List<Bitmap> bitmaphistory = new ArrayList<>();
    Paint scale;
    int textx = 120, texty = 140, textwidth, textheight = 70, savedheight;
    String textstring = " ";
    Drawable d;
    Bitmap bitmapx;
    int bmpx, bmpy, bmpwidth, savedheight2;
    picInt actInt;
    GestureDetector gestureDetector;

    public myView(Context context) {
        super(context);
        init(null);
    }

    public myView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public myView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        p1 = new Paint();
        p1.setAntiAlias(true);
        p1.setColor(Color.CYAN);
        p1.setStyle(Paint.Style.STROKE);
        p1.setStrokeWidth(10);
        p2 = new Paint();
        p2.setAntiAlias(true);
        p2.setColor(Color.RED);
        p2.setStyle(Paint.Style.STROKE);
        p2.setStrokeWidth(10);
        p3 = new Paint();
        p3.setAntiAlias(true);
        p3.setColor(Color.TRANSPARENT);
        p3.setStyle(Paint.Style.FILL);
        scale = new Paint();
        path = new Path();
        gestureDetector = new GestureDetector(getContext() , new GestureListener());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            d = getResources().getDrawable(R.drawable.ic_code_black_24dp, null);
        }
        this.setDrawingCacheEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(canvas);
        if (stat) {
            canvas.drawBitmap(bitmap, 0, 0, scale);
            c.drawBitmap(bitmap, 0, 0, scale);
        }
        if (textstat) {
            canvas.drawText(textstring, textx, texty, text);
            c.drawText(textstring, textx, texty, text);
            canvas.drawCircle((float) (textx + textwidth + 30), (float) (texty + 30), 25, p3);
            c.drawCircle((float) (textx + textwidth + 30), (float) (texty + 30), 25, p3);
            d.setBounds(textx + textwidth + 5, texty + 5, textx + textwidth + 55, texty + 55);
            d.draw(canvas);
            d.draw(c);
        }
        if (statint == 1) {
            x = (float) getWidth() / 3;
            y = (float) getHeight() / 3;
            lx = ly = 200;
            statint++;
        } else if (statint2 == 1) {
            cx = (float) getWidth() / 2;
            cy = (float) getHeight() / 2;
            r = 100;
            statint2++;
        }
        if (cropstat) {
            canvas.drawRect(x, y, x + lx, y + ly, p1);
            c.drawRect(x, y, x + lx, y + ly, p1);
        }
        if (circlecropstat) {
            canvas.drawCircle(cx, cy, r, p1);
            c.drawCircle(cx, cy, r, p1);
        }
        if (doodlestat) {
            canvas.drawPath(path, p2);
            c.drawPath(path, p2);
        }
        if (blurstat) {
            canvas.drawRect(x, y, x + lx, y + ly, p1);
            c.drawRect(x, y, x + lx, y + ly, p1);
        }
        if (picstat) {
            canvas.drawBitmap(bitmapx, bmpx, bmpy, null);
            c.drawBitmap(bitmapx, bmpx, bmpy, null);
            if (eng == 0) {
                canvas.drawRect(bmpx - 10, bmpy - 10, bmpx + bitmapx.getWidth() + 10, bmpy + bitmapx.getHeight() + 10, p1);
                c.drawRect(bmpx - 10, bmpy - 10, bmpx + bitmapx.getWidth() + 10, bmpy + bitmapx.getHeight() + 10, p1);
                canvas.drawCircle((float) (bmpx + bitmapx.getWidth() + 30), (float) (bmpy + bitmapx.getHeight() + 30), 25, p3);
                c.drawCircle((float) (bmpx + bitmapx.getWidth() + 30), (float) (bmpy + bitmapx.getHeight() + 30), 25, p3);
                if(drawablestat) {
                    d.setBounds(bmpx + bitmapx.getWidth() + 5, bmpy + bitmapx.getHeight() + 5, bmpx + bitmapx.getWidth() + 55, bmpy + bitmapx.getHeight() + 55);
                    d.draw(canvas);
                    d.draw(c);
                }
            } else if (eng == 7) {
                canvas.drawRect(bmpx - 10, bmpy - 10, bmpx + bmpwidth + 10, bmpy + bitmapx.getHeight() * (float) (bmpwidth) / bitmapx.getWidth() + 10, p1);
                c.drawRect(bmpx - 10, bmpy - 10, bmpx + bmpwidth + 10, bmpy + bitmapx.getHeight() * (float) (bmpwidth) / bitmapx.getWidth() + 10, p1);
                if(drawablestat) {
                    d.setBounds(bmpx + bmpwidth + 5, (int) (bmpy + bitmapx.getHeight() * (float) (bmpwidth) / bitmapx.getWidth() + 5), bmpx + bmpwidth + 55, (int) (bmpy + bitmapx.getHeight() * (float) (bmpwidth) / bitmapx.getWidth() + 55));
                    d.draw(canvas);
                    d.draw(c);
                }
                canvas.drawCircle((float) (bmpx + bmpwidth + 30), bmpy + bitmapx.getHeight() * (float) (bmpwidth) / bitmapx.getWidth() + 30, 25, p3);
                c.drawCircle((float) (bmpx + bmpwidth + 30), bmpy + bitmapx.getHeight() * (float) (bmpwidth) / bitmapx.getWidth() + 30, 25, p3);
            }
        }
    }

    public void setPath(Bitmap b) {
        bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        empty = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        canvas = new Canvas(empty);
        stat = true;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = widthMeasureSpec;
        height = heightMeasureSpec;
    }

    //"#" + Integer.toHexString(u).substring(2)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        float xx = event.getX();
        float yy = event.getY();
        if (circlecropstat) {
            d11 = Math.sqrt(Math.pow(xx - cx, 2) + Math.pow(yy - cy, 2));
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (pickcolorstat && (xx > 0 && xx < getWidth() && yy > 0 && yy < getHeight())) {
                    Integer u = empty.getPixel((int) xx, (int) yy);
                    swapcolor(u);

                }
                if (doodlestat) {
                    path.moveTo(xx, yy);
                    if (xo != 0) {
                        bitmaphistory.add(Bitmap.createBitmap(
                                bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), null, false));
                    }
                    xo = 0;
                    yo = 10;
                }
                if (textstat) {
                    double d2 = Math.sqrt(Math.pow(xx - (float) (textx + textwidth + 30), 2) + Math.pow(yy - (float) (texty + 30), 2));
                    if (d2 < 50 && eng == 0) {
                        eng = 6;
                    }
                }
                if (picstat) {
                    double d2 = Math.sqrt(Math.pow(xx - (float) (bmpx + bitmapx.getWidth() + 30), 2) + Math.pow(yy - (float) (bmpy + bitmapx.getHeight() + 30), 2));
                    if (d2 < 50 && eng == 0) {
                        eng = 7;
                    }
                }
                return gestureDetector.onTouchEvent(event);
            }
            case MotionEvent.ACTION_MOVE: {
                if (picstat) {
                    double d2 = Math.sqrt(Math.pow(xx - (float) (bmpx + bitmapx.getWidth() + 30), 2) + Math.pow(yy - (float) (bmpy + bitmapx.getHeight() + 30), 2));
                    if (d2 > 70 && eng == 0 && (xx - bitmapx.getWidth() / 2 > 0 && yy - bitmapx.getHeight() / 2 > 0 && xx + bitmapx.getWidth() / 2 < canvas.getWidth() - 15 && yy + bitmapx.getHeight() / 2 < canvas.getHeight())) {
                        bmpx = (int) xx - bitmapx.getWidth() / 2;
                        bmpy = (int) yy - bitmapx.getHeight() / 2;
                    }
                    if (bmpy + bitmapx.getHeight() * (float) (bmpwidth) / bitmapx.getWidth() < canvas.getHeight() - 60) {
                        savedheight = (int) xx - bmpx-30;
                    }
                    if (xx - bmpx - 30 > 200) {
                        savedheight2 = (int) xx - bmpx - 30;
                    }
                    if (eng == 7 && (xx - bmpx - 30 > 150 && xx + 30 < canvas.getWidth())) {
                        bmpwidth = (int) xx - bmpx;
                    }
                }
                if (textstat) {
                    double d2 = Math.sqrt(Math.pow(xx - (float) (textx + textwidth + 30), 2) + Math.pow(yy - (float) (texty + 30), 2));
                    if (d2 > 70 && eng == 0 && (xx > 5 && yy - textheight + 40 > 0 && xx + textwidth + 5 < canvas.getWidth() && yy + 25 < canvas.getHeight())) {
                        textx = (int) xx;
                        texty = (int) yy;
                    }
                    if (textx + textwidth < canvas.getWidth() - 60) {
                        savedheight = textheight;
                    }
                    if (eng == 6 && (yy > texty - textheight + 70 && yy + 30 < canvas.getHeight())) {
                        textheight += (int) yy - texty - 30;
                        texty = (int) yy - 30;
                        updatepainttext();
                    }
                }
                if ((cropstat || blurstat) && xx > 0 && xx < canvas.getWidth() && yy > 0 && yy < canvas.getHeight()) {
                    double d1 = Math.sqrt(Math.pow(xx - x, 2) + Math.pow(yy - y, 2));
                    double d2 = Math.sqrt(Math.pow(xx - x - lx, 2) + Math.pow(yy - y, 2));
                    double d3 = Math.sqrt(Math.pow(xx - x, 2) + Math.pow(yy - y - ly, 2));
                    double d4 = Math.sqrt(Math.pow(xx - x - lx, 2) + Math.pow(yy - y - ly, 2));

                    if (xx > x && xx < x + lx && yy > y && yy < y + ly && eng == 0 && d1 > 60 && d2 > 60 && d3 > 60 && d4 > 60 && xx + lx / 2 < canvas.getWidth() && yy + ly / 2 < canvas.getHeight()
                            && xx - lx / 2 > 0 && yy - ly / 2 > 0) {
                        x = xx - lx / 2;
                        y = yy - ly / 2;
                        postInvalidate();
                    }


                    if (d1 < 60 && eng == 0) {
                        eng = 1;
                    } else if (d2 < 60 && eng == 0) {
                        eng = 2;
                    } else if (d3 < 60 && eng == 0) {
                        eng = 3;
                    } else if (d4 < 60 && eng == 0) {
                        eng = 4;
                    }
                    if (eng == 1) {

                        if (xx < x) {
                            lx += x - xx;
                        } else {
                            lx -= xx - x;
                        }
                        if (yy < y) {
                            ly += y - yy;
                        } else {
                            ly -= yy - y;
                        }
                        x = xx;
                        y = yy;
                        eng = 0;
                    } else if (eng == 2) {

                        if (yy < y) {
                            ly += y - yy;
                        } else {
                            ly -= yy - y;
                        }
                        lx = xx - x;
                        y = yy;
                        eng = 0;
                    } else if (eng == 3 && xx < width && yy < height) {

                        if (xx < x) {
                            lx += x - xx;
                        } else {
                            lx -= xx - x;
                        }
                        x = xx;
                        ly = yy - y;
                        eng = 0;
                    } else if (eng == 4 && xx < width && yy < height) {
                        lx = xx - x;
                        ly = yy - y;
                        eng = 0;
                    }
                }
                if (circlecropstat && cx + d11 < canvas.getWidth() && cy + d11 < canvas.getHeight() && cx - d11 > 0 && cy - d11 > 0) {

                    if (d11 < r - 30 && xx + r < canvas.getWidth() && yy + r < canvas.getHeight() && xx - r > 0 && yy - r > 0) {
                        cx = xx;
                        cy = yy;
                    }
                    if (d11 > r - 30 && d11 < r + 30 && eng == 0) {
                        eng = 1;
                    }
                    if (eng == 1) {
                        r = (float) d11;
                        eng = 0;
                    }
                }
                if (doodlestat) {
                    path.lineTo(xx, yy);
                }
                postInvalidate();
                return gestureDetector.onTouchEvent(event);
            }
            case MotionEvent.ACTION_UP: {

                if (lx < 0) {
                    x += lx;
                    lx *= -1;
                }
                if (ly < 0) {
                    y += ly;
                    ly *= -1;
                }
                if (eng == 6) {
                    eng = 0;
                }
                if (eng == 7 && bmpy + bitmapx.getHeight() * (float) (bmpwidth) / bitmapx.getWidth() > canvas.getHeight()) {
                    updatepicsize(savedheight);
                    eng = 0;
                }
                if (eng == 7 && xx - bmpx - 30 < 200) {
                    eng = 0;
                    updatepicsize(savedheight2);
                }
                if (eng == 7) {
                    eng = 0;
                    updatepicsize((int) xx - bmpx);
                }
                if (textstat && textx + textwidth > canvas.getWidth()) {
                    textheight = savedheight;
                    savedheight = 70;
                    updatepainttext();
                }
                postInvalidate();
                return gestureDetector.onTouchEvent(event);
            }

        }
        return gestureDetector.onTouchEvent(event);
    }

    public void setcrop() {
        cropstat = true;
        postInvalidate();
    }

    public Bitmap hidecrop() {
        cropstat = false;
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x + lx > canvas.getWidth()) {
            lx = canvas.getWidth() - x;
        }
        if (y + ly > canvas.getHeight()) {
            ly = canvas.getHeight() - y;
        }
        Bitmap croppedBitmap = Bitmap.createBitmap(empty, (int) x + 10, (int) y + 10, (int) lx - 20, (int) ly - 20);
        Matrix matrix = new Matrix();
        float scalex = (float) bitmap.getWidth() / (float) croppedBitmap.getWidth();
        float scaley = (float) bitmap.getHeight() / (float) croppedBitmap.getHeight();
        matrix.postScale(scalex, scaley);
        croppedBitmap = Bitmap.createBitmap(
                croppedBitmap, 0, 0, croppedBitmap.getWidth(), croppedBitmap.getHeight(), matrix, false);
        statint = 1;
        postInvalidate();
        return croppedBitmap;
    }

    public void setcirclecrop() {
        circlecropstat = true;
        postInvalidate();
    }

    public Bitmap hidecirclecrop(int color1) {
        circlecropstat = false;
        Bitmap croppedBitmap = Bitmap.createBitmap(empty, (int) (cx - r - 1), (int) (cy - r - 1), (int) (2 * r + 2), (int) (2 * r + 2));
        final Bitmap outputBitmap = croppedBitmap.copy(Bitmap.Config.ARGB_8888, true);

        final Path path = new Path();
        path.addCircle(
                r
                , r
                , r - 7
                , Path.Direction.CCW);

        final Canvas canvas1 = new Canvas(outputBitmap);
        canvas1.drawColor(color1);
        canvas1.clipPath(path);
        canvas1.drawBitmap(croppedBitmap, 0, 0, null);
        statint2 = 1;
        postInvalidate();
        return outputBitmap;
    }


    public void setdoodle() {
        bitmaphistory.add(Bitmap.createBitmap(
                bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), null, false));
        yo = 0;
        doodlestat = true;
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getContext(), doodlecolor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                doodlecolor = color;
                p2.setColor(doodlecolor);
            }
        });
        colorPicker.show();
        postInvalidate();
    }

    public void hidedoodle() {
        doodlestat = false;
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawPath(path, p2);
        p2.setColor(Color.BLACK);
        bitmap = empty.copy(Bitmap.Config.ARGB_8888, true);
        if (yo == 0 && path.isEmpty()) {
            bitmaphistory.remove(bitmaphistory.size() - 1);
        }
        path.reset();
        postInvalidate();
    }

    public void pickcolor() {
        pickcolorstat = true;
    }

    public void stoppickcolor() {
        pickcolorstat = false;
    }

    public void swapcolor(final Integer u) {
        bitmaphistory.add(Bitmap.createBitmap(
                bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), null, false));
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getContext(), u, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                bitmap = bitmaphistory.get(bitmaphistory.size() - 1).copy(Bitmap.Config.ARGB_8888, true);
                bitmaphistory.remove(bitmaphistory.size() - 1);
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
                empty.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
                for (int i = 0; i < bitmap.getWidth() * bitmap.getHeight(); i++) {
                    if (pixels[i] == u) {
                        bitmap.setPixel(i % bitmap.getWidth(), i / bitmap.getWidth(), color);
                    }
                }
                postInvalidate();
            }
        });
        colorPicker.show();
    }



    public void startblur() {
        blurstat = true;
        bitmaphistory.add(Bitmap.createBitmap(
                bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), null, false));
        postInvalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void stopblur() {
        blurstat = false;
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x + lx > canvas.getWidth()) {
            lx = canvas.getWidth() - x;
        }
        if (y + ly > canvas.getHeight()) {
            ly = canvas.getHeight() - y;
        }
        Bitmap croppedBitmap = Bitmap.createBitmap(empty, (int) x + 10, (int) y + 10, (int) lx - 20, (int) ly - 20);
        croppedBitmap = croppedBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap outputBitmap = Bitmap.createBitmap(croppedBitmap);
        final RenderScript renderScript = RenderScript.create(getContext());
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, croppedBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);
        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(10);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        postInvalidate();
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawBitmap(outputBitmap, x + 10, y + 10, null);
        bitmap = empty.copy(Bitmap.Config.ARGB_8888, true);
        statint = 1;
    }

    public void undo() {
        if ((xo > 0 || (!doodlestat)) && !blurstat) {
            if (bitmaphistory.size() > 0) {
                bitmap = bitmaphistory.get(bitmaphistory.size() - 1).copy(Bitmap.Config.ARGB_8888, true);
                bitmaphistory.remove(bitmaphistory.size() - 1);
                postInvalidate();
            }
            /*else {
                Toast.makeText(getContext(), "dfadsfaf", Toast.LENGTH_SHORT).show();
            }*/
        }
        if (doodlestat && xo == 0) {
            path.reset();
            bitmaphistory.remove(bitmaphistory.size() - 1);
            xo++;
            postInvalidate();
        }
    }

    public void setcolorfilter() {
        scale = new Paint();
        scale.setAntiAlias(true);
        bitmaphistory.add(Bitmap.createBitmap(
                bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), null, false));
    }

    public void oncolorfilterchanged(float hue, float brightness, int contrast, float saturation) {
        scale.setColorFilter(ColorFilterGenerator.adjustColor(brightness, contrast, saturation, hue));
        postInvalidate();
    }

    public void colorfilterapplied() {
        if(scale!=null) {
            bitmap = empty.copy(Bitmap.Config.ARGB_8888, true);
            scale = null;
        }
    }

    public void colorfiltercancelled() {
        if(scale!=null) {
            scale = null;
            bitmap = bitmaphistory.get(bitmaphistory.size() - 1).copy(Bitmap.Config.ARGB_8888, true);
            bitmaphistory.remove(bitmaphistory.size() - 1);
            postInvalidate();
        }
    }

    public void setTextBox(int color) {
        textstat = true;
        text = new TextPaint();
        text.setAntiAlias(true);
        text.setColor(color);
        text.setTextSize(textheight);
        postInvalidate();
        bitmaphistory.add(Bitmap.createBitmap(
                bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), null, false));
    }

    public void settextforfield(String t) {
        textstring = t;
        textwidth = (int) text.measureText(textstring);
        if (!textstring.isEmpty()) {
            p3.setColor(Color.CYAN);
            drawablestat = true;
        } else {
            p3.setColor(Color.TRANSPARENT);
            drawablestat = false;
        }
        postInvalidate();
    }

    public void textapplied(String x) {
        if(textstat) {
            textstring = x;
            textstat = false;
            p3.setColor(Color.TRANSPARENT);
            drawablestat = false;
            if(!textstring.isEmpty()) {
                canvas.drawBitmap(bitmap, 0, 0, null);
                canvas.drawText(textstring, (float) textx, (float) texty, text);
                bitmap = empty.copy(Bitmap.Config.ARGB_8888, true);
            }
            else{
                bitmap = bitmaphistory.get(bitmaphistory.size() - 1).copy(Bitmap.Config.ARGB_8888, true);
                bitmaphistory.remove(bitmaphistory.size() - 1);
                postInvalidate();
            }
            textx = 120;
            texty = 140;
            textheight = 70;
        }
    }

    public void textrejected() {
        if(textstat) {
            textstat = false;
            p3.setColor(Color.TRANSPARENT);
            drawablestat = false;
            bitmap = bitmaphistory.get(bitmaphistory.size() - 1).copy(Bitmap.Config.ARGB_8888, true);
            bitmaphistory.remove(bitmaphistory.size() - 1);
            postInvalidate();
            textx = 120;
            texty = 140;
            textheight = 70;
        }
    }

    public void updatepainttext() {
        text.setTextSize(textheight);
        textwidth = (int) text.measureText(textstring);
    }

    public void changetextcolor(int color) {
        text.setColor(color);
        postInvalidate();
    }

    public void setbold() {
        text.setFakeBoldText(true);
        postInvalidate();
    }

    public void setitalic() {
        text.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
        postInvalidate();
    }

    public void setunderline() {
        text.setUnderlineText(true);
        postInvalidate();
    }

    public void hidebold() {
        text.setFakeBoldText(false);
        postInvalidate();
    }

    public void hideitalic() {
        text.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        postInvalidate();
    }

    public void hideunderline() {
        text.setUnderlineText(false);
        postInvalidate();
    }

    public void setImageAdd(Bitmap b) {
        picstat = true;
        p3.setColor(Color.YELLOW);
        drawablestat = true;
        bitmaphistory.add(Bitmap.createBitmap(
                bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), null, false));
        bitmapx = b.copy(Bitmap.Config.ARGB_8888, true);
        bmpx = 50;
        bmpy = 50;
        postInvalidate();
    }

    public void cancelimageadd() {
        picstat = false;
        p3.setColor(Color.TRANSPARENT);
        drawablestat = false;
        postInvalidate();
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawBitmap(bitmapx, bmpx, bmpy, null);
        bitmap = empty.copy(Bitmap.Config.ARGB_8888, true);
    }

    public void updatepicsize(int textwidth) {
        Matrix matrix = new Matrix();
        matrix.postScale((float) (textwidth) / (float) bitmapx.getWidth(), (float) (textwidth) / (float) bitmapx.getWidth());
        bitmapx = Bitmap.createBitmap(
                bitmapx, 0, 0, bitmapx.getWidth(), bitmapx.getHeight(), matrix, true);
        bitmapx = bitmapx.copy(Bitmap.Config.ARGB_8888, true);
        postInvalidate();
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();

            Bitmap croppedBitmap = Bitmap.createBitmap(empty, (int) x - 40, (int) y -40, 80, 80);
            Matrix matrix = new Matrix();
            matrix.postScale(5.0f, 5.0f);
            croppedBitmap = Bitmap.createBitmap(
                    croppedBitmap, 0, 0, croppedBitmap.getWidth(), croppedBitmap.getHeight(), matrix, false);
            actInt.onZoom(croppedBitmap);
            return true;
        }
    }
    public void setInter(picInt inter){
        actInt = inter;
    }

}
