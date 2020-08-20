package com.example.myphotoeditorapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.emoji.widget.EmojiTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphotoeditorapp.R;

import java.util.List;

public class EmojiAdaptar extends RecyclerView.Adapter<EmojiAdaptar.EmojiViewHolder> {

    private EmojiAdapterListener mListener;
    private List<String> emojiList;
    private Context mContext;

    public EmojiAdaptar(EmojiAdapterListener mListener, List<String> emojiList, Context mContext) {
        this.mListener = mListener;
        this.emojiList = emojiList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public EmojiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new EmojiViewHolder(LayoutInflater.from(mContext).inflate(R.layout.emoji_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final EmojiViewHolder holder, final int position) {

        holder.tvEmoji.setText(emojiList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {
                    mListener.onEmoijiItemSelected(emojiList.get(position));
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return emojiList.size();
    }

    public class EmojiViewHolder extends RecyclerView.ViewHolder {

        public EmojiTextView tvEmoji;


        public EmojiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEmoji = (EmojiTextView) itemView.findViewById(R.id.tvEmoji);


        }
    }

    public interface EmojiAdapterListener {

        void onEmoijiItemSelected(String emoji);

    }
}
