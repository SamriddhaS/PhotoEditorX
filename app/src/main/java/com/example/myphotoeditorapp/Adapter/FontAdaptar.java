package com.example.myphotoeditorapp.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphotoeditorapp.R;

import java.util.ArrayList;
import java.util.List;

public class FontAdaptar extends RecyclerView.Adapter<FontAdaptar.FontViewHolder> {


    private FontAdaptarClickListener mListener ;
    private Context mContext ;
    private List<String> fontList ;
    private int rowSelected = -1 ;

    public FontAdaptar(FontAdaptarClickListener mListener, Context context) {
        this.mListener = mListener;
        mContext = context;
        fontList = getFontList();
    }

    private List<String> getFontList() {

        List<String> fonts = new ArrayList<>();

        fonts.add("Allura-Regular.otf");
        fonts.add("Arizonia-Regular.ttf");
        fonts.add("blackjack.otf");
        fonts.add("Chunk Five Print.otf");
        fonts.add("ChunkFive-Regular.otf");
        fonts.add("ColabBol.otf");
        fonts.add("ColabLig.otf");
        fonts.add("ColabMed.otf");
        fonts.add("ColabReg.otf");
        fonts.add("ColabThi.otf");
        fonts.add("DancingScript-Regular.otf");
        fonts.add("GrandHotel-Regular.otf");
        fonts.add("GreatVibes-Regular.otf");
        fonts.add("KaushanScript-Regular.otf");
        fonts.add("LeagueGothic-CondensedItalic.otf");
        fonts.add("LeagueGothic-CondensedRegular.otf");
        fonts.add("LeagueGothic-Italic.otf");
        fonts.add("LeagueGothic-Regular.otf");
        fonts.add("Lobster_1.3.otf");
        fonts.add("LobsterTwo-Bold.otf");
        fonts.add("LobsterTwo-BoldItalic.otf");
        fonts.add("LobsterTwo-Italic.otf");
        fonts.add("LobsterTwo-Regular.otf");
        fonts.add("Montserrat-Black.otf");
        fonts.add("Montserrat-BlackItalic.otf");
        fonts.add("Montserrat-Bold.otf");
        fonts.add("Montserrat-BoldItalic.otf");
        fonts.add("Montserrat-ExtraBold.otf");
        fonts.add("Montserrat-ExtraBoldItalic.otf");
        fonts.add("Montserrat-ExtraLight.otf");
        fonts.add("Montserrat-ExtraLightItalic.otf");
        fonts.add("Montserrat-Italic.otf");
        fonts.add("Montserrat-LightItalic.otf");
        fonts.add("Montserrat-Medium.otf");
        fonts.add("Montserrat-MediumItalic.otf");
        fonts.add("Montserrat-SemiBoldItalic.otf");
        fonts.add("Montserrat-Thin.otf");
        fonts.add("Montserrat-ThinItalic.otf");
        fonts.add("MontserratAlternates-Black.otf");
        fonts.add("MontserratAlternates-BlackItalic.otf");
        fonts.add("MontserratAlternates-Bold.otf");
        fonts.add("MontserratAlternates-BoldItalic.otf");
        fonts.add("MontserratAlternates-ExtraBold.otf");
        fonts.add("MontserratAlternates-ExtraBoldItalic.otf");
        fonts.add("MontserratAlternates-ExtraLight.otf");
        fonts.add("MontserratAlternates-ExtraLightItalic.otf");
        fonts.add("MontserratAlternates-Italic.otf");
        fonts.add("MontserratAlternates-Light.otf");
        fonts.add("MontserratAlternates-LightItalic.otf");
        fonts.add("MontserratAlternates-Medium.otf");
        fonts.add("MontserratAlternates-MediumItalic.otf");
        fonts.add("MontserratAlternates-Regular.otf");
        fonts.add("MontserratAlternates-SemiBold.otf");
        fonts.add("MontserratAlternates-Thin.otf");
        fonts.add("MontserratAlternates-ThinItalic.otf");
        fonts.add("Pacifico.ttf");
        fonts.add("Poppins-Black.otf");
        fonts.add("Poppins-BlackItalic.otf");
        fonts.add("Poppins-Bold.otf");
        fonts.add("Poppins-BoldItalic.otf");
        fonts.add("Poppins-ExtraBold.otf");
        fonts.add("Poppins-ExtraBoldItalic.otf");
        fonts.add("Poppins-ExtraLight.otf");
        fonts.add("Poppins-ExtraLightItalic.otf");
        fonts.add("Poppins-Italic.otf");
        fonts.add("Poppins-Light.otf");
        fonts.add("Poppins-LightItalic.otf");
        fonts.add("Poppins-Medium.otf");
        fonts.add("Poppins-MediumItalic.otf");
        fonts.add("Poppins-Regular.otf");
        fonts.add("Poppins-SemiBold.otf");
        fonts.add("Poppins-SemiBoldItalic.otf");
        fonts.add("Poppins-Thin.otf");
        fonts.add("Poppins-ThinItalic.otf");
        fonts.add("Quicksand-Bold.otf");
        fonts.add("Quicksand-BoldItalic.otf");
        fonts.add("Quicksand-Italic.otf");
        fonts.add("Quicksand-Light.otf");
        fonts.add("Quicksand-LightItalic.otf");
        fonts.add("Quicksand-Regular.otf");
        fonts.add("Quicksand_Dash.otf");
        fonts.add("Sofia-Regular.otf");
        fonts.add("SourceSansPro-Black.otf");
        fonts.add("SourceSansPro-BlackIt.otf");
        fonts.add("SourceSansPro-Bold.otf");
        fonts.add("SourceSansPro-BoldIt.otf");
        fonts.add("SourceSansPro-ExtraLight.otf");
        fonts.add("SourceSansPro-ExtraLightIt.otf");
        fonts.add("SourceSansPro-It.otf");
        fonts.add("SourceSansPro-Light.otf");
        fonts.add("SourceSansPro-LightIt.otf");
        fonts.add("SourceSansPro-Regular.otf");
        fonts.add("SourceSansPro-Semibold.otf");
        fonts.add("SourceSansPro-SemiboldIt.otf");
        fonts.add("Titillium-Black.otf");
        fonts.add("Titillium-Bold.otf");
        fonts.add("Titillium-BoldItalic.otf");
        fonts.add("Titillium-BoldUpright.otf");
        fonts.add("Titillium-Light.otf");
        fonts.add("Titillium-LightItalic.otf");
        fonts.add("Titillium-LightUpright.otf");
        fonts.add("Titillium-Regular.otf");
        fonts.add("Titillium-RegularItalic.otf");
        fonts.add("Titillium-RegularUpright.otf");
        fonts.add("Titillium-Semibold.otf");
        fonts.add("Titillium-SemiboldItalic.otf");
        fonts.add("Titillium-SemiboldUpright.otf");
        fonts.add("Titillium-Thin.otf");
        fonts.add("Titillium-ThinItalic.otf");
        fonts.add("Titillium-ThinUpright.otf");
        fonts.add("Windsong.ttf");


        return fonts ;
    }


    @NonNull
    @Override
    public FontViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FontViewHolder(LayoutInflater.from(mContext).inflate(R.layout.font_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final FontViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFontSelected(fontList.get(position));
                rowSelected = position;
                holder.fontCheck.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });


        if (rowSelected!=position)
            holder.fontCheck.setVisibility(View.INVISIBLE);

        holder.fontName.setText(fontList.get(position));

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets() ,new StringBuilder("fonts/").append(fontList.get(position)).toString());
        holder.fontDemo.setTypeface(typeface);

    }

    @Override
    public int getItemCount() {
        return fontList.size();
    }


    public class FontViewHolder extends RecyclerView.ViewHolder {

        public ImageView fontCheck ;
        public TextView fontDemo , fontName ;

        public FontViewHolder(@NonNull View itemView) {
            super(itemView);

            fontCheck = (ImageView)itemView.findViewById(R.id.ivFontTick) ;
            fontDemo = (TextView)itemView.findViewById(R.id.tvFontDemo);
            fontName = (TextView)itemView.findViewById(R.id.tvFontName);

        }

    }

    public interface FontAdaptarClickListener{

        public void onFontSelected(String fontName);
    }
}
