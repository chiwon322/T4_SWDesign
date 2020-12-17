package com.team4.finding_sw_developers;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.team4.finding_sw_developers.mypage.Edit_information;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class MainFourthFragment extends Fragment {

    private Button editInfo_button, writing_button;
    private TextView userID_textView;
    private String userID;
    private FirebaseAuth mAuth;

    CircleImageView image_profile;
    DatabaseReference reference;
    FirebaseUser fuser;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;
    private ToggleButton toggleButton;
    private LinearLayout postlayout;
    private TextView post_text;

    private ChangeStatusLisntener changeStatusLisntener;

    public void setChangeStatusLisntener(ChangeStatusLisntener changeStatusLisntener) {
        this.changeStatusLisntener = changeStatusLisntener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_main_fourth, container, false);
        editInfo_button = (Button)v.findViewById(R.id.mypage_editInfo_button);
        userID_textView = (TextView)v.findViewById(R.id.mypage_userID_textView);
        writing_button= v.findViewById(R.id.writing_bt);
        toggleButton=v.findViewById(R.id.toglebutton);
        postlayout=v.findViewById(R.id.posting_layout);
        post_text=v.findViewById(R.id.posting_text);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userID = currentUser.getEmail();

        userID_textView.setText(userID);

        writing_button.setOnClickListener(writing_button_onClickListener);
        editInfo_button.setOnClickListener(editInfo_button_onClickListener);
        if(MainActivity.getState()==2){
            toggleButton.setChecked(true);
            postlayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.stroke_design_green));
            post_text.setText("요구사항을 작성하시고,\n 딱 맞는 전문가와 거래를 진행하세요!");
            writing_button.setBackgroundColor(Color.parseColor("#B2F384"));
        }else{
            toggleButton.setChecked(false);
            postlayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.stroke_design_blue));
            post_text.setText("도움이 필요한 의뢰인을 직접 찾아\n 2배 이상의 수익을 올려보세요!");
            writing_button.setBackgroundColor(Color.parseColor("#BCECF3"));
        }

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleButton.isChecked()){
                    changeStatusLisntener.StatusChange(2);
                    postlayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.stroke_design_green));
                    post_text.setText("요구사항을 작성하시고,\n 딱 맞는 전문가와 거래를 진행하세요!");
                    writing_button.setBackgroundColor(Color.parseColor("#B2F384"));
                }else{
                    changeStatusLisntener.StatusChange(1);
                    postlayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.stroke_design_blue));
                    post_text.setText("도움이 필요한 의뢰인을 직접 찾아\n 2배 이상의 수익을 올려보세요!");
                    writing_button.setBackgroundColor(Color.parseColor("#BCECF3"));
                }
            }
        });

        //이미지 업로딩
        image_profile = v.findViewById(R.id.profile_image);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        fuser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext() == null) {
                    return;
                }
                User user = snapshot.getValue(User.class);
                if(user.getImageURL().equals("default")){
                    image_profile.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Glide.with(getContext()).load(user.getImageURL()).into(image_profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        image_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openImage();
            }
        });
        return v;
    }

    private void openImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading");
        pd.show();

        if(imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });

        }else{
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    View.OnClickListener editInfo_button_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), Edit_information.class);

            startActivity(intent);
            //getActivity().getSupportFragmentManager().beginTransaction().remove(MainFourthFragment.this).commit();
        }
    };

    View.OnClickListener writing_button_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(toggleButton.isChecked()){
                Intent intent = new Intent(getActivity(), ClientWritingFirst.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(getActivity(), ExpertWritingFirst.class);
                startActivity(intent);
            }


        }
    };



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data!= null && data.getData() != null){
            imageUri = data.getData();

            if(uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getContext(), "Upload is in progress", Toast.LENGTH_SHORT).show();
            }else{
                uploadImage();
            }
        }
    }
    interface ChangeStatusLisntener{
        void StatusChange(int status);
    }

}