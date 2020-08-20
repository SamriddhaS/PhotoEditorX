package com.example.myphotoeditorapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphotoeditorapp.R;

import java.util.ArrayList;
import java.util.List;

public class FrameAdaptar extends RecyclerView.Adapter<FrameAdaptar.FrameViewHolder> {

    private FrameAdaptarListener mListener ;
    private Context mContext ;
    private List<Integer> frameList ;
    private int frameSelected = -1 ;

    public FrameAdaptar(FrameAdaptarListener mListener, Context mContext) {
        this.mListener = mListener;
        this.mContext = mContext;
        frameList = getframeList();
    }

    private List<Integer> getframeList() {

        List<Integer> frames = new ArrayList<>();

        frames.add(R.drawable.frame2);
        frames.add(R.drawable.frame3);
        frames.add(R.drawable.frame7);
        frames.add(R.drawable.frame8);
        frames.add(R.drawable.frame9);
        frames.add(R.drawable.frame10);

        return frames ;


    }

    @NonNull
    @Override
    public FrameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FrameViewHolder(LayoutInflater.from(mContext).inflate(R.layout.frame_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final FrameViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onFrameSelected(frameList.get(position));
                frameSelected = position ;
                holder.frameTick.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });

        if (frameSelected!=position)
            holder.frameTick.setVisibility(View.INVISIBLE);


        holder.frame.setImageResource(frameList.get(position));

    }

    @Override
    public int getItemCount() {
        return frameList.size();
    }

    public class FrameViewHolder extends RecyclerView.ViewHolder{


        public ImageView frameTick , frame ;

        public FrameViewHolder(@NonNull View itemView) {
            super(itemView);

            frameTick = (ImageView)itemView.findViewById(R.id.ivFrameTick);
            frame = (ImageView)itemView.findViewById(R.id.ivFrame);
        }

    }
    public interface FrameAdaptarListener{

        void onFrameSelected(int frame);
    }
}
