package com.example.myphotoeditorapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphotoeditorapp.Interface.FilterListFragmentListner;
import com.example.myphotoeditorapp.R;
import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.List;
import java.util.zip.Inflater;

public class ThumbnailAdaptar extends RecyclerView.Adapter<ThumbnailAdaptar.ThumbnailViewHolder> {

    private List<ThumbnailItem> thumbnailItems ;
    private FilterListFragmentListner mListener;
    private Context mContext ;
    private int selectionIndex = 0 ;

    public ThumbnailAdaptar(List<ThumbnailItem> thumbnailItems, FilterListFragmentListner mListener, Context mContext) {
        this.thumbnailItems = thumbnailItems;
        this.mListener = mListener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ThumbnailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThumbnailViewHolder(LayoutInflater.from(mContext).inflate(R.layout.thumb_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ThumbnailViewHolder holder, final int position) {

        holder.thumbnail.setImageBitmap(thumbnailItems.get(position).image);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFilterSelected(thumbnailItems.get(position).filter);
                selectionIndex = position ;
                notifyDataSetChanged();
            }
        });

        holder.filterName.setText(thumbnailItems.get(position).filterName);

        if (selectionIndex == position){
            holder.filterName.setTextColor(ContextCompat.getColor(mContext,R.color.selectedFilter));

        }else {
            holder.filterName.setTextColor(ContextCompat.getColor(mContext,R.color.normalFilter));
        }
    }

    @Override
    public int getItemCount() {
        return thumbnailItems.size();
    }

    class ThumbnailViewHolder extends RecyclerView.ViewHolder {

        private TextView filterName ;
        private ImageView thumbnail ;

        public ThumbnailViewHolder(@NonNull View itemView) {
            super(itemView);

            filterName = (TextView)itemView.findViewById(R.id.filterName);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}
