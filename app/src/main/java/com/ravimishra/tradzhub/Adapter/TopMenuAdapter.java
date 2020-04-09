package com.ravimishra.tradzhub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ravimishra.tradzhub.Model.TopMenuModel;
import com.ravimishra.tradzhub.R;

import java.util.List;
import java.util.Random;


public class TopMenuAdapter extends RecyclerView.Adapter<TopMenuAdapter.viewholder> {

    private Context context;
    int mList[];
    int[] imageArray = new int[6];

    List<TopMenuModel> menuModel;
    public TopMenuAdapter(Context context, List<TopMenuModel>  menuModel) {
        this.context = context;
        this.menuModel = menuModel;
    }

    @NonNull
    @Override
    public TopMenuAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        imageArray[0] = R.drawable.pf1;
        imageArray[1] = R.drawable.pf2;
        imageArray[2] = R.drawable.pf3;
        imageArray[3] = R.drawable.pf4;
        imageArray[4] = R.drawable.pf1;
        imageArray[5] = R.drawable.pf2;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_menu_row, parent, false);
        return new TopMenuAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final TopMenuModel model = menuModel.get(position);
        Random rand = new Random();
        holder.img.setImageResource(imageArray[position]);
        holder.topMenuText.setText(model.getCategoryName());

    }
    @Override
    public int getItemCount() {
        return 6;
    }

    class viewholder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView topMenuText;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.topMenuImage);
            topMenuText = itemView.findViewById(R.id.topMenuName);

        }
    }
}
