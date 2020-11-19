package com.team4.finding_sw_developers;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainFirstFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ListItem> arrayList=new ArrayList<>();
    private LinearLayout linearLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v=inflater.inflate(R.layout.fragment_main_first, container, false);

       /* recyclerView=v.findViewById(R.id.first_main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));*/

        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));
        arrayList.add(new ListItem("응용프로그래밍","4.8","100,000원"));

        linearLayout=v.findViewById(R.id.main_fragment_layout);
        for(int i=0;i<arrayList.size();i++){
            LayoutInflater layoutInflater= (LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.registor_item_design, container, false);
            TextView title_txt = (TextView) view.findViewById(R.id.item_title);
            TextView price_txt = (TextView) view.findViewById(R.id.item_price);
            TextView review_txt = (TextView) view.findViewById(R.id.item_review);
            ImageView imageView=(ImageView) view.findViewById(R.id.item_image);
            title_txt.setText(arrayList.get(i).getTitle());
            price_txt.setText(arrayList.get(i).getPrice());
            review_txt.setText(arrayList.get(i).getReview());
            linearLayout.addView(view);

        }

        //CustomAdapter customAdapter = new CustomAdapter(arrayList);
        //recyclerView.setAdapter(customAdapter);

        return v;
    }
}