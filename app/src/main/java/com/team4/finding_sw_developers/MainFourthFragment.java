package com.team4.finding_sw_developers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.team4.finding_sw_developers.mypage.Edit_information;

public class MainFourthFragment extends Fragment {

    private Button editInfo_button, editInterest_button;
    private TextView userID_textView;
    private String userID;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_main_fourth, container, false);

        editInfo_button = (Button)v.findViewById(R.id.mypage_editInfo_button);
        editInterest_button = (Button)v.findViewById(R.id.mypage_editInterest_button);
        userID_textView = (TextView)v.findViewById(R.id.mypage_userID_textView);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userID = currentUser.getEmail();

        userID_textView.setText(userID);

        editInfo_button.setOnClickListener(editInfo_button_onClickListener);
        editInterest_button.setOnClickListener(editInterest_button_onClickListener);
        return v;
    }

    View.OnClickListener editInfo_button_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Edit_information.class);
            intent.putExtra("user_email", userID);
            startActivity(intent);
            getActivity().getSupportFragmentManager().beginTransaction().remove(MainFourthFragment.this).commit();
        }
    };

    View.OnClickListener editInterest_button_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Edit_information.class);
        }
    };
}