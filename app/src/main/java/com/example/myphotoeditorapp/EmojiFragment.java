package com.example.myphotoeditorapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myphotoeditorapp.Adapter.EmojiAdaptar;
import com.example.myphotoeditorapp.Interface.EmojiFragmentListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ja.burhanrashid52.photoeditor.PhotoEditor;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmojiFragment extends BottomSheetDialogFragment implements EmojiAdaptar.EmojiAdapterListener {

    private RecyclerView recyclerEmoji ;
    public static EmojiFragment mInstance ;
    private EmojiFragmentListener mListener;
    private EmojiAdaptar emojiAdaptar ;

    public void setmListener(EmojiFragmentListener mListener) {
        this.mListener = mListener;
    }

    public static EmojiFragment getInstance() {
        if (mInstance==null)
            mInstance = new EmojiFragment();

        return mInstance ;
    }

    public EmojiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emoji, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerEmoji = (RecyclerView)view.findViewById(R.id.recyclerEmoiji);
        recyclerEmoji.setHasFixedSize(true);
        recyclerEmoji.setLayoutManager(new GridLayoutManager(getContext(),4,GridLayoutManager.HORIZONTAL,false));

        emojiAdaptar = new EmojiAdaptar(this, PhotoEditor.getEmojis(getContext()),getContext());
        recyclerEmoji.setAdapter(emojiAdaptar);

    }

    @Override
    public void onEmoijiItemSelected(String emoji) {

        mListener.onEmojiSelected(emoji);
    }
}
