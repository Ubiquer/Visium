package com.example.arek.visium.image_selection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arek.visium.R;
import com.example.arek.visium.TestActivity;
import com.example.arek.visium.realm.Token;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.mindorks.placeholderview.SmoothLinearLayoutManager;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

import javax.annotation.RegEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageSelectionActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 100;
    private static final int REQUEST_CODE = 6384;
    private static int RESULT_LOAD_IMAGE = 1;
    private String TAG = "TestActivity";
    String path;
    Uri selectedImage;
    //    Realm realm;
    private String spinnerCategory;

    @BindView(R.id.category_spinner)
    Spinner categorySpinner;
    @BindView(R.id.test_button)
    Button testButton;
    @BindView(R.id.test_image)
    ImageView testImage;
    @BindView(R.id.upload_button) Button uploadButton;

    ProgressDialog progressDialog;
    private ApiInterface mApiInterface;
    ArrayAdapter<CharSequence> spinnerAdapter;
    Realm realm;
    private String mToken;

    @BindView(R.id.carousel_recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        getAccessToken();

        if(ContextCompat.checkSelfPermission(ImageSelectionActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(ImageSelectionActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        mApiInterface = ApiAdapter.getAPIService();
        testButton.setOnClickListener(v -> loadImage());

        progressDialog.dismiss();
        setUpSpinner();
        uploadButton.setOnClickListener(v -> uploadFile(selectedImage));
    }

    private void initRecyclerView(){

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new SmoothLinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ImageCarouselAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }

    private void loadImage(){
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private void setUpSpinner(){

        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCategory = categorySpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerAdapter = null;
            }
        });
    }

//    private void getFromRealm(){
//
//        ArrayList<String> spinnerList;
//        RealmResults<UserPreferencesCategories> = realm.where(UserPreferencesCategories.class).findAll();
//        for (UserPreferencesCategories listOfCategories : spinnerList)
//    }

    private void getAccessToken(){
        realm.beginTransaction();
        Token token = realm.where(Token.class).findFirst();
        mToken = token.getM_token();
        realm.commitTransaction();
    }

    private void setButtonActive(){
        if (selectedImage == null){
            uploadButton.setEnabled(false);
        }else if (selectedImage!=null){
            uploadButton.setEnabled(true);
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

            ImageView imageView = (ImageView) findViewById(R.id.test_image);
            Bitmap bmp = null;
            try{
                bmp = getBitmapFromUri(selectedImage);
            }catch (IOException e){
                e.printStackTrace();
            }
            imageView.setImageBitmap(bmp);
        }

    }

    //    private void requestPermission(){
//
//        if(ContextCompat.checkSelfPermission(TestActivity.this,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED){
//
//            ActivityCompat.requestPermissions(TestActivity.this,
//                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
//                    MY_PERMISSIONS_REQUEST);
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        switch(requestCode) {
//            case REQUEST_CODE:
//                if (resultCode == RESULT_OK){
//                    if(data!=null){
//
//                        uri = data.getData();
//                        Log.i(TAG, "Uri = " + uri.toString());
//                        try {
//                            path = FileUtils.getPath(this, uri);
//                            Toast.makeText(TestActivity.this, "File selected: " + path, Toast.LENGTH_LONG).show();
////                            uploadFile(uri);
//                        }catch (Exception e){
//                            Log.e("Error ", "File error", e);
//                        }
//                    }
//                }
//                break;
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void showChooser(){
//
//        Intent target = FileUtils.createGetContentIntent();
//        Intent intent = Intent.createChooser(target, getString(R.string.choose_file));
//
//        try{
//            startActivityForResult(intent, REQUEST_CODE);
//        } catch (ActivityNotFoundException e){
//
//        }
//    }

    //    @OnClick(R.id.upload_button)
    public void uploadFile(Uri fileUri) {
        progressDialog.show();
        File originalFile = FileUtils.getFile(this, fileUri);
        RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, spinnerCategory);
        RequestBody filePart = RequestBody.create(
                MediaType.parse(getContentResolver().getType(fileUri)),
                originalFile);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("photo", originalFile.getName(), filePart);

        mApiInterface.uploadImage(mToken, spinnerCategory, fileToUpload).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                response.message();
                Toast.makeText(ImageSelectionActivity.this, "Correct: " + call.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ImageSelectionActivity.this, "Incorrect login", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}
