package com.example.myphotoeditorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.provider.FontRequest;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.text.FontRequestEmojiCompatConfig;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myphotoeditorapp.Adapter.ViewPagerAdaptar;
import com.example.myphotoeditorapp.Interface.AlertDialogFragmentListener;
import com.example.myphotoeditorapp.Interface.BrushFragmentListener;
import com.example.myphotoeditorapp.Interface.EditImageFragmentListner;
import com.example.myphotoeditorapp.Interface.EmojiFragmentListener;
import com.example.myphotoeditorapp.Interface.FilterListFragmentListner;
import com.example.myphotoeditorapp.Interface.FrameFragmentListener;
import com.example.myphotoeditorapp.Interface.TextFragmentListener;
import com.example.myphotoeditorapp.Utils.BitmapUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.yalantis.ucrop.UCrop;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.Permission;
import java.util.List;
import java.util.UUID;

import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class MainActivity extends AppCompatActivity implements EditImageFragmentListner, FilterListFragmentListner
        , BrushFragmentListener, EmojiFragmentListener, TextFragmentListener, FrameFragmentListener, AlertDialogFragmentListener {

    private PhotoEditorView imagePreview;
    private PhotoEditor photoEditor;
    //private TabLayout tabLayout;
    //private ViewPager viewPager;
    private ConstraintLayout constraintLayout;
    private CardView btnImageFilter, btnEditImage, btnBrush, btnEmoji, btnText, btnInsertImage, btnAddFrame;
    private CardView btnCropImage;

    private Bitmap originalBitmap ;
    private Bitmap filteredBitmap; // For Backing Up image with filter applied
    private Bitmap  finalBitmap;  // final image after appling brightness,saturation,contrast .

    private FilterListFragment filterListFragment;
    private EditImageFragment editImageFragment;

    private int finalBrightness = 0;
    private float finalSaturation = 1.0f;
    private float finalContrast = 1.0f;

    public static final String pictureName = "kakasi.jpg";
    private Uri cropSelectedUri;
    public static final int PERMISSION_PICK_IMAGE = 1000;
    public static final int PERMISSION_INSERT_IMAGE = 1011;
    public static final int REQUEST_CAMERA = 1078;

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sam Photo Editor");

        btnEditImage = (CardView) findViewById(R.id.btnEditImage);
        btnImageFilter = (CardView) findViewById(R.id.btnFilterList);
        btnBrush = (CardView) findViewById(R.id.btnBrush);
        btnEmoji = (CardView) findViewById(R.id.btnEmoji);
        btnText = (CardView) findViewById(R.id.btnText);
        btnInsertImage = (CardView) findViewById(R.id.btnInsertImage);
        btnAddFrame = (CardView) findViewById(R.id.btnFrame);
        btnCropImage = (CardView) findViewById(R.id.btnCropImage);
        imagePreview = (PhotoEditorView) findViewById(R.id.imagepreview);
        constraintLayout = (ConstraintLayout) findViewById(R.id.coordinator);
        photoEditor = new PhotoEditor.Builder(this, imagePreview)
                .setPinchTextScalable(true)
                .build();


        if (originalBitmap == null && filteredBitmap == null && finalBitmap == null)
            loadImage();


        btnEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editImageFragment!=null) {
                    editImageFragment.show(getSupportFragmentManager(), editImageFragment.getTag());
                }else{
                editImageFragment = new EditImageFragment() ;
                editImageFragment.setmListener(MainActivity.this);
                editImageFragment.show(getSupportFragmentManager(),editImageFragment.getTag());
                }
            }
        });

        btnImageFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (filterListFragment != null) {
                    filterListFragment.show(getSupportFragmentManager(), filterListFragment.getTag());
                } else {

                    filterListFragment = FilterListFragment.getInstance(null);
                    filterListFragment.setmListener(MainActivity.this);
                    filterListFragment.show(getSupportFragmentManager(), filterListFragment.getTag());

                }

            }
        });

        btnBrush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                photoEditor.setBrushDrawingMode(true);
                BrushFragment brushFragment = BrushFragment.getInstance();
                brushFragment.setmListener(MainActivity.this);
                brushFragment.show(getSupportFragmentManager(), brushFragment.getTag());
            }
        });

        btnEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initEmojiLibrary();
                EmojiFragment emojiFragment = EmojiFragment.getInstance();
                emojiFragment.setmListener(MainActivity.this);
                emojiFragment.show(getSupportFragmentManager(), emojiFragment.getTag());

            }
        });

        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextFragment textFragment = TextFragment.getInstance();
                textFragment.setmListener(MainActivity.this);
                textFragment.show(getSupportFragmentManager(), textFragment.getTag());
            }
        });

        btnInsertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertImageToPicture();

            }
        });

        btnAddFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FrameFragment frameFragment = FrameFragment.getInstance();
                frameFragment.setmListener(MainActivity.this);
                frameFragment.show(getSupportFragmentManager(), frameFragment.getTag());

            }
        });

        btnCropImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startCrop(BitmapUtils.getImageUri(MainActivity.this, finalBitmap));

            }
        });


        //tabLayout = (TabLayout) findViewById(R.id.tabs);
        //viewPager = (ViewPager) findViewById(R.id.viewpager);
        //setUpViewPager(viewPager);
        //tabLayout.setupWithViewPager(viewPager);

    }

    private void initEmojiLibrary() {

        //For Using Emoji Library We need to initilze EmojiCompact
        FontRequest fontRequest = new FontRequest(
                "com.example.fontprovider",
                "com.example",
                "emoji compat Font Query",
                R.array.photo_editor_emoji);
        EmojiCompat.Config config = new FontRequestEmojiCompatConfig(this, fontRequest);
        EmojiCompat.init(config);
    }

    private void startCrop(Uri cropSelectedUri) {

        String destinationFileName = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();

        UCrop uCrop = UCrop.of(cropSelectedUri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop.start(MainActivity.this);

    }

    private void insertImageToPicture() {

        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                        if (multiplePermissionsReport.areAllPermissionsGranted()) {

                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, PERMISSION_INSERT_IMAGE);

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                        Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }).check();

    }

    private void loadImage() {

        originalBitmap = BitmapUtils.getBitmapFromAssets(this, pictureName, 300, 300);
        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

        imagePreview.getSource().setImageBitmap(originalBitmap);

    }

    private void setUpViewPager(ViewPager viewPager) {

        ViewPagerAdaptar viewPagerAdaptar = new ViewPagerAdaptar(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        FilterListFragment filterListFragment = new FilterListFragment();
        filterListFragment.setmListener(this);

        EditImageFragment editImageFragment = new EditImageFragment();
        editImageFragment.setmListener(this);

        viewPagerAdaptar.addFragment(filterListFragment, "Filter List");
        viewPagerAdaptar.addFragment(editImageFragment, "Edit Image");

        viewPager.setAdapter(viewPagerAdaptar);

    }

    @Override
    public void onBrightnessChanged(int brightness) {

        finalBrightness = brightness;
        Filter mFilter = new Filter();
        mFilter.addSubFilter(new BrightnessSubFilter(brightness));
        imagePreview.getSource().setImageBitmap(mFilter.processFilter(finalBitmap.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onContrastChanged(float contrast) {
        finalContrast = contrast;
        Filter mFilter = new Filter();
        mFilter.addSubFilter(new ContrastSubFilter(contrast));
        imagePreview.getSource().setImageBitmap(mFilter.processFilter(finalBitmap.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onSaturationChanged(float saturation) {
        finalSaturation = saturation;
        Filter mFilter = new Filter();
        mFilter.addSubFilter(new SaturationSubfilter(saturation));
        imagePreview.getSource().setImageBitmap(mFilter.processFilter(finalBitmap.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onEditStart() {

    }

    @Override
    public void onEditStop() {

        Bitmap bitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Filter mFilter = new Filter();
        mFilter.addSubFilter(new BrightnessSubFilter(finalBrightness));
        mFilter.addSubFilter(new ContrastSubFilter(finalContrast));
        mFilter.addSubFilter(new SaturationSubfilter(finalSaturation));

        finalBitmap = mFilter.processFilter(bitmap);
        imagePreview.getSource().setImageBitmap(finalBitmap);


    }

    @Override
    public void onFilterSelected(Filter filter) {

        resetControll();

        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        imagePreview.getSource().setImageBitmap(filter.processFilter(filteredBitmap));
        finalBitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);
    }

    private void resetControll() {

        editImageFragment = new EditImageFragment() ;
        editImageFragment.setmListener(this);

        finalBrightness = 0;
        finalContrast = 1.0f;
        finalSaturation = 1.0f;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.openItem:
                selectImageFromGallary();
                return true;
            case R.id.saveItem:
                saveImageInGallary();
                return true;

            case R.id.openCamera:
                openCamera();
                return true;
            default:
                return false;

        }

    }

    private void openCamera() {

        Dexter.withContext(this).withPermissions(Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                if (multiplePermissionsReport.areAllPermissionsGranted()) {

                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Pic");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "Taken Using Camera");
                    cropSelectedUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, cropSelectedUri);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();

    }

    private void saveImageInGallary() {

        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                        if (multiplePermissionsReport.areAllPermissionsGranted()) {

                            photoEditor.saveAsBitmap(new OnSaveBitmap() {
                                @Override
                                public void onBitmapReady(Bitmap saveBitmap) {
                                    try {

                                        imagePreview.getSource().setImageBitmap(saveBitmap);
                                        final String path = BitmapUtils.insertImage(getContentResolver()
                                                , saveBitmap, System.currentTimeMillis() + "_profile.jpeg", null);

                                        if (!TextUtils.isEmpty(path)) {

                                            Snackbar snackbar = Snackbar.make(constraintLayout, "Image Saved To Gallary", Snackbar.LENGTH_LONG)
                                                    .setAction("Open", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            openImage(path);
                                                        }
                                                    });
                                            snackbar.show();
                                        } else {

                                            Snackbar snackbar = Snackbar.make(constraintLayout, "Operation Failed", Snackbar.LENGTH_LONG);
                                            snackbar.show();

                                        }


                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Exception e) {

                                }
                            });

                        } else {

                            Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest();

                    }
                }).check();


    }

    private void openImage(String path) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(path), "image/*");
        startActivity(intent);

    }

    private void selectImageFromGallary() {

        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                        if (multiplePermissionsReport.areAllPermissionsGranted()) {

                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, PERMISSION_PICK_IMAGE);
                        } else {

                            Toast.makeText(MainActivity.this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PERMISSION_PICK_IMAGE) {

            //cropSelectedUri = data.getData();
            Bitmap bitmap = BitmapUtils.getBitmapFromGallery(this, data.getData(), 800, 800);

            //Clear Bitmap memory
            originalBitmap.recycle();
            finalBitmap.recycle();
            filteredBitmap.recycle();

            originalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
            filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

            imagePreview.getSource().setImageBitmap(originalBitmap);
            bitmap.recycle();

            //Render selected Image Thumbnail
            //Fix Bug
            filterListFragment = FilterListFragment.getInstance(originalBitmap);
            filterListFragment.setmListener(this);

            //Reset Image Edit Seekbars
            resetControll();
        }

        if (resultCode == RESULT_OK && requestCode == PERMISSION_INSERT_IMAGE) {

            Bitmap bitmap = BitmapUtils.getBitmapFromGallery(this, data.getData(), 150, 150);
            photoEditor.addImage(bitmap);

        }

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            handelCropResult(data);
        }

        if (resultCode == RESULT_OK && requestCode == UCrop.RESULT_ERROR) {

            handelCropError(data);
        }

        if (resultCode == RESULT_OK && requestCode == REQUEST_CAMERA) {


            Bitmap bitmap = BitmapUtils.getBitmapFromGallery(this, cropSelectedUri, 800, 800);

            //Clear Bitmap memory
            originalBitmap.recycle();
            finalBitmap.recycle();
            filteredBitmap.recycle();

            originalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
            filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

            imagePreview.getSource().setImageBitmap(originalBitmap);
            bitmap.recycle();

            //Render selected Image Thumbnail
            //Fix Bug
            filterListFragment = FilterListFragment.getInstance(originalBitmap);
            filterListFragment.setmListener(this);

            resetControll();


        }

    }

    private void handelCropError(Intent data) {

        final Throwable cropError = UCrop.getError(data);
        if (cropError != null)
            Toast.makeText(this, "" + cropError.getMessage(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "An Error Occurred", Toast.LENGTH_SHORT).show();
    }

    private void handelCropResult(Intent data) {

        final Uri result = UCrop.getOutput(data);
        if (result != null) {
            imagePreview.getSource().setImageURI(result);
            Bitmap bitmap = ((BitmapDrawable) imagePreview.getSource().getDrawable()).getBitmap();
            originalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            finalBitmap = originalBitmap;
            filteredBitmap = originalBitmap;

        } else
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBrushSizeChangedListener(float size) {

        photoEditor.setBrushSize(size);
    }

    @Override
    public void onBrushOpacityChangedListener(int opacity) {

        photoEditor.setOpacity(opacity);
    }

    @Override
    public void onBrushColorChangedListener(int color) {

        photoEditor.setBrushColor(color);
    }

    @Override
    public void onBrushStateChangedListener(boolean isEraser) {

        if (isEraser)
            photoEditor.brushEraser();
        else
            photoEditor.setBrushDrawingMode(true);

    }

    @Override
    public void onEmojiSelected(String emoji) {

        photoEditor.addEmoji(emoji);

    }

    @Override
    public void onAddTextButtonClick(Typeface typeface, String text, int color) {

        photoEditor.addText(typeface, text, color);

    }

    @Override
    public void onAddFrame(int frame) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), frame);
        photoEditor.addImage(bitmap);

    }

    @Override
    public void onBackPressed() {

        AlertDialogFragment alertDialogFragment = AlertDialogFragment.getInstance();
        alertDialogFragment.setmListener(MainActivity.this);
        alertDialogFragment.show(getSupportFragmentManager(), alertDialogFragment.getTag());


    }

    @Override
    public void onSaveAndExit() {
        saveImageInGallary();
        super.onBackPressed();
    }

    @Override
    public void onExitWithoutSaving() {
        super.onBackPressed();
    }

    @Override
    public void onCancelDialog(AlertDialogFragment instance) {
        instance.dismiss();
    }
}

