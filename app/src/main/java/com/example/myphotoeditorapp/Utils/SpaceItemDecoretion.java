package com.example.myphotoeditorapp.Utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoretion extends RecyclerView.ItemDecoration {

    // For Adding padding to right of every recycler view item except the last item. Used in recycler view to show filter list.

    private int space;

    public SpaceItemDecoretion(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view)==state.getItemCount()-1){

            outRect.left = space ;
            outRect.right = 0 ;
        }else {

            outRect.left = 0 ;
            outRect.right = space ;
        }
        }

    }

