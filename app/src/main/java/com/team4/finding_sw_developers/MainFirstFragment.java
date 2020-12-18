package com.team4.finding_sw_developers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team4.finding_sw_developers.Models.ClientAd;
import com.team4.finding_sw_developers.Models.ExpertAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class MainFirstFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ClientAd> clientarrayList = new ArrayList<>();
    private ArrayList<ExpertAd> expertarrayList = new ArrayList<>();
    private LinearLayout linearLayout1, linearLayout2, linearLayout3;
    private DatabaseReference reference;
    private String[] CategoryString = {"어플리케이션", "데이터 베이스", "디자인", "그래픽", "서버", "홈페이지"};
    private ProgressDialog dialog;
    private FirebaseDatabase database;
    private ImageView[] imageViews = new ImageView[6];


    public void setClickImageview(ClickImageview clickImageview) {
        this.clickImageview = clickImageview;
    }

    private ClickImageview clickImageview;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_main_first, container, false);
       /* recyclerView=v.findViewById(R.id.first_main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));*/
        database = FirebaseDatabase.getInstance();

        dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("데이터 불러오는 중");
        dialog.show();

        imageViews[0] = v.findViewById(R.id.app_image);
        imageViews[1] = v.findViewById(R.id.database_image);
        imageViews[2] = v.findViewById(R.id.design_image);
        imageViews[3] = v.findViewById(R.id.graphic_image);
        imageViews[4] = v.findViewById(R.id.server_image);
        imageViews[5] = v.findViewById(R.id.web_image);

        for (int i = 0; i < 6; i++) {
            int finalI = i;
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickImageview.clickListener((finalI + 1));
                }
            });
        }

        linearLayout1 = v.findViewById(R.id.main_fragment_layout1);
        linearLayout2 = v.findViewById(R.id.main_fragment_layout2);
        linearLayout3 = v.findViewById(R.id.new_see_service);


        if (MainActivity.getState() == 1) {
            reference = database.getReference(FirebaseId.User).child(FirebaseAuth.getInstance().getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<String> concernlist = new ArrayList<>();
                    int index = 0;
                    for (DataSnapshot ds : snapshot.child("interest_field").getChildren()) {
                        String value = ds.getValue().toString();
                        if (value.equals("true")) concernlist.add(CategoryString[index]);
                        index++;
                    }
                    reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ClientAd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                clientarrayList.clear();
                                linearLayout1.removeAllViews();
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    ClientAd item = snapshot1.getValue(ClientAd.class);
                                    clientarrayList.add(item);
                                }
                                ArrayList<ClientAd> concern_list = new ArrayList<>();
                                int concerncount = 0;
                                for (int i = 0; i < clientarrayList.size(); i++) {
                                    if (getContext() != null && concernlist.contains(CategoryString[clientarrayList.get(i).getClientcategory()])) {
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
                                        linearLayout1.addView(view);


                                    }
                                }
                                linearLayout2.removeAllViews();
                                ArrayList<ClientAd> see_list = clientarrayList;

                                Collections.sort(see_list, new Comparator<ClientAd>() {
                                    @Override
                                    public int compare(ClientAd t1, ClientAd t2) {
                                        if (t1.getClientvisit() <= t2.getClientvisit()) return 1;
                                        return -1;
                                    }
                                });
                                for (int i = 0; i < see_list.size(); i++) {
                                    if (getContext() != null) {
                                        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                                        View view = layoutInflater.inflate(R.layout.registor_item_design, container, false);
                                        TextView title_txt = (TextView) view.findViewById(R.id.item_title);
                                        TextView price_txt = (TextView) view.findViewById(R.id.item_price);
                                        TextView review_txt = (TextView) view.findViewById(R.id.item_review);
                                        ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
                                        setbackground(imageView, see_list.get(i).getClientcategory());
                                        title_txt.setText(see_list.get(i).getClienttitle());
                                        price_txt.setText(see_list.get(i).getClientbudget() + "0,000 원");
                                        review_txt.setText(CategoryString[see_list.get(i).getClientcategory()]);
                                        int finalI = i;
                                        int finalI1 = i;
                                        view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                ClientAd clientAd = see_list.get(finalI);
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
                                                        reference.child(FirebaseId.ClientVisit).setValue(see_list.get(finalI).getClientvisit() + 1);
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


                                                //ClientAd clientAd = see_list.get(finalI);
                                                //Toast.makeText(getContext(), arrayList.get(finalI).getClientkey()+"", Toast.LENGTH_SHORT).show();
                                                //reference = database.getReference(FirebaseId.ClientAd).child(clientAd.getClientkey());
                                                // reference.child(FirebaseId.ClientVisit).setValue(see_list.get(finalI).getClientvisit() + 1);
                                                // Toast.makeText(getContext(), clientAd.getClientvisit() + "", Toast.LENGTH_SHORT).show();
                                    /*Intent intent = new Intent(getContext(), MessageActivity.class);
                                    String user = arrayList.get(finalI).getId();
                                    intent.putExtra("userid", user);
                                    startActivity(intent);*/

                                            }
                                        });
                                        linearLayout2.addView(view);


                                    }
                                }
                            }
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            reference = database.getReference(FirebaseId.User).child(FirebaseAuth.getInstance().getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<String> concernlist = new ArrayList<>();
                    int index = 0;
                    for (DataSnapshot ds : snapshot.child("interest_field").getChildren()) {
                        String value = ds.getValue().toString();
                        if (value.equals("true")) concernlist.add(CategoryString[index]);
                        index++;
                    }
                    reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ExpertAd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                expertarrayList.clear();
                                linearLayout1.removeAllViews();
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    ExpertAd item = snapshot1.getValue(ExpertAd.class);
                                    expertarrayList.add(item);
                                }
                                ArrayList<ExpertAd> concern_list = new ArrayList<>();
                                int concerncount = 0;
                                for (int i = 0; i < expertarrayList.size(); i++) {
                                    if (getContext() != null && concernlist.contains(CategoryString[expertarrayList.get(i).getExpertcategory()])) {
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
                                                            Intent intent = new Intent(getContext(), ExpertPostingActivity.class);
                                                            intent.putExtra("expert_key", expertAd.getExpertkey());
                                                            startActivity(intent);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            }
                                        });
                                        linearLayout1.addView(view);


                                    }
                                }
                                linearLayout2.removeAllViews();
                                ArrayList<ExpertAd> see_list = expertarrayList;

                                Collections.sort(see_list, new Comparator<ExpertAd>() {
                                    @Override
                                    public int compare(ExpertAd t1, ExpertAd t2) {
                                        if (t1.getExpertvisit() <= t2.getExpertvisit()) return 1;
                                        return -1;
                                    }
                                });
                                for (int i = 0; i < see_list.size(); i++) {
                                    if (getContext() != null) {
                                        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                                        View view = layoutInflater.inflate(R.layout.registor_item_design, container, false);
                                        TextView title_txt = (TextView) view.findViewById(R.id.item_title);
                                        TextView price_txt = (TextView) view.findViewById(R.id.item_price);
                                        TextView review_txt = (TextView) view.findViewById(R.id.item_review);
                                        ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
                                        setbackground(imageView, see_list.get(i).getExpertcategory());
                                        title_txt.setText(see_list.get(i).getExperttitle());
                                        price_txt.setText(see_list.get(i).getExpertbudget() + "0,000 원");
                                        review_txt.setText(CategoryString[see_list.get(i).getExpertcategory()]);
                                        int finalI = i;
                                        int finalI1 = i;
                                        view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                ExpertAd expertAd = see_list.get(finalI);
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
                                                        reference.child(FirebaseId.ExpertVisit).setValue(see_list.get(finalI).getExpertvisit() + 1);
                                                        //Toast.makeText(getContext(), clientAd.getClientvisit() + "", Toast.LENGTH_SHORT).show();
                                    /*Intent intent = new Intent(getContext(), MessageActivity.class);
                                    String user = arrayList.get(finalI).getId();
                                    intent.putExtra("userid", user);
                                    startActivity(intent);*/
                                                        if (expertAd != null) {
                                                            Intent intent = new Intent(getContext(), ExpertPostingActivity.class);
                                                            intent.putExtra("expert_key", expertAd.getExpertkey());
                                                            startActivity(intent);
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });


                                                //ClientAd clientAd = see_list.get(finalI);
                                                //Toast.makeText(getContext(), arrayList.get(finalI).getClientkey()+"", Toast.LENGTH_SHORT).show();
                                                //reference = database.getReference(FirebaseId.ClientAd).child(clientAd.getClientkey());
                                                // reference.child(FirebaseId.ClientVisit).setValue(see_list.get(finalI).getClientvisit() + 1);
                                                // Toast.makeText(getContext(), clientAd.getClientvisit() + "", Toast.LENGTH_SHORT).show();
                                    /*Intent intent = new Intent(getContext(), MessageActivity.class);
                                    String user = arrayList.get(finalI).getId();
                                    intent.putExtra("userid", user);
                                    startActivity(intent);*/

                                            }
                                        });
                                        linearLayout2.addView(view);


                                    }
                                }
                            }
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (MainActivity.getState() == 1) {
            reference = database.getReference(FirebaseId.User).child(FirebaseAuth.getInstance().getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user == null) return;
                    ArrayList<String> seearraylist = user.getVisitlist();
                    if (seearraylist == null) return;
                    reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ClientAd);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                linearLayout3.removeAllViews();
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    ClientAd clientAd = snapshot1.getValue(ClientAd.class);
                                    if (seearraylist.contains(clientAd.getClientkey())) {
                                        if (getContext() != null) {
                                            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                                            View view = layoutInflater.inflate(R.layout.new_see_service_item, container, false);
                                            TextView title_txt = (TextView) view.findViewById(R.id.new_see_title);
                                            TextView price_txt = (TextView) view.findViewById(R.id.new_see_price);
                                            // TextView review_txt = (TextView) view.findViewById(R.id.item_review);
                                            ImageView imageView = (ImageView) view.findViewById(R.id.new_see_image);
                                            setbackground(imageView, clientAd.getClientcategory());
                                            title_txt.setText(clientAd.getClienttitle());
                                            price_txt.setText(clientAd.getClientbudget() + "0,000 원");
                                            //review_txt.setText(CategoryString[arrayList.get(i).getClientcategory()]);
                                            view.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if (clientAd != null) {
                                                        Intent intent = new Intent(getContext(), ClientPostingActivity.class);
                                                        intent.putExtra("client_key", clientAd.getClientkey());
                                                        startActivity(intent);
                                                    }
                                                   /* ClientAd clientAd = arrayList.get(finalI1);
                                                    //Toast.makeText(getContext(), arrayList.get(finalI).getClientkey()+"", Toast.LENGTH_SHORT).show();
                                                    reference = database.getReference(FirebaseId.ClientAd).child(clientAd.getClientkey());
                                                    reference.child(FirebaseId.ClientVisit).setValue(arrayList.get(finalI).getClientvisit() + 1);
                                                    Toast.makeText(getContext(), clientAd.getClientvisit() + "", Toast.LENGTH_SHORT).show();
                                    *//*Intent intent = new Intent(getContext(), MessageActivity.class);
                                    String user = arrayList.get(finalI).getId();
                                    intent.putExtra("userid", user);
                                    startActivity(intent);*/
                                                }
                                            });
                                            linearLayout3.addView(view);


                                        }
                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            reference = database.getReference(FirebaseId.User).child(FirebaseAuth.getInstance().getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user == null) return;
                    ArrayList<String> seearraylist = user.getVisitlist();
                    if (seearraylist == null) return;
                    reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ExpertAd);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                linearLayout3.removeAllViews();
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    ExpertAd expertAd = snapshot1.getValue(ExpertAd.class);
                                    if (seearraylist.contains(expertAd.getExpertkey())) {
                                        if (getContext() != null) {
                                            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                                            View view = layoutInflater.inflate(R.layout.new_see_service_item, container, false);
                                            TextView title_txt = (TextView) view.findViewById(R.id.new_see_title);
                                            TextView price_txt = (TextView) view.findViewById(R.id.new_see_price);
                                            // TextView review_txt = (TextView) view.findViewById(R.id.item_review);
                                            ImageView imageView = (ImageView) view.findViewById(R.id.new_see_image);
                                            setbackground(imageView, expertAd.getExpertcategory());
                                            title_txt.setText(expertAd.getExperttitle());
                                            price_txt.setText(expertAd.getExpertbudget() + "0,000 원");
                                            //review_txt.setText(CategoryString[arrayList.get(i).getClientcategory()]);
                                            view.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if (expertAd != null) {
                                                        Intent intent = new Intent(getContext(), ExpertPostingActivity.class);
                                                        intent.putExtra("expert_key", expertAd.getExpertkey());
                                                        startActivity(intent);
                                                    }
                                                   /* ClientAd clientAd = arrayList.get(finalI1);
                                                    //Toast.makeText(getContext(), arrayList.get(finalI).getClientkey()+"", Toast.LENGTH_SHORT).show();
                                                    reference = database.getReference(FirebaseId.ClientAd).child(clientAd.getClientkey());
                                                    reference.child(FirebaseId.ClientVisit).setValue(arrayList.get(finalI).getClientvisit() + 1);
                                                    Toast.makeText(getContext(), clientAd.getClientvisit() + "", Toast.LENGTH_SHORT).show();
                                    *//*Intent intent = new Intent(getContext(), MessageActivity.class);
                                    String user = arrayList.get(finalI).getId();
                                    intent.putExtra("userid", user);
                                    startActivity(intent);*/
                                                }
                                            });
                                            linearLayout3.addView(view);


                                        }
                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



        /*linearLayout3.removeAllViews();
        reference = database.getReference(FirebaseId.User).child("visitlist");
        for (int i = 0; i < arrayList.size(); i++) {
            if (getContext() != null) {
                reference = database.getReference(FirebaseId.User).child();


                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.new_see_service_item, container, false);
                TextView title_txt = (TextView) view.findViewById(R.id.new_see_title);
                TextView price_txt = (TextView) view.findViewById(R.id.new_see_price);
                //TextView review_txt = (TextView) view.findViewById(R.id.);
                ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
                title_txt.setText(arrayList.get(i).getClienttitle());
                price_txt.setText(arrayList.get(i).getClientbudget() + "0,000 원");
                //review_txt.setText(CategoryString[arrayList.get(i).getClientcategory()]);
                int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClientAd clientAd = arrayList.get(finalI);
                        //Toast.makeText(getContext(), arrayList.get(finalI).getClientkey()+"", Toast.LENGTH_SHORT).show();
                        reference = database.getReference(FirebaseId.ClientAd).child(clientAd.getClientkey());
                        reference.child(FirebaseId.ClientVisit).setValue(arrayList.get(finalI).getClientvisit() + 1);
                        Toast.makeText(getContext(), clientAd.getClientvisit() + "", Toast.LENGTH_SHORT).show();
                                    *//*Intent intent = new Intent(getContext(), MessageActivity.class);
                                    String user = arrayList.get(finalI).getId();
                                    intent.putExtra("userid", user);
                                    startActivity(intent);*//*
                    }
                });
                linearLayout1.addView(view);


            }
        }*/

        //CustomAdapter customAdapter = new CustomAdapter(arrayList);
        //recyclerView.setAdapter(customAdapter);

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

    interface ClickImageview {
        void clickListener(int index);
    }
}