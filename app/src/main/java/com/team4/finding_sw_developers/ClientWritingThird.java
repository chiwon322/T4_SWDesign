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

import java.util.HashMap;

public class ClientWritingThird extends AppCompatActivity {
    private Button finish_bt;
    private ClientAd clientAd = new ClientAd();
    private EditText editText;
    private FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference reference;
    private String user_UID=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_writing_third);
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        user_UID = mAuth.getUid();


        clientAd = (ClientAd) intent.getSerializableExtra("Data");

        editText = findViewById(R.id.third_budget_text);

        finish_bt = findViewById(R.id.third_finish_bt);

        finish_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.length() != 0) {
                    clientAd.setClientbudget(editText.getText().toString());

                    reference = FirebaseDatabase.getInstance().getReference(FirebaseId.ClientAd).push();
                    String key = reference.getKey();
                    HashMap<String,Object> hashMap = new HashMap<>();

                    hashMap.put(FirebaseId.ClientCategory,clientAd.getClientcategory());
                    hashMap.put(FirebaseId.Clienttitle,clientAd.getClienttitle());
                    hashMap.put(FirebaseId.ClientContext,clientAd.getClientcontext());
                    hashMap.put(FirebaseId.ClientRegion,clientAd.getClientregion());
                    hashMap.put(FirebaseId.ClientStarttime,clientAd.getClientstarttime());
                    hashMap.put(FirebaseId.ClientEndtime,clientAd.getClientendtime());
                    hashMap.put(FirebaseId.ClientPrepare,clientAd.getClientprepare());
                    hashMap.put(FirebaseId.ClientBudget,clientAd.getClientbudget());
                    hashMap.put(FirebaseId.Userid,mAuth.getUid());

                    reference.setValue(hashMap);

                    Intent intent = new Intent(ClientWritingThird.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
}