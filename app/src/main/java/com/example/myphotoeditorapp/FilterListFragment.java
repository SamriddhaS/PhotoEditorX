package com.example.myphotoeditorapp;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myphotoeditorapp.Adapter.ThumbnailAdaptar;
import com.example.myphotoeditorapp.Interface.EditImageFragmentListner;
import com.example.myphotoeditorapp.Interface.FilterListFragmentListner;
import com.example.myphotoeditorapp.Utils.BitmapUtils;
import com.example.myphotoeditorapp.Utils.SpaceItemDecoretion;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterListFragment extends BottomSheetDialogFragment implements FilterListFragmentListner{

    private RecyclerView recyclerView;
    private ThumbnailAdaptar adapter ;
    private FilterListFragmentListner mListener;
    private List<ThumbnailItem> thumbItems ;
    public static FilterListFragment mInstance ;
    public static Bitmap mBitmap ;


    public void setmListener(FilterListFragmentListner mListener) {
        this.mListener = mListener;
    }

    public FilterListFragment() {
        // Required empty public constructor
    }

    public static FilterListFragment getInstance(Bitmap saveBitmap){

        mBitmap = saveBitmap ;
        if (mInstance==null){
            mInstance = new FilterListFragment();
        }

        return mInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        thumbItems = new ArrayList<>();
        adapter = new ThumbnailAdaptar(thumbItems,mListener,getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new SpaceItemDecoretion(space));
        recyclerView.setAdapter(adapter);


        displayThumbnail(mBitmap);

    }

    public void displayThumbnail(final Bitmap bitmap) {

        Runnable run = new Runnable() {
            @Override
            public void run() {

                Bitmap thumbImage;

                if (bitmap == null){
                    thumbImage = BitmapUtils.getBitmapFromAssets(getContext(),MainActivity.pictureName,100,100);
                }else{
                    thumbImage = Bitmap.createScaledBitmap(bitmap,100,100,false);
                }

                if (thumbImage==null)
                    return;

                ThumbnailsManager.clearThumbs();
                thumbItems.clear();

                //add normal bitmap first
                ThumbnailItem item = new ThumbnailItem();
                item.image = thumbImage ; //Original Image
                item.filterName = "Normal";
                ThumbnailsManager.addThumb(item);

                List<Filter> filterList = FilterPack.getFilterPack(getContext());
                for (Filter currentFilter: filterList){

                    ThumbnailItem tItem = new ThumbnailItem();
                    tItem.image = thumbImage ;
                    tItem.filterName = currentFilter.getName();
                    tItem.filter = currentFilter ;
                    ThumbnailsManager.addThumb(tItem);

                }

                thumbItems.addAll(ThumbnailsManager.processThumbs(getContext()));

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        new Thread(run).start();

    }

    @Override
    public void onFilterSelected(Filter filter) {

        if (mListener!=null){
            mListener.onFilterSelected(filter);
        }

    }
}
