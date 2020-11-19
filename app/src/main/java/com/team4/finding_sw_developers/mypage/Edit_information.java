package com.team4.finding_sw_developers.mypage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team4.finding_sw_developers.MainActivity;
import com.team4.finding_sw_developers.R;
import com.team4.finding_sw_developers.signup.PasswordRegisterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.PasswordAuthentication;

public class Edit_information extends AppCompatActivity {

    private Button complete_button;
    private EditText name_editText;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String user_UID, user_name = "";
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_information);

        user = FirebaseAuth.getInstance().getCurrentUser();
        user_UID = user.getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("MEMBER").child(user_UID);

        complete_button = (Button)findViewById(R.id.information_editing_complete_button);
        name_editText = (EditText)findViewById(R.id.information_editing_name_editText);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_name = snapshot.child("username").getValue().toString();
                name_editText.setText(user_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        complete_button.setOnClickListener(complete_button_onClickListener);
    }

    private View.OnClickListener complete_button_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String new_name = name_editText.getText().toString();

            reference.child("username").setValue(new_name);
            reference.child("search").setValue(new_name);

            finish();
        }
    };

}