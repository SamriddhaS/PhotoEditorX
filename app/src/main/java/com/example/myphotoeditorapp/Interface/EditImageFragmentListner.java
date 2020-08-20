package com.example.myphotoeditorapp.Interface;

public interface EditImageFragmentListner {

    public void onBrightnessChanged(int brightness);
    public void onContrastChanged(float contrast);
    public void onSaturationChanged(float saturation);
    void onEditStart();
    void onEditStop();
}
