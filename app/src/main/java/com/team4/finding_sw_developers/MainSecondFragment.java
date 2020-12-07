package com.team4.finding_sw_developers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainSecondFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_main_second, container, false);
        Toast.makeText(getContext(), "second", Toast.LENGTH_SHORT).show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DeveloperAd");
        HashMap<String, Object> hashMap = new HashMap<>();
        reference= FirebaseDatabase.getInstance().getReference("DeveloperAd").push();
        String key = reference.getKey();
        hashMap.put("abc","name");
        hashMap.put("ABC","name");

        reference.setValue(hashMap);

        return v;
    }
}