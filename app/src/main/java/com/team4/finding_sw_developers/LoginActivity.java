package com.team4.finding_sw_developers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team4.finding_sw_developers.signup.SignUpActivity;

public class LoginActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog progressDialog;
    private Button button_login_signin;
    private ImageButton button_google_signup;
    private Button button_login_signup;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText editText_email, editText_password;
    private static final int RC_SIGN_IN = 6969;
    private Boolean isRegistered;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        isRegistered=false;
        button_login_signup = (Button) findViewById(R.id.button_login_signup);
        button_google_signup = (ImageButton) findViewById(R.id.button_login_google_signup);
        editText_email = (EditText) findViewById(R.id.editText_login_email);
        editText_password = (EditText) findViewById(R.id.editText_login_password);
        button_login_signin=findViewById(R.id.button_login_signin);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait for checking your google account");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        button_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));

            }
        });

        button_google_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                signIn();
            }
        });

        editText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText_email.getText().length() == 0) {
                    Drawable myDrawable = LoginActivity.this.getResources().getDrawable(R.drawable.email_icon);
                    myDrawable.setBounds( 0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    editText_email.setCompoundDrawables(myDrawable, null, null, null);
                } else {
                    Drawable myDrawable = LoginActivity.this.getResources().getDrawable(R.drawable.change_email_icon);
                    myDrawable.setBounds( 0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    editText_email.setCompoundDrawables(myDrawable, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText_password.getText().length() == 0) {
                    Drawable myDrawable = LoginActivity.this.getResources().getDrawable(R.drawable.password_icon);
                    myDrawable.setBounds( 0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    editText_password.setCompoundDrawables(myDrawable, null, null, null);
                } else {
                    Drawable myDrawable = LoginActivity.this.getResources().getDrawable(R.drawable.change_password_icon);
                    myDrawable.setBounds( 0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
                    editText_password.setCompoundDrawables(myDrawable, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button_login_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editText_email.getText().toString().trim();
                String password = editText_password.getText().toString().trim();


                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(LoginActivity.this);
                View warningview = layoutInflater.inflate(R.layout.login_error_alertdialog, null);
                Button button = warningview.findViewById(R.id.login_error_bt);
                /*button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.
                    }
                });*/

                builder.setView(warningview);
                final AlertDialog alertDialog = builder.create();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                if (TextUtils.isEmpty(email)) {
                    alertDialog.show();
                    //editText_email.setError("Please fill the blank");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    alertDialog.show();
                    //editText_password.setError("Please fill the blank");
                    return;
                }
                if (password.length() < 6) {
                    alertDialog.show();
                    //editText_password.setError("More than 6characters");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (!mAuth.getCurrentUser().isEmailVerified()) {
                            Toast.makeText(LoginActivity.this, "You should verify your email:" + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //setProfile();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        alertDialog.show();
                        // Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_SIGN_IN) {
            // siggIn() 함수에서 startActivityForResult 에서 응답이 성공했으면, 로그인 성공
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                return;
            } catch (Exception e) {
                Toast.makeText(this, "Login Failed,, try again plsase" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.e("ID", "firebaseAuthWithGoogle" + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    googleLoginCheck();
                } else {
                    Toast.makeText(LoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void googleLoginCheck(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                    assert user != null;
                    if (user.getId().equals(mAuth.getUid())){
                        //id 가 이제 같으면,
                        isRegistered = true;
                    }
                }

                if(!isRegistered) {
                    //register();
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else{

                }
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void signIn() {
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
}