package com.team4.finding_sw_developers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ClientWritingSecond extends AppCompatActivity {
    private LinearLayout first_layout;
    private TextView first_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_writing_second);

        first_textview=findViewById(R.id.second_category_text);
        first_layout=findViewById(R.id.second_layout);
        first_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClientWritingSecond.this);
                builder.setTitle("지역 선택");
                final int[] temp1 = new int[1];
                final int[] temp2 = new int[1];
                LayoutInflater layoutInflater = LayoutInflater.from(ClientWritingSecond.this);
                View v = layoutInflater.inflate(R.layout.totalsearch_local_setting, null);
                final String[] bigcity_string = {""};
                final String[] smallcity_string = {""};
                final TextView textView = v.findViewById(R.id.textview_result_city);
                final ListView listView1 = v.findViewById(R.id.big_city_listview);
                final ListView listView2 = v.findViewById(R.id.small_city_listview);
                final ArrayList<String> bigarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.bigcity)));
                TotalsearchCityAdapter totalsearchCityAdapter = new TotalsearchCityAdapter(bigarraylist);
                listView1.setAdapter(totalsearchCityAdapter);
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ArrayList<String> smallarraylist = null;
                        if (position == 0) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.total_middle)));
                        } else if (position == 1) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.seoul_middle)));
                        } else if (position == 2) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.incheon_middle)));
                        } else if (position == 3) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.daejeun_middle)));
                        } else if (position == 4) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.daegu_middle)));
                        } else if (position == 5) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.gangju_middle)));
                        } else if (position == 6) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.busan_middle)));
                        } else if (position == 7) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.ulsan_middle)));
                        } else if (position == 8) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.sejong_middle)));
                        } else if (position == 9) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.geunggi_middle)));
                        } else if (position == 10) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.gangwon_middle)));
                        } else if (position == 11) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.choongbook_middle)));
                        } else if (position == 12) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.choongnam_middle)));
                        } else if (position == 13) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.geungbook_middle)));
                        } else if (position == 14) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.geungnak_middle)));
                        } else if (position == 15) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.junbook_middle)));
                        } else if (position == 16) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.junnam_midlle)));
                        } else if (position == 17) {
                            smallarraylist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.jeaju_middle)));
                        }
                        bigcity_string[0] = (String) listView1.getAdapter().getItem(position);
                        temp1[0] = (int) listView1.getAdapter().getItemId(position);
                        temp2[0] = 0;
                        smallcity_string[0]="";
                        textView.setText(bigcity_string[0]);
                        TotalsearchCityAdapter totalsearchCityAdapter = new TotalsearchCityAdapter(smallarraylist);
                        listView2.setAdapter(totalsearchCityAdapter);
                    }
                });
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        smallcity_string[0] = (String) listView2.getAdapter().getItem(position);
                        temp2[0] = (int) listView2.getAdapter().getItemId(position);
                        textView.setText(bigcity_string[0] + " " + smallcity_string[0]);
                    }
                });
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // a = temp1[0];
                       // b = temp2[0];
                        first_textview.setText(bigcity_string[0] + " " + smallcity_string[0]);
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder.setView(v);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}