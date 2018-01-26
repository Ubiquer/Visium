package com.example.arek.visium.screens.image_selection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arek.visium.R;
import com.example.arek.visium.model.Category;
import com.example.arek.visium.screens.image_selection.image_carousel.ImageCarouselAdapter;
import com.example.arek.visium.screens.image_selection.image_carousel.ImageCarouselPresenterImpl;
import com.example.arek.visium.screens.image_selection.image_carousel.ImageCarouselView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class ImageSelectionActivity extends AppCompatActivity implements ImageCarouselView, ImageSelectionView {

    private static final int MY_PERMISSIONS_REQUEST = 100;
    private static final int REQUEST_CODE = 6384;
    private static int RESULT_LOAD_IMAGE = 1;
    private String TAG = "TestActivity";
    private Uri selectedImage;
    private String spinnerCategory, imageUri;

    @BindView(R.id.category_spinner)
    Spinner categorySpinner;
    @BindView(R.id.test_button)
    Button testButton;
    @BindView(R.id.test_image)
    ImageView testImage;
    @BindView(R.id.upload_button) Button uploadButton;

    ProgressDialog progressDialog;
    private Realm realm;
    private SpinnerAdapter spinnerAdapter;

    @BindView(R.id.carousel_recycler_view)
    RecyclerView mRecyclerView;
    private ImageCarouselAdapter adapter;
    private Boolean isSDPresent;
    private LinearLayoutManager linearLayoutManager;
    private ImageCarouselPresenterImpl imageCarouselPresenterImpl;
    private ImageSelectionPresenterImpl imageSelectionPresenterImpl;
    private ArrayList<String> imagePathsList;
    private Bitmap bitmap;

    @Override
    protected void onStart() {
        super.onStart();
        requestPermissions();
    }

    @Override
    protected void onStop() {
        imageCarouselPresenterImpl.onDetach();
        imageSelectionPresenterImpl.onDetach();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        ButterKnife.bind(this);

        imageSelectionPresenterImpl = new ImageSelectionPresenterImpl(this);
        imageCarouselPresenterImpl = new ImageCarouselPresenterImpl();
        testButton.setOnClickListener(v -> loadImage());
//        progressDialog.dismiss();
        uploadButton.setOnClickListener(v -> imageSelectionPresenterImpl.uploadImage(imageUri, spinnerCategory));
    }

    private void initRecyclerView(){

        SnapHelper snapHelper = new LinearSnapHelper();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        snapHelper.attachToRecyclerView(mRecyclerView);
        adapter = new ImageCarouselAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(linearLayoutManager);
                int pos = mRecyclerView.getChildAdapterPosition(view);
                int firstItemVisible = linearLayoutManager.findFirstVisibleItemPosition();

                if (firstItemVisible != 0 && firstItemVisible % imageCarouselPresenterImpl.getImageListSize() == 0){
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                    mRecyclerView.getLayoutManager().scrollToPosition(0);
                }

                showEnlargedSnappedImage(pos);
            }
        });
    }

    private void showEnlargedSnappedImage(int pos) {
        pos = pos % imagePathsList.size();
        imageUri = imagePathsList.get(pos);
        bitmap = BitmapFactory.decodeFile(imageUri);
        ImageView imageView = (ImageView) findViewById(R.id.test_image);
        imageView.setImageBitmap(bitmap);

    }

    private void loadImage(){

        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private void setUpSpinner(){
        spinnerAdapter = new SpinnerAdapter(this, R.layout.item_spinner_dropdown, imageSelectionPresenterImpl.getCategoriesFromRealm());
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category = spinnerAdapter.getItem(position);
                spinnerCategory = category.getCategoryName();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerAdapter = null;
            }
        });
    }

    private void setButtonActive(){
        if (selectedImage == null){
            uploadButton.setEnabled(false);
        }else {
            uploadButton.setEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            attachPresenters();
            setUpSpinner();
        }else{
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){

            selectedImage = data.getData();
            setButtonActive();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
        }
    }

    @Override
    public void showImages(ArrayList<String> imagePathsList) {
        this.imagePathsList = imagePathsList;
        initRecyclerView();
        adapter.setData(imagePathsList);
    }


    @Override
    public void onSuccessfulUpload(String message) {
        Toast.makeText(this, R.string.imageUploadedInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadError(String message) {
        Toast.makeText(this, getString(R.string.imageErrorInfo1) + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void insufficientData(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void requestPermissions(){

        if(ContextCompat.checkSelfPermission(ImageSelectionActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(ImageSelectionActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);
        }else {
            attachPresenters();
            setUpSpinner();
        }
    }

    private void attachPresenters(){

        imageCarouselPresenterImpl.onAttach(this);
        imageSelectionPresenterImpl.onAttach(this);

    }

}
