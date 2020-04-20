package com.ravimishra.tradzhub.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ravimishra.tradzhub.Model.TabRecyclerViewModel;
import com.ravimishra.tradzhub.R;

import java.util.ArrayList;
import java.util.List;

public class MainTabStoreAdapter  extends RecyclerView.Adapter<MainTabStoreAdapter.viewholder> {

    private Context context;
    int[] mList;
    int[] imageArray = new int[9];
    List<TabRecyclerViewModel> productModel;
   //private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    public MainTabStoreAdapter(Context context, List<TabRecyclerViewModel> productModel) {
        this.context = context;
        this.productModel = productModel;
    }

    @NonNull
    @Override
    public MainTabStoreAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        imageArray[0] = R.drawable.pf1;
        imageArray[1] = R.drawable.pf4;
        imageArray[2] = R.drawable.pf3;
        imageArray[3] = R.drawable.pf4;
        imageArray[4] = R.drawable.pf3;
        imageArray[5] = R.drawable.pf1;
        imageArray[6] = R.drawable.pf1;
        imageArray[7] = R.drawable.pf4;
        imageArray[8] = R.drawable.pf3;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_store_row, parent, false);
       // itemView.setOnClickListener(mOnClickListener);
        return new MainTabStoreAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final TabRecyclerViewModel model = productModel.get(position);

        // holder.img.setImageResource(R.drawable.pf1);
        holder.productName.setText(model.getProductName());
       // holder.productRating.setText(model.getRating());
      //  holder.offer.setText(model.getOffer());
        holder.productPice.setText(model.getProctPrice());
    }

    @Override
    public int getItemCount() {
        return productModel.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView productName, productPice,productRating;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            //img = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPice = itemView.findViewById(R.id.productPrice);
           // productRating = itemView.findViewById(R.id.ratingId);
            Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/lato_regular.ttf");
            productName.setTypeface(typeFace);

        }
    }
}
