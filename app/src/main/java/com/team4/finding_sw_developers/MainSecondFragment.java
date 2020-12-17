package com.team4.finding_sw_developers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team4.finding_sw_developers.Models.ClientAd;
import com.team4.finding_sw_developers.Models.ExpertAd;

import java.util.ArrayList;
import java.util.HashMap;

public class MainSecondFragment extends Fragment {
    private int index;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private String[] CategoryString = {"어플리케이션", "데이터 베이스", "디자인", "그래픽", "서버", "홈페이지"};
    private LinearLayout linearLayout;
    ArrayList<ClientAd> clientarrayList = new ArrayList<>();
    ArrayList<ExpertAd> expertarrayList = new ArrayList<>();

    public MainSecondFragment(int index) {
        this.index = index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_second, container, false);

        reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ClientAd);
        database = FirebaseDatabase.getInstance();
        linearLayout = v.findViewById(R.id.second_linear_layout);


        if (MainActivity.getState() == 1) {
            reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ClientAd);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        clientarrayList.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            ClientAd item = snapshot1.getValue(ClientAd.class);
                            if (index == 0) {
                                clientarrayList.add(item);
                            } else {
                                if (index != 0 && item.getClientcategory() == (index - 1))
                                    clientarrayList.add(item);
                            }
                        }
                        ArrayList<ClientAd> concern_list = new ArrayList<>();
                        int concerncount = 0;
                        for (int i = 0; i < clientarrayList.size(); i++) {
                            if (getContext() != null) {
                                concern_list.add(clientarrayList.get(i));
                                concerncount++;
                                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                                View view = layoutInflater.inflate(R.layout.registor_item_design, container, false);
                                TextView title_txt = (TextView) view.findViewById(R.id.item_title);
                                TextView price_txt = (TextView) view.findViewById(R.id.item_price);
                                TextView review_txt = (TextView) view.findViewById(R.id.item_review);
                                ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
                                setbackground(imageView, clientarrayList.get(i).getClientcategory());
                                title_txt.setText(clientarrayList.get(i).getClienttitle());
                                price_txt.setText(clientarrayList.get(i).getClientbudget() + "0,000 원");
                                review_txt.setText(CategoryString[clientarrayList.get(i).getClientcategory()]);
                                int finalI = i;
                                int finalI1 = i;
                                int finalConcerncount = concerncount;
                                int finalConcerncount1 = concerncount;
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ClientAd clientAd = concern_list.get(finalConcerncount - 1);
                                        reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());
                                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                User user = snapshot.getValue(User.class);
                                                ArrayList<String> seelist;
                                                if (user.getVisitlist() == null)
                                                    seelist = new ArrayList<>();
                                                else seelist = user.getVisitlist();
                                                if (!seelist.contains(clientAd.getClientkey())) {
                                                    seelist.add(clientAd.getClientkey());
                                                    HashMap<String, Object> hashMap = new HashMap<>();
                                                    hashMap.put("visitlist", seelist);
                                                    reference.updateChildren(hashMap);
                                                }
                                                //Toast.makeText(getContext(), arrayList.get(finalI).getClientkey()+"", Toast.LENGTH_SHORT).show();
                                                reference = database.getReference(FirebaseId.ClientAd).child(clientAd.getClientkey());
                                                reference.child(FirebaseId.ClientVisit).setValue(concern_list.get(finalConcerncount1 - 1).getClientvisit() + 1);
                                                //Toast.makeText(getContext(), clientAd.getClientvisit() + "", Toast.LENGTH_SHORT).show();
                                    /*Intent intent = new Intent(getContext(), MessageActivity.class);
                                    String user = arrayList.get(finalI).getId();
                                    intent.putExtra("userid", user);
                                    startActivity(intent);*/
                                                if (clientAd != null) {
                                                    Intent intent = new Intent(getContext(), ClientPostingActivity.class);
                                                    intent.putExtra("client_key", clientAd.getClientkey());
                                                    startActivity(intent);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

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
        }else{
            reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ExpertAd);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        expertarrayList.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            ExpertAd item = snapshot1.getValue(ExpertAd.class);
                            if (index == 0) {
                                expertarrayList.add(item);
                            } else {
                                if (index != 0 && item.getExpertcategory() == (index - 1))
                                    expertarrayList.add(item);
                            }
                        }
                        ArrayList<ExpertAd> concern_list = new ArrayList<>();
                        int concerncount = 0;
                        for (int i = 0; i < expertarrayList.size(); i++) {
                            if (getContext() != null) {
                                concern_list.add(expertarrayList.get(i));
                                concerncount++;
                                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                                View view = layoutInflater.inflate(R.layout.registor_item_design, container, false);
                                TextView title_txt = (TextView) view.findViewById(R.id.item_title);
                                TextView price_txt = (TextView) view.findViewById(R.id.item_price);
                                TextView review_txt = (TextView) view.findViewById(R.id.item_review);
                                ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
                                setbackground(imageView, expertarrayList.get(i).getExpertcategory());
                                title_txt.setText(expertarrayList.get(i).getExperttitle());
                                price_txt.setText(expertarrayList.get(i).getExpertbudget() + "0,000 원");
                                review_txt.setText(CategoryString[expertarrayList.get(i).getExpertcategory()]);
                                int finalI = i;
                                int finalI1 = i;
                                int finalConcerncount = concerncount;
                                int finalConcerncount1 = concerncount;
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ExpertAd expertAd = concern_list.get(finalConcerncount - 1);
                                        reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());
                                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                User user = snapshot.getValue(User.class);
                                                ArrayList<String> seelist;
                                                if (user.getVisitlist() == null)
                                                    seelist = new ArrayList<>();
                                                else seelist = user.getVisitlist();
                                                if (!seelist.contains(expertAd.getExpertkey())) {
                                                    seelist.add(expertAd.getExpertkey());
                                                    HashMap<String, Object> hashMap = new HashMap<>();
                                                    hashMap.put("visitlist", seelist);
                                                    reference.updateChildren(hashMap);
                                                }
                                                //Toast.makeText(getContext(), arrayList.get(finalI).getClientkey()+"", Toast.LENGTH_SHORT).show();
                                                reference = database.getReference(FirebaseId.ExpertAd).child(expertAd.getExpertkey());
                                                reference.child(FirebaseId.ExpertVisit).setValue(concern_list.get(finalConcerncount1 - 1).getExpertvisit() + 1);
                                                //Toast.makeText(getContext(), clientAd.getClientvisit() + "", Toast.LENGTH_SHORT).show();
                                    /*Intent intent = new Intent(getContext(), MessageActivity.class);
                                    String user = arrayList.get(finalI).getId();
                                    intent.putExtra("userid", user);
                                    startActivity(intent);*/
                                                if (expertAd != null) {
                                                    Intent intent = new Intent(getContext(), ClientPostingActivity.class);
                                                    intent.putExtra("client_key", expertAd.getExpertkey());
                                                    startActivity(intent);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

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
        }

        return v;
    }

    void setbackground(ImageView imageView, int category) {
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if (category == 0) {
            imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.design_background));
        } else if (category == 1) {
            imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.graphic_background));
        } else if (category == 2) {
            imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.web_background));
        } else if (category == 3) {
            imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.app_background));
        } else if (category == 4) {
            imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.server_background));
        } else if (category == 5) {
            imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.database_background));
        }

    }
}