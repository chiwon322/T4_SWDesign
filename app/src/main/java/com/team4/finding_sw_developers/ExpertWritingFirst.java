package com.team4.finding_sw_developers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.team4.finding_sw_developers.Models.ClientAd;
import com.team4.finding_sw_developers.Models.ExpertAd;

import java.util.ArrayList;

public class ExpertWritingFirst extends AppCompatActivity {

    private Button next_button;
    LinearLayout category_layout;
    TextView category_textview;
    private EditText editText, context;
    private Boolean first, second;
    private ArrayList<Boolean> arrayList = new ArrayList<>();
    private String[] CategoryString = {"어플리케이션", "데이터 베이스", "디자인", "그래픽", "서버", "웹"};
    private FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference reference;
    // private User mUser;
    private ExpertAd expertAd;
    private int select_index=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_writing_first);
        first = false;
        second = false;
        expertAd= new ExpertAd();

        category_layout = findViewById(R.id.first_layout);
        category_textview = findViewById(R.id.first_category_text);
        next_button = findViewById(R.id.first_next_bt);
        editText = findViewById(R.id.title_text);
        context = findViewById(R.id.client_content);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText.length() < 6) second = false;
                else second = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select_index!=-1 && second&&context.length()>=30) {
                    //HashMap<String, Object> hashMap = new HashMap<>();
                    expertAd.setExpertcategory(select_index);
                    expertAd.setExperttitle(editText.getText().toString());
                    expertAd.setExpertcontext(context.getText().toString());

                    Intent intent = new Intent(ExpertWritingFirst.this, ExpertWritingSecond.class);
                    intent.putExtra("Data",expertAd);
                    startActivity(intent);
                } else if (select_index==-1) {
                    Toast.makeText(ExpertWritingFirst.this, "관심 분야를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                } else if (!second) {
                    Toast.makeText(ExpertWritingFirst.this, "제목을 6글자 이상의 제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(context.length()<30){
                    Toast.makeText(ExpertWritingFirst.this, "내용을 30글자 이상의 제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        category_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomsheetCategory bottomsheetCategory = new BottomsheetCategory(select_index);
                bottomsheetCategory.setSelectListener(new BottomsheetCategory.SelectListener() {
                    @Override
                    public void Selecttype(int index) {
                        if(index!=-1) {
                            select_index=index;
                            category_textview.setText(CategoryString[index]);
                        }

                    }
                });

                bottomsheetCategory.show(getSupportFragmentManager(), "category");
            }
        });
    }
}