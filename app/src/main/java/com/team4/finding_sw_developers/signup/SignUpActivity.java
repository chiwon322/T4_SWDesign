package com.team4.finding_sw_developers.signup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.team4.finding_sw_developers.R;

public class SignUpActivity extends AppCompatActivity {
    private Button button_register;
    private EditText editText_email;
    private EditText editText_password;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Context context;
    private long  time;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        button_register = (Button) findViewById(R.id.button_signup_register);
        editText_email = (EditText) findViewById(R.id.editText_signup_email);
        editText_password = (EditText) findViewById(R.id.editText_signup_password);
        time = System.currentTimeMillis();


        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Please Wait for checking your email account");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        context =this;

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editText_email.getText().toString().trim();
                final String password = "000000";

                if (TextUtils.isEmpty(email)) {
                    editText_email.setError("Please fill the blank");
                    return;
                }
             /*   if (TextUtils.isEmpty(password)) {
                    editText_password.setError("Please fill the b lank");
                    return;
                }
                if (password.length() < 6) {
                    editText_password.setError("More than 6characters");
                    return;
                }
*/

                final FirebaseUser user = mAuth.getCurrentUser();

                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                                    View verifiedView = layoutInflater.inflate(R.layout.sign_up_sented_dialog,null);
                                    Button button = verifiedView.findViewById(R.id.got_it_bt);
                                    builder.setView(verifiedView);
                                    final AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            alertDialog.dismiss();
                                             //startActivity(new Intent(SignUpActivity.this,PasswordRegisterActivity.class));
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Toast.makeText(SignUpActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        if(e.getMessage().equals("The email address is badly formatted.")){
                            progressDialog.dismiss();
                            //editText_email.setError("Please enter a valid email address.");
                            Drawable myDrawable = context.getResources().getDrawable(R.drawable.error_email_icon);
                            myDrawable.setBounds( 0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                            editText_email.setCompoundDrawables(myDrawable, null, null, null);
                        }else{
                            signIn(email,password);
                        }

                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                    }
                });
            }
        });
        editText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText_email.getText().length() == 0) {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.email_icon);
                    myDrawable.setBounds( 0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    editText_email.setCompoundDrawables(myDrawable, null, null, null);
                } else {
                    Drawable myDrawable = context.getResources().getDrawable(R.drawable.change_email_icon);
                    myDrawable.setBounds( 0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    editText_email.setCompoundDrawables(myDrawable, null, null, null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void signIn(final String email, final String password){
        if(mAuth.getCurrentUser() == null){
            Toast.makeText(context, "Your email is already registerd", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(mAuth.getCurrentUser().isEmailVerified()){
                    progressDialog.dismiss();
                    //register();
                    Toast.makeText(context, "Registerd!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(context, PasswordRegisterActivity.class);
                    //intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                }else{
                    mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            LayoutInflater layoutInflater = LayoutInflater.from(context);
                            View verifiedView = layoutInflater.inflate(R.layout.sign_up_sented_again_dialog,null);
                            Button button = verifiedView.findViewById(R.id.got_it_bt);
                            builder.setView(verifiedView);
                            final AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //  startActivity(new Intent(SignUpActivity.this,PasswordRegisterActivity.class));
                                    alertDialog.dismiss();

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            LayoutInflater layoutInflater = LayoutInflater.from(context);
                            View verifiedView = layoutInflater.inflate(R.layout.sign_up_auth_failure_dialog,null);
                            Button button = verifiedView.findViewById(R.id.got_it_bt);
                            builder.setView(verifiedView);
                            final AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //  startActivity(new Intent(SignUpActivity.this,PasswordRegisterActivity.class));
                                    alertDialog.dismiss();

                                }
                            });
                        }
                    });
                }
            }
        });

    }

    /*private void register(){
        //유저이름,

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        HashMap<String,Object> hashMap =new HashMap<>();
        hashMap.put(FirebaseId.id,userid);
        hashMap.put(FirebaseId.name,"default");
        hashMap.put(FirebaseId.imageUrl,"default");
        hashMap.put(FirebaseId.status,"offline");
        hashMap.put(FirebaseId.auth,"true");
        hashMap.put(FirebaseId.timeIn,String.valueOf(time));
        hashMap.put(FirebaseId.timeOut,String.valueOf(time));

        hashMap.put(FirebaseId.regionFirst,"Seoul");
        hashMap.put(FirebaseId.regionSecond,"Busan");
        hashMap.put(FirebaseId.regionThird,"Daegu");

        hashMap.put(FirebaseId.interestingFirst,"Food");
        hashMap.put(FirebaseId.interstingSecond,"Language");
        hashMap.put(FirebaseId.interstingThird,"Night life");

        //여기 구글도 박자
        ArrayList<String> bookmark = new ArrayList<>();
        bookmark.add("init");
        hashMap.put(FirebaseId.bookmark,bookmark);


        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.e("error","됐다");
                }
            }
        });


    }*/

}
