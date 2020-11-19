package com.team4.finding_sw_developers;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomsheetCategory extends BottomSheetDialogFragment {
    RadioGroup radioGroup;
    CheckBox[] checkBoxes = new CheckBox[6];
    Button button;
    private ArrayList<Boolean> arrayList;

    public BottomsheetCategory(ArrayList<Boolean> arrayList) {
        this.arrayList = arrayList;
    }

    private SelectListener selectListener;


    public void setSelectListener(SelectListener selectListener) {
        this.selectListener = selectListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.select_category_bottomsheet, container, false);


        checkBoxes[0] = v.findViewById(R.id.design_radio_bt);
        checkBoxes[1] = v.findViewById(R.id.graphic_radio_bt);
        checkBoxes[2] = v.findViewById(R.id.web_radio_bt);
        checkBoxes[3] = v.findViewById(R.id.app_radio_bt);
        checkBoxes[4] = v.findViewById(R.id.server_radio_bt);
        checkBoxes[5] = v.findViewById(R.id.db_radio_bt);
        for (int i = 0; i < 6; i++) {
            if (arrayList.get(i)) checkBoxes[i].setChecked(true);
            else checkBoxes[i].setChecked(false);
        }
        button = v.findViewById(R.id.bottom_select_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.clear();
                for(int i=0;i<6;i++){
                    arrayList.add(checkBoxes[i].isChecked());
                }
                selectListener.Selecttype(arrayList);
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
        void Selecttype(ArrayList<Boolean> arrayList1);
    }
}
