package com.example.arek.visium;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.FileChooserActivity;
import com.ipaulpro.afilechooser.utils.FileUtils;

import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;
import com.example.arek.visium.rest.ServiceGenerator;

//import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.jar.Manifest;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 100;
    private static final int REQUEST_CODE = 6384;
    private String TAG = "TestActivity";

@BindView(R.id.test_button)
    Button testButton;
@BindView(R.id.test_image)
    ImageView testImage;

    ProgressDialog progressDialog;
    private String mediaPath;
    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        if(ContextCompat.checkSelfPermission(TestActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(TestActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);

        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        mApiInterface = ApiAdapter.getAPIService();
        testImage.setImageResource(R.drawable.midget);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooser();
            }
        });
        progressDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode) {

            case REQUEST_CODE:
                if (resultCode == RESULT_OK){

                    if(data!=null){

                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());

                        try {
                            final String path = FileUtils.getPath(this, uri);
                            Toast.makeText(TestActivity.this, "File selected: " + path, Toast.LENGTH_LONG).show();
                            uploadFile(uri);

                        }catch (Exception e){
                            Log.e("Error ", "File error", e);
                        }
                    }
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showChooser(){

        Intent target = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(target, getString(R.string.choose_file));
        try{
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e){

        }
    }

    private void uploadFile(Uri fileUri) {
        progressDialog.show();

        File originalFile = FileUtils.getFile(this, fileUri);
        RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, "Midgets");

        RequestBody filePart = RequestBody.create(
                MediaType.parse(getContentResolver().getType(fileUri)),
                originalFile);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("photo", originalFile.getName(), filePart);

        mApiInterface.uploadImage(descriptionPart, fileToUpload).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Toast.makeText(TestActivity.this, "Correct: " + call.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(TestActivity.this, "Incorrect login", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });



//        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename);
//        call.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                ServerResponse serverResponse = response.body();
//                if (serverResponse != null) {
//                    if (serverResponse.getSuccess()) {
//                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    assert serverResponse != null;
//                    Log.v("Response", serverResponse.toString());
//                }
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//
//            }
//        });
    }

}
