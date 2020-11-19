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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.net.PasswordAuthentication;

public class Edit_information extends AppCompatActivity {

    private Button complete_button;
    private EditText name_editText;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String user_UID, user_name = "";
    private FirebaseUser user;
    private CheckBox[] checkBoxes = new CheckBox[6];
    private String[] field_name = {"design", "graphic", "web", "app", "server", "database"};
    private Boolean chk[] = new Boolean[6];
    private ProgressDialog dialog;

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

        checkBoxes[0] = (CheckBox)findViewById(R.id.edit_information_design);
        checkBoxes[1] = (CheckBox)findViewById(R.id.edit_information_graphic);
        checkBoxes[2] = (CheckBox)findViewById(R.id.edit_information_web);
        checkBoxes[3] = (CheckBox)findViewById(R.id.edit_information_app);
        checkBoxes[4] = (CheckBox)findViewById(R.id.edit_information_server);
        checkBoxes[5] = (CheckBox)findViewById(R.id.edit_information_database);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("데이터 불러오는 중");
        dialog.show();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_name = snapshot.child("username").getValue().toString();
                name_editText.setText(user_name);
                int index = 0;
                for(DataSnapshot ds : snapshot.child("interest_field").getChildren()) {
                    String value = ds.getValue().toString();
                    if(value.equals("true"))
                        chk[index++] = true;
                    else chk[index++] = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 6; i++) {
                    if(chk[i])
                        checkBoxes[i].setChecked(true);
                    else checkBoxes[i].setChecked(false);
                }
                dialog.dismiss();
            }
        }, 1000);

        complete_button.setOnClickListener(complete_button_onClickListener);
    }

    private View.OnClickListener complete_button_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String new_name = name_editText.getText().toString();

            reference.child("username").setValue(new_name);
            reference.child("search").setValue(new_name);

            for(int i = 0; i < 6; i++) {
                if(checkBoxes[i].isChecked())
                    reference.child("interest_field").child(field_name[i]).setValue("true");
                else reference.child("interest_field").child(field_name[i]).setValue("false");
            }

            finish();
        }
    };

}