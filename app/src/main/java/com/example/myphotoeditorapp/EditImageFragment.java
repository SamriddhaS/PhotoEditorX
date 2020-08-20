package com.example.myphotoeditorapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.myphotoeditorapp.Interface.EditImageFragmentListner;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditImageFragment extends BottomSheetDialogFragment implements SeekBar.OnSeekBarChangeListener {

    private EditImageFragmentListner mListener ;
    private  SeekBar contrast , brightness , saturation ;

    public static EditImageFragment mInstance ;

    public static EditImageFragment getInstance(){

        if (mInstance==null){

            mInstance = new EditImageFragment();
        }
        return mInstance ;
    }


    public void setmListener(EditImageFragmentListner mListener) {
        this.mListener = mListener;
    }

    public EditImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contrast = (SeekBar)view.findViewById(R.id.seekbarContrast);
        saturation = (SeekBar)view.findViewById(R.id.seekbarSaturation);
        brightness = (SeekBar)view.findViewById(R.id.seekbarBrightness);


        brightness.setMax(200);
        brightness.setProgress(100);

        contrast.setMax(20);
        contrast.setProgress(0);

        saturation.setMax(30);
        saturation.setProgress(10);

        brightness.setOnSeekBarChangeListener(this);
        saturation.setOnSeekBarChangeListener(this);
        contrast.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            if (mListener!=null){

                if (seekBar.getId()==R.id.seekbarBrightness){

                    mListener.onBrightnessChanged(progress-100);

                }else if(seekBar.getId()==R.id.seekbarContrast){

                    progress += 10 ;
                    float value = .10f *progress ;
                    mListener.onContrastChanged(value);

                }else if (seekBar.getId()==R.id.seekbarSaturation){

                    float value = .10f*progress ;
                    mListener.onSaturationChanged(value);

                }
            }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

        if (mListener!=null)
            mListener.onEditStart();

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        if (mListener!=null)
            mListener.onEditStop();
    }

    public  void resetControls(){

        brightness.setProgress(100);
        contrast.setProgress(0);
        saturation.setProgress(10);
    }
}
