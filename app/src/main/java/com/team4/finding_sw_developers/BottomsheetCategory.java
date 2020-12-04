package com.team4.finding_sw_developers;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomsheetCategory extends BottomSheetDialogFragment {
    RadioGroup radioGroup;
    RadioButton[] radiobutton = new RadioButton[6];
    Button button;
    private int index=-1;

    public BottomsheetCategory(int index) {
        this.index = index;
    }

    private SelectListener selectListener;


    public void setSelectListener(SelectListener selectListener) {
        this.selectListener = selectListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.select_category_bottomsheet, container, false);

        radioGroup=v.findViewById(R.id.radio_group);
        radiobutton[0] = v.findViewById(R.id.design_radio_bt);
        radiobutton[1] = v.findViewById(R.id.graphic_radio_bt);
        radiobutton[2] = v.findViewById(R.id.web_radio_bt);
        radiobutton[3] = v.findViewById(R.id.app_radio_bt);
        radiobutton[4] = v.findViewById(R.id.server_radio_bt);
        radiobutton[5] = v.findViewById(R.id.db_radio_bt);

        if(index!=-1) radiobutton[index].setChecked(true);

        for(int i=0;i<6;i++){
            final int finalI = i;
            radiobutton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(radiobutton[finalI].isChecked()){
                        index=finalI;
                    }
                }
            });
        }

        button = v.findViewById(R.id.bottom_select_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectListener.Selecttype(index);
                dismiss();


            }
        });


        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupRatio(bottomSheetDialog);
            }
        });
        return dialog;
    }

    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout)
                bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = getBottomSheetDialogDefaultHeight();
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getBottomSheetDialogDefaultHeight() {//높이 길이 설정
        return getWindowHeight() * 85 / 100;
    }

    private int getWindowHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    interface SelectListener {
        void Selecttype(int index);
    }
}
