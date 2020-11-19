package com.team4.finding_sw_developers.signup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team4.finding_sw_developers.MainActivity;
import com.team4.finding_sw_developers.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class User_information_registration extends AppCompatActivity {

    private EditText name_editText;
    private Button complete_button;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String user_name, user_UID;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_registration);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("MEMBER");

        user = FirebaseAuth.getInstance().getCurrentUser();
        user_UID = user.getUid();

        name_editText = (EditText) findViewById(R.id.information_registration_name_editText);
        complete_button = (Button)findViewById(R.id.information_registration_complete_button);

        complete_button.setOnClickListener(complete_onClickListener);
    }

    private View.OnClickListener complete_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            user_name = name_editText.getText().toString();
            reference.child(user_UID).child("username").setValue(user_name);
            reference.child(user_UID).child("search").setValue(user_name);

            Intent intent = new Intent(User_information_registration.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}