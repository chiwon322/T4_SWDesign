package com.team4.finding_sw_developers.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team4.finding_sw_developers.MainActivity;
import com.team4.finding_sw_developers.R;

public class PasswordRegisterActivity extends AppCompatActivity {
    private EditText editText_first;
    private EditText editText_second;
    private Button btn_next;
    private TextView tv_warning;
    private boolean fir_blind_mode,sec_blind_mode;
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private  ProgressDialog progressDialog;
    private Intent m_intent;
    private String user_email;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_register);

        m_intent = getIntent();
        user_email = m_intent.getExtras().getString("email");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("MEMBER");

        progressDialog = new ProgressDialog(PasswordRegisterActivity.this);
        progressDialog.setMessage("Please Wait for registering password");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        editText_first = findViewById(R.id.editText_password_first);
        editText_second = findViewById(R.id.editText_password_second);
        btn_next = findViewById(R.id.button_next);
        tv_warning = findViewById(R.id.warning);

        fir_blind_mode = true;
        sec_blind_mode=true;
        editText_first.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= editText_first.getRight() - editText_first.getTotalPaddingRight()) {
                        if (fir_blind_mode && editText_first.length() != 0) {
                            editText_first.setInputType(InputType.TYPE_CLASS_TEXT);
                            Drawable myDrawable = getResources().getDrawable(R.drawable.change_password_icon);
                            Drawable myDrawable1 = getResources().getDrawable(R.drawable.not_blind_icon);
                            myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                            myDrawable1.setBounds(0, 0, myDrawable1.getIntrinsicWidth(), myDrawable1.getIntrinsicHeight());
                            editText_first.setCompoundDrawables(myDrawable, null, myDrawable1, null);
                            fir_blind_mode = false;
                        } else if (!fir_blind_mode && editText_first.length() != 0) {
                            editText_first.setInputType(129);
                            Drawable myDrawable = getResources().getDrawable(R.drawable.change_password_icon);
                            Drawable myDrawable1 = getResources().getDrawable(R.drawable.blind_icon);
                            myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                            myDrawable1.setBounds(0, 0, myDrawable1.getIntrinsicWidth(), myDrawable1.getIntrinsicHeight());
                            editText_first.setCompoundDrawables(myDrawable, null, myDrawable1, null);
                            fir_blind_mode = true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        editText_second.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= editText_second.getRight() - editText_second.getTotalPaddingRight()) {
                        if (sec_blind_mode && editText_second.length() != 0) {
                            editText_second.setInputType(InputType.TYPE_CLASS_TEXT);
                            Drawable myDrawable = getResources().getDrawable(R.drawable.change_password_icon);
                            Drawable myDrawable1 = getResources().getDrawable(R.drawable.not_blind_icon);
                            myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                            myDrawable1.setBounds(0, 0, myDrawable1.getIntrinsicWidth(), myDrawable1.getIntrinsicHeight());
                            editText_second.setCompoundDrawables(myDrawable, null, myDrawable1, null);
                            sec_blind_mode = false;
                        } else if (!sec_blind_mode && editText_second.length() != 0) {
                            editText_second.setInputType(129);
                            Drawable myDrawable = getResources().getDrawable(R.drawable.change_password_icon);
                            Drawable myDrawable1 = getResources().getDrawable(R.drawable.blind_icon);
                            myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                            myDrawable1.setBounds(0, 0, myDrawable1.getIntrinsicWidth(), myDrawable1.getIntrinsicHeight());
                            editText_second.setCompoundDrawables(myDrawable, null, myDrawable1, null);
                            sec_blind_mode = true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        editText_first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText_first.getText().length() == 0) {
                    fir_blind_mode = true;
                    Drawable myDrawable = getResources().getDrawable(R.drawable.password_icon);
                    myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    editText_first.setCompoundDrawables(myDrawable, null, null, null);
                } else {
                    Drawable myDrawable = getResources().getDrawable(R.drawable.change_password_icon);
                    Drawable myDrawable1 = getResources().getDrawable(R.drawable.blind_icon);
                    myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    myDrawable1.setBounds(0, 0, myDrawable1.getIntrinsicWidth(), myDrawable1.getIntrinsicHeight());
                    editText_first.setCompoundDrawables(myDrawable, null, myDrawable1, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editText_second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText_second.getText().length() == 0) {
                    sec_blind_mode=true;
                    Drawable myDrawable = getResources().getDrawable(R.drawable.password_icon);
                    myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    editText_second.setCompoundDrawables(myDrawable, null, null, null);
                } else {
                    Drawable myDrawable = getResources().getDrawable(R.drawable.change_password_icon);
                    Drawable myDrawable1 = getResources().getDrawable(R.drawable.blind_icon);
                    myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    myDrawable1.setBounds(0, 0, myDrawable1.getIntrinsicWidth(), myDrawable1.getIntrinsicHeight());
                    editText_second.setCompoundDrawables(myDrawable, null, myDrawable1, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password1 = editText_first.getText().toString();
                String password2 = editText_second.getText().toString();

                if (!password1.equals(password2)) {
                    editText_second.setError("Please make sure your passwords match.");
                    return;
                } else if (password1.length() < 6) {
                    editText_first.setError("");
                    editText_second.setError("Password must me at least 6 characters long.");
                    return;
                } else {
                    progressDialog.show();
                    mUser.updatePassword(password1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                reference.child(stringReplace(user_email)).setValue("true");
                                Toast.makeText(PasswordRegisterActivity.this, "successfuly update", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }
                    });
                }
            }
        });

    }

    public static String stringReplace(String str){
        String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z]";
        str =str.replaceAll(match, "");
        return str;
    }
}