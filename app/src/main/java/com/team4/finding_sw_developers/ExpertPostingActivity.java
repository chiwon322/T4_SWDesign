package com.team4.finding_sw_developers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team4.finding_sw_developers.Models.ClientAd;
import com.team4.finding_sw_developers.Models.ExpertAd;

public class ExpertPostingActivity extends AppCompatActivity {

    private TextView title, context, region, start, end, prepare,category,budget,visit;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private ExpertAd expertAd=null;
    private Button button;
    private String[] CategoryString = {"어플리케이션", "데이터 베이스", "디자인", "그래픽", "서버", "웹"};
    private Switch aSwitch;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_posting);

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        String key = intent.getStringExtra("expert_key");

        title = findViewById(R.id.post_title);
        context = findViewById(R.id.post_context);
        budget=findViewById(R.id.post_budget);
        category=findViewById(R.id.post_category);
        visit=findViewById(R.id.post_visit);
        aSwitch=findViewById(R.id.matching_complete);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ExpertAd).child(expertAd.getExpertkey());
                if(b){
                    reference.child(FirebaseId.ExpertMatching).setValue(true);
                }else{
                    reference.child(FirebaseId.ExpertMatching).setValue(false);
                }
            }
        });



        button=findViewById(R.id.post_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpertPostingActivity.this, MessageActivity.class);
                intent.putExtra("userid", expertAd.getId());
                startActivity(intent);
            }
        });
        reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ExpertAd);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if(snapshot1.getValue(ExpertAd.class).getExpertkey().equals(key)){
                        expertAd  = snapshot1.getValue(ExpertAd.class);
                    }

                }

                title.setText(expertAd.getExperttitle().toString());
                context.setText(expertAd.getExpertcontext().toString());
                budget.setText(expertAd.getExpertbudget().toString()+"0,000");
                category.setText(CategoryString[expertAd.getExpertcategory()]);
                visit.setText("조회수 : "+expertAd.getExpertvisit());
                if(!expertAd.getExpertmatching()){
                    button.setClickable(true);
                    button.setText("연결하기");
                }else{
                    button.setClickable(false);
                    button.setText("매칭이 완료되었습니다.");
                }
                if(expertAd.getId().equals(user.getUid())){
                    aSwitch.setVisibility(View.VISIBLE);
                    aSwitch.setChecked(expertAd.getExpertmatching());
                }else{
                    aSwitch.setVisibility(View.GONE);
                    aSwitch.setChecked(expertAd.getExpertmatching());
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}