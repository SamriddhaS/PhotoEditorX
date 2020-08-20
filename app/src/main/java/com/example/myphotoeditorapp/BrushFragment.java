package com.example.myphotoeditorapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.example.myphotoeditorapp.Adapter.BrushColorAdaptar;
import com.example.myphotoeditorapp.Interface.BrushFragmentListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrushFragment extends BottomSheetDialogFragment implements BrushColorAdaptar.ColorAdapterListener {

    private SeekBar brushOpacity , brushSize;
    private RecyclerView recyclerBrushColor;
    private ToggleButton brushState ;
    private BrushColorAdaptar adaptar ;
    private BrushFragmentListener mListener ;
    public static BrushFragment mInstance ;

    public void setmListener(BrushFragmentListener mListener) {
        this.mListener = mListener;
    }

    public BrushFragment() {
        // Required empty public constructor
    }

    public static BrushFragment getInstance(){

        if (mInstance==null)
            mInstance = new BrushFragment();

        return mInstance ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_brush, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        brushSize = (SeekBar)view.findViewById(R.id.seekbarBrushSize);
        brushOpacity = (SeekBar)view.findViewById(R.id.seekbarOpacity);
        brushState = (ToggleButton)view.findViewById(R.id.btnBrushState);
        recyclerBrushColor = (RecyclerView)view.findViewById(R.id.recyclerBrushColor);
        recyclerBrushColor.setHasFixedSize(true);
        recyclerBrushColor.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        adaptar = new BrushColorAdaptar(getContext(),this);
        recyclerBrushColor.setAdapter(adaptar);

        brushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mListener!=null)
                    mListener.onBrushSizeChangedListener(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        brushOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mListener!=null)
                    mListener.onBrushOpacityChangedListener(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        brushState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mListener!=null)
                    mListener.onBrushStateChangedListener(isChecked);
            }
        });



    }


    @Override
    public void onColorSelected(int color) {

        brushState.setTextColor(color);
        if (mListener!=null)
            mListener.onBrushColorChangedListener(color);
    }
}
