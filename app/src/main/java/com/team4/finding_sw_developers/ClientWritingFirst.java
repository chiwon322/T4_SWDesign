package com.team4.finding_sw_developers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClientWritingFirst extends AppCompatActivity {

    private Button next_button;
    LinearLayout category_layout;
    TextView category_textview;
    private EditText editText;
    private Boolean first,second;
    private LinearLayout scrolllayout;
    private ArrayList<Boolean> arrayList = new ArrayList<>();
    private String[] CategoryString = {"디자인", "그래픽", "홈페이지", "어플리케이션", "서버", "데이터 베이스"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_writing_first);
        first=false;
        second=false;

        category_layout = findViewById(R.id.first_layout);
        category_textview = findViewById(R.id.first_category_text);
        scrolllayout = findViewById(R.id.scroll_layout);
        next_button=findViewById(R.id.first_next_bt);
        editText=findViewById(R.id.title_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText.length()<6) second= false;
                else second= true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        for (int i = 0; i < 6; i++) arrayList.add(false);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(first&&second){
                    Intent intent = new Intent(ClientWritingFirst.this,ClientWritingSecond.class);
                    startActivity(intent);
                }else if(!first){
                    Toast.makeText(ClientWritingFirst.this, "관심 분야를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(!second){
                    Toast.makeText(ClientWritingFirst.this, "6글자 이상의 제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        category_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomsheetCategory bottomsheetCategory = new BottomsheetCategory(arrayList);
                bottomsheetCategory.setSelectListener(new BottomsheetCategory.SelectListener() {
                    @Override
                    public void Selecttype(ArrayList<Boolean> arrayList1) {
                        arrayList = arrayList1;
                        scrolllayout.removeAllViews();
                        int count=0;

                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i)) {
                                count++;
                                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view = inflater.inflate(R.layout.card_layout, null);
                                final int finalI = i;
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(ClientWritingFirst.this, finalI +"", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                TextView textView = view.findViewById(R.id.card_textview);
                                textView.setText(CategoryString[i]);

                                scrolllayout.addView(view);
                            }
                        }
                        if(count==0) {
                            category_textview.setText("");
                            first=false;
                        }
                        else {
                            category_textview.setText(count+"개 선택");
                            first=true;
                        }

                    }
                });

                bottomsheetCategory.show(getSupportFragmentManager(), "category");
            }
        });
    }
}