package com.example.myphotoeditorapp.Interface;

import com.example.myphotoeditorapp.AlertDialogFragment;

public interface AlertDialogFragmentListener {

    public void onSaveAndExit();
    public void onExitWithoutSaving();
    public void onCancelDialog(AlertDialogFragment instance);

}
