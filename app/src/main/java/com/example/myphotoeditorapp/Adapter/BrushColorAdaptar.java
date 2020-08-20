package com.example.myphotoeditorapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphotoeditorapp.R;

import java.util.ArrayList;
import java.util.List;

public class BrushColorAdaptar extends RecyclerView.Adapter<BrushColorAdaptar.BrushColorViewHolder> {

    private Context mContext;
    private ColorAdapterListener mListener;
    private List<Integer> colorList ;

    public BrushColorAdaptar(Context mContext, ColorAdapterListener mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.colorList = getColorList();
    }

    @NonNull
    @Override
    public BrushColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BrushColorViewHolder(LayoutInflater.from(mContext).inflate(R.layout.brush_color_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BrushColorViewHolder holder, final int position) {



        holder.brushColorSection.setCardBackgroundColor(colorList.get(position));

        holder.brushColorSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onColorSelected(colorList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }


    private List<Integer> getColorList() {

        List<Integer> colorList = new ArrayList<>();

        colorList.add(Color.parseColor("#000000"));
        colorList.add(Color.parseColor("#101010"));
        colorList.add(Color.parseColor("#0e2f44"));
        colorList.add(Color.parseColor("#001428"));
        colorList.add(Color.parseColor("#003366"));
        colorList.add(Color.parseColor("#ffffff"));
        colorList.add(Color.parseColor("#c0c5ce"));

        colorList.add(Color.parseColor("#fe9004"));
        colorList.add(Color.parseColor("#196db6"));
        colorList.add(Color.parseColor("#ffffdd"));
        colorList.add(Color.parseColor("#8bad9d"));
        colorList.add(Color.parseColor("#dfd7bb"));
        colorList.add(Color.parseColor("#beebe9"));
        colorList.add(Color.parseColor("#81f5ff"));

        colorList.add(Color.parseColor("#a0ffe6"));
        colorList.add(Color.parseColor("#ffffdd"));
        colorList.add(Color.parseColor("#ffd5e5"));
        colorList.add(Color.parseColor("#dfeacd"));
        colorList.add(Color.parseColor("#ebd2ed"));
        colorList.add(Color.parseColor("#d8cdea"));
        colorList.add(Color.parseColor("#c0fae2"));

        colorList.add(Color.parseColor("#c0f5fa"));
        colorList.add(Color.parseColor("#fac5c0"));
        colorList.add(Color.parseColor("#ffc1b2"));
        colorList.add(Color.parseColor("#ff9e00"));
        colorList.add(Color.parseColor("#ffd300"));
        colorList.add(Color.parseColor("#ff8800"));
        colorList.add(Color.parseColor("#000022"));

        colorList.add(Color.parseColor("#39a6c1"));
        colorList.add(Color.parseColor("#72c1d5"));
        colorList.add(Color.parseColor("#bfe3eb"));
        colorList.add(Color.parseColor("#ff4105"));
        colorList.add(Color.parseColor("#fe9b1d"));
        colorList.add(Color.parseColor("#fe9004"));
        colorList.add(Color.parseColor("#fe9004"));

        colorList.add(Color.parseColor("#ca8c6f"));
        colorList.add(Color.parseColor("#dcd4da"));
        colorList.add(Color.parseColor("#ecdad1"));
        colorList.add(Color.parseColor("#ffc1b2"));
        colorList.add(Color.parseColor("#c0c5ce"));
        colorList.add(Color.parseColor("#63a194"));
        colorList.add(Color.parseColor("#ceb795"));
        return colorList ;
    }


    public class BrushColorViewHolder extends RecyclerView.ViewHolder {


        public CardView brushColorSection ;

        public BrushColorViewHolder(@NonNull View itemView) {
            super(itemView);

            brushColorSection = itemView.findViewById(R.id.brushColorSection);
        }
    }

    public interface ColorAdapterListener {

        void onColorSelected(int color);
    }
}
