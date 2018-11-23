package com.example.sestefan.proyecto.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sestefan.proyecto.R;

public class FilterDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.filter_dialog_fragment, container, false);
        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View v = getActivity().getLayoutInflater().inflate(R.layout.filter_dialog_fragment, null);

        Button btnApply = v.findViewById(R.id.btn_apply);
        btnApply.setOnClickListener(view -> apply());

        Button btnCancel = v.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(view -> cancel());
        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void apply() {
        getTargetFragment().onActivityResult(1, 200, null);
        dismiss();
    }

    private void cancel() {
        getTargetFragment().onActivityResult(1, 200, null);
        dismiss();
    }


}
