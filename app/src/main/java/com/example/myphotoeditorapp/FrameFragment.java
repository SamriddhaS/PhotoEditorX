package com.example.myphotoeditorapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myphotoeditorapp.Adapter.FrameAdaptar;
import com.example.myphotoeditorapp.Interface.FrameFragmentListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FrameFragment extends BottomSheetDialogFragment implements FrameAdaptar.FrameAdaptarListener {

    private RecyclerView recyclerFrame ;
    private FrameAdaptar adaptar ;
    private Button addFrame;
    private FrameFragmentListener mListener ;
    private static FrameFragment mInstance ;
    private int frameSelected = -1 ;

    public FrameFragment() {
        // Required empty public constructor
    }

    public void setmListener(FrameFragmentListener mListener) {
        this.mListener = mListener;
    }

    public static FrameFragment getInstance(){

        if (mInstance==null)
            mInstance = new FrameFragment();

        return mInstance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frame, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addFrame = (Button)view.findViewById(R.id.btnAddFrame) ;
        recyclerFrame = (RecyclerView)view.findViewById(R.id.recyclerFrame);
        recyclerFrame.setHasFixedSize(true);
        recyclerFrame.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        adaptar = new FrameAdaptar(this,getContext());

        recyclerFrame.setAdapter(adaptar);

        addFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null)
                    mListener.onAddFrame(frameSelected);
            }
        });

    }

    @Override
    public void onFrameSelected(int frame) {

        frameSelected = frame ;
    }
}
