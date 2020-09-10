<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/clMain"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FFFFFF"
    tools:context=".Main2Activity">

    <HorizontalScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/imageView">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <ImageButton
                android:id="@+id/leftturn"
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:layout_marginLeft="10dp"
                android:layout_marginTop="5dp"
                android:layout_marginBottom="5dp"
                android:background="#000000"
                android:src="@drawable/ic_rotate_left_black_24dp" />

            <ImageButton
                android:id="@+id/rightturn"
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:layout_marginLeft="10dp"
                android:layout_marginTop="5dp"
                android:layout_marginBottom="5dp"
                android:background="#000000"
                android:src="@drawable/ic_rotate_right_black_24dp" />

            <ImageButton
                android:id="@+id/crop"
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:layout_marginLeft="10dp"
                android:layout_marginTop="5dp"
                android:layout_marginBottom="5dp"
                android:background="#000000"
                android:src="@drawable/ic_crop_black_24dp"
                android:tex