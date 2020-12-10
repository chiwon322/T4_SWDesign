package com.team4.finding_sw_developers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team4.finding_sw_developers.Models.ClientAd;

public class PostingActivity extends AppCompatActivity {

    private TextView title, context, region, start, end, prepare,category,budget,visit;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private ClientAd clientAd=null;
    private Button button;
    private String[] CategoryString = {"어플리케이션", "데이터 베이스", "디자인", "그래픽", "서버", "웹"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        String key = intent.getStringExtra("client_key");

        title = findViewById(R.id.post_title);
        context = findViewById(R.id.post_context);
        region = findViewById(R.id.post_region);
        start = findViewById(R.id.post_start_term);
        end = findViewById(R.id.post_end_term);
        prepare = findViewById(R.id.post_prepare);
        budget=findViewById(R.id.post_budget);
        category=findViewById(R.id.post_category);
        visit=findViewById(R.id.post_visit);



        button=findViewById(R.id.post_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostingActivity.this, MessageActivity.class);
                intent.putExtra("userid", clientAd.getId());
                startActivity(intent);
            }
        });
        reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ClientAd);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if(snapshot1.getValue(ClientAd.class).getClientkey().equals(key)){
                        clientAd= snapshot1.getValue(ClientAd.class);
                    }

                }
                title.setText(clientAd.getClienttitle().toString());
                context.setText(clientAd.getClientcontext().toString());
                region.setText(clientAd.getClientregion().toString());
                start.setText(clientAd.getClientstarttime().toString());
                end.setText(clientAd.getClientendtime().toString());
                prepare.setText(clientAd.getClientprepare().toString());
                budget.setText(clientAd.getClientbudget().toString()+"0,000");
                category.setText(CategoryString[clientAd.getClientcategory()]);
                visit.setText("조회수 : "+clientAd.getClientvisit());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}