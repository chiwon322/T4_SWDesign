package com.team4.finding_sw_developers;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team4.finding_sw_developers.Models.ClientAd;

import java.util.ArrayList;

public class MainFirstFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ClientAd> arrayList=new ArrayList<>();
    private LinearLayout linearLayout;
    private DatabaseReference reference;
    private String[] CategoryString = {"디자인", "그래픽", "홈페이지", "어플리케이션", "서버", "데이터 베이스"};


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View v=inflater.inflate(R.layout.fragment_main_first, container, false);
        Toast.makeText(getContext(), "first", Toast.LENGTH_SHORT).show();

       /* recyclerView=v.findViewById(R.id.first_main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));*/


        linearLayout=v.findViewById(R.id.main_fragment_layout);

        reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ClientAd);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    arrayList.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        ClientAd item = snapshot1.getValue(ClientAd.class);
                        arrayList.add(item);
                    }
                    for(int i=0;i<arrayList.size();i++) {
                        if(getContext()!=null) {
                            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                            View view = layoutInflater.inflate(R.layout.registor_item_design, container, false);
                            TextView title_txt = (TextView) view.findViewById(R.id.item_title);
                            TextView price_txt = (TextView) view.findViewById(R.id.item_price);
                            TextView review_txt = (TextView) view.findViewById(R.id.item_review);
                            ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
                            title_txt.setText(arrayList.get(i).getClienttitle());
                            price_txt.setText(arrayList.get(i).getClientbudget());
                            review_txt.setText(CategoryString[arrayList.get(i).getClientcategory()]);
                            final int finalI = i;
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getContext(), MessageActivity.class);
                                    String user = arrayList.get(finalI).getId();
                                    intent.putExtra("userid", user);
                                    startActivity(intent);
                                }
                            });

                            linearLayout.addView(view);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //CustomAdapter customAdapter = new CustomAdapter(arrayList);
        //recyclerView.setAdapter(customAdapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ClientAd);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    arrayList.clear();
                   for(DataSnapshot snapshot1:snapshot.getChildren()){
                       ClientAd item = snapshot1.getValue(ClientAd.class);
                       arrayList.add(item);
                   }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}