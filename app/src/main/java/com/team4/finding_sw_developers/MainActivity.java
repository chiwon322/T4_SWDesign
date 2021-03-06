package com.team4.finding_sw_developers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.team4.finding_sw_developers.signup.PasswordRegisterActivity;
import com.team4.finding_sw_developers.signup.User_information_registration;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private MainFirstFragment main_first_fragment= new MainFirstFragment();
    private MainSecondFragment main_second_fragment;
    private MainThirdFragment main_third_fragment = new MainThirdFragment();
    private MainFourthFragment main_fourth_fragment = new MainFourthFragment();

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String user_UID;
    private FirebaseUser user;
    private int select_index=0;
    private static int state=1;

    public static int getState() {
        return state;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Intent intent=getIntent();
        state=intent.getIntExtra("state",1);

        main_fourth_fragment.setChangeStatusLisntener(new MainFourthFragment.ChangeStatusLisntener() {
            @Override
            public void StatusChange(int status) {
                if(status==2){
                    chipNavigationBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.navigationbargreen));
                    state=status;
                }
                else {
                    chipNavigationBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.navigationbarblue));
                    state=status;
                }
            }
        });
        main_first_fragment.setClickImageview(new MainFirstFragment.ClickImageview() {
            @Override
            public void clickListener(int index) {
                select_index=index;
                chipNavigationBar.setItemSelected(R.id.second_menu,true);
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_UID = user.getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user_name = snapshot.child(user_UID).child("username").getValue().toString();

                if(user_name.equals("NULL")) {
                    Toast.makeText(MainActivity.this, "개인 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, User_information_registration.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        chipNavigationBar = findViewById(R.id.chipnavigation);
        if(state==1){
            chipNavigationBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.navigationbarblue));
        }else{
            chipNavigationBar.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.navigationbargreen));
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail_framlayout, main_first_fragment);
        fragmentTransaction.commit();
        chipNavigationBar.setItemSelected(R.id.first_menu,true);

       chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
           @Override
           public void onItemSelected(int i) {
               FragmentManager fragmentManager = getSupportFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               if (i == R.id.first_menu) {
                   fragmentTransaction.replace(R.id.detail_framlayout, main_first_fragment);
                   fragmentTransaction.commit();
               } else if (i == R.id.second_menu) {
                   main_second_fragment=new MainSecondFragment(select_index);
                   fragmentTransaction.replace(R.id.detail_framlayout, main_second_fragment);
                   fragmentTransaction.commit();
                   select_index=0;
               } else if (i == R.id.third_menu) {
                   fragmentTransaction.replace(R.id.detail_framlayout, main_third_fragment);
                   fragmentTransaction.commit();
               } else if (i == R.id.fourth_menu) {
                   fragmentTransaction.replace(R.id.detail_framlayout, main_fourth_fragment);
                   fragmentTransaction.commit();
               }
           }
       });

    }
    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status)  ;

        reference.updateChildren(hashMap);

    }
    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

}