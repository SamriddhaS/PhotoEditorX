package com.example.myphotoeditorapp;

import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.myphotoeditorapp.Adapter.BrushColorAdaptar;
import com.example.myphotoeditorapp.Adapter.FontAdaptar;
import com.example.myphotoeditorapp.Interface.TextFragmentListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends BottomSheetDialogFragment implements BrushColorAdaptar.ColorAdapterListener
, FontAdaptar.FontAdaptarClickListener {

    private RecyclerView recyclerTextColor , recyclerTextFont;
    private EditText edtAddText ;
    private Button btnAddText ;
    private BrushColorAdaptar adaptar ;
    private FontAdaptar fontAdaptar ;
    private int defaultColor = Color.parseColor("#000000"); // Default Text Color is Black
    public static TextFragment mInstance ;
    private TextFragmentListener mListener;
    private Typeface typefaceSelected = Typeface.DEFAULT ;

    public void setmListener(TextFragmentListener mListener) {
        this.mListener = mListener;
    }

    public static TextFragment getInstance(){

        if (mInstance==null)
            mInstance = new TextFragment() ;

        return mInstance ;
    }


    public TextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtAddText = (EditText)view.findViewById(R.id.edtText);
        btnAddText = (Button) view.findViewById(R.id.btnAddText);
        recyclerTextColor = (RecyclerView)view.findViewById(R.id.recyclerTextColor);
        recyclerTextColor.setHasFixedSize(true);
        recyclerTextColor.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));

        recyclerTextFont = (RecyclerView)view.findViewById(R.id.recyclerTextFont);
        recyclerTextFont.setHasFixedSize(true);
        recyclerTextFont.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));

        adaptar = new BrushColorAdaptar(getContext(),this) ;
        recyclerTextColor.setAdapter(adaptar);

        fontAdaptar = new FontAdaptar(this,getContext());
        recyclerTextFont.setAdapter(fontAdaptar);

        btnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener!=null)
                    mListener.onAddTextButtonClick(typefaceSelected,edtAddText.getText().toString().trim(),defaultColor);

            }
        });
    }

    @Override
    public void onColorSelected(int color) {

        defaultColor = color ;
        if (!edtAddText.getText().toString().trim().isEmpty())
            edtAddText.setTextColor(defaultColor);

    }

    @Override
    public void onFontSelected(String fontName) {

        typefaceSelected = Typeface.createFromAsset(getContext().getAssets() ,new StringBuilder("fonts/").append(fontName).toString());
    }
}
