package com.team4.finding_sw_developers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<ListItem> mList;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
       private TextView title_txt,price_txt,review_txt;
       private ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);
            this.title_txt = (TextView) view.findViewById(R.id.item_title);
            this.price_txt = (TextView) view.findViewById(R.id.item_price);
            this.review_txt = (TextView) view.findViewById(R.id.item_review);
            this.imageView=(ImageView) view.findViewById(R.id.item_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Toast.makeText(view.getContext(), "1", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }


    public CustomAdapter(ArrayList<ListItem> list) {
        this.mList = list;

    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.registor_item_design, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {

        viewholder.title_txt.setText(mList.get(position).getTitle());
        viewholder.price_txt.setText(mList.get(position).getPrice());
        viewholder.review_txt.setText(mList.get(position).getReview());
        //viewholder.imageView.setImageDrawable(R.drawable.ic_launcher_foreground);

        /*viewholder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.star_switch.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.korean.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        viewholder.text.setGravity(Gravity.CENTER);
        viewholder.star_switch.setGravity(Gravity.CENTER);
        viewholder.korean.setGravity(Gravity.CENTER);



        viewholder.text.setText(mList.get(position).getId());
        viewholder.star_switch.setText(mList.get(position).getEnglish());
        viewholder.korean.setText(mList.get(position).getKorean());*/
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
