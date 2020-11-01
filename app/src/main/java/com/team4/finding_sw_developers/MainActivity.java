package com.team4.finding_sw_developers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private MainFirstFragment main_first_fragment= new MainFirstFragment();
    private MainSecondFragment main_second_fragment= new MainSecondFragment();
    private MainThirdFragment main_third_fragment = new MainThirdFragment();
    private MainFourthFragment main_fourth_fragment = new MainFourthFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        chipNavigationBar = findViewById(R.id.chipnavigation);

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
                   fragmentTransaction.replace(R.id.detail_framlayout, main_second_fragment);
                   fragmentTransaction.commit();
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
}