package com.team4.finding_sw_developers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team4.finding_sw_developers.Models.ClientAd;
import com.team4.finding_sw_developers.Models.ExpertAd;

import java.util.HashMap;

public class ExpertWritingSecond extends AppCompatActivity {
    private Button finish_bt;
    private ExpertAd expertAd = new ExpertAd();
    private EditText editText;
    private FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference reference;
    private String user_UID=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_writing_second);
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        user_UID = mAuth.getUid();


        expertAd = (ExpertAd) intent.getSerializableExtra("Data");

        editText = findViewById(R.id.third_budget_text);

        finish_bt = findViewById(R.id.third_finish_bt);

        finish_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.length() != 0) {
                    String budget= editText.getText().toString();
                    if(budget.length()==3){
                        String temp = budget.charAt(0)+","+budget.charAt(1)+budget.charAt(2);
                        budget=temp;
                    }else if(editText.getText().length()==4){
                        String temp = budget.charAt(0)+budget.charAt(1)+","+budget.charAt(2)+budget.charAt(3);
                        budget=temp;
                    }
                    expertAd.setExpertbudget(budget);

                    reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ExpertAd).push();
                    String key = reference.getKey();
                    HashMap<String,Object> hashMap = new HashMap<>();


                    hashMap.put(FirebaseId.ExpertKey,key);
                    hashMap.put(FirebaseId.ExpertCategory,expertAd.getExpertcategory());
                    hashMap.put(FirebaseId.Experttitle,expertAd.getExperttitle());
                    hashMap.put(FirebaseId.ExpertContext,expertAd.getExpertcontext());
                    hashMap.put(FirebaseId.ExpertBudget,expertAd.getExpertbudget());
                    hashMap.put(FirebaseId.Userid,mAuth.getUid());
                    hashMap.put(FirebaseId.ExpertVisit,0);
                    hashMap.put(FirebaseId.ExpertMatching,false);

                    reference.setValue(hashMap);

                    Intent intent = new Intent(ExpertWritingSecond.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
}