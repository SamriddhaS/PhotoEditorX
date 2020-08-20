package com.example.myphotoeditorapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myphotoeditorapp.Interface.AlertDialogFragmentListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AlertDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private Button saveAndExit , exit , cancel ;
    private AlertDialogFragmentListener mListener ;
    private static AlertDialogFragment mInstance ;

    public void setmListener(AlertDialogFragmentListener mListener) {
        this.mListener = mListener;
    }

    public static AlertDialogFragment getInstance(){

        if (mInstance==null)
            mInstance = new AlertDialogFragment() ;

        return mInstance;
    }


    public AlertDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveAndExit = (Button)view.findViewById(R.id.btnSaveAndExit);
        exit = (Button)view.findViewById(R.id.btnExitWithoutSaving);
        cancel = (Button)view.findViewById(R.id.btnCancel);

        saveAndExit.setOnClickListener(this);
        exit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnSaveAndExit :
                if (mListener!=null)
                    mListener.onSaveAndExit();

                break;
            case R.id.btnExitWithoutSaving:
                if (mListener!=null)
                    mListener.onExitWithoutSaving();

                break;

            case R.id.btnCancel:

                if (mListener!=null && mInstance!=null)
                    mListener.onCancelDialog(mInstance);

                break;
        }



    }
}
