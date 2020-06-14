package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.finalproject.interface_api.CSPreferences;
import com.example.finalproject.interface_api.WebApicall;

import java.io.ByteArrayOutputStream;
import java.io.File;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Edit_item extends AppCompatActivity {


    ImageView back,take_image,iamgevirew;
    RadioGroup group;
    RadioButton elect,book;

    Button buttonsubmit;
    EditText tital,no_of_item,discraption;
    File file;

    private final int requestCode = 20;
    private static final int CAMERA_REQUEST = 1888;

    private Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tital=findViewById(R.id.tital);
        no_of_item=findViewById(R.id.no_of_item);
        discraption=findViewById(R.id.discraption);
        take_image=findViewById(R.id.take_image);
        iamgevirew=findViewById(R.id.iamgevirew);
        final Bundle extras = getIntent().getExtras();

        tital.setText(extras.getString("tital"));
        no_of_item.setText(""+extras.getInt("total_item"));
        discraption.setText(extras.getString("discraption"));
        Glide.with(this)
                .load(extras.getString("image"))
                .fitCenter()
                .placeholder(R.drawable.logo)
                .into(iamgevirew);

        if (1 == extras.getInt("item_type")){
            elect=findViewById(R.id.elect);
            elect.setChecked(true);
        }else {
            book=findViewById(R.id.book);
            book.setChecked(true);

        }

        group=findViewById(R.id.group);
        buttonsubmit=findViewById(R.id.buttonsubmit);
        take_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpicture(this);
            }
        });

        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId=group.getCheckedRadioButtonId();
                elect=findViewById(selectedId);

                if (elect.getText().toString().length() == 0){
                    GlobalClass.showtost(Edit_item.this,"Please select your opation Electronics & Book");
                }else if (tital.getText().toString().length() == 0){
                    GlobalClass.showtost(Edit_item.this,"Please Enter your Tital");

                }else if (no_of_item.getText().toString().length() == 0){
                    GlobalClass.showtost(Edit_item.this,"Please Enter Your No of ");
                }else if (discraption.getText().toString().length() == 0){
                    GlobalClass.showtost(Edit_item.this,"Please Enter Your Descraption ");
                }else{
                    if (elect.getText().toString().equals("Electronics")){

                        RequestBody session_id = RequestBody.create(MediaType.parse("multipart/form-data"), CSPreferences.readString(Edit_item.this,"sessioniid"));
                        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"),tital.getText().toString().trim());
                        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), discraption.getText().toString().trim());
                        RequestBody no_of_items = RequestBody.create(MediaType.parse("multipart/form-data"), no_of_item.getText().toString().trim());
                        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"),"1");
                        RequestBody item_id = RequestBody.create(MediaType.parse("multipart/form-data"),""+extras.getInt("itemid"));
                        RequestBody item_type = RequestBody.create(MediaType.parse("multipart/form-data"),""+extras.getInt("item_type"));




                        RequestBody imagerequestFile;
                        MultipartBody.Part imagebody;


                        if (file == null){
                            WebApicall webApicall = new WebApicall();
                            webApicall.update_del_items(Edit_item.this,session_id,name,description,no_of_items, type,item_id,item_type,null);

                        }else {
                            imagerequestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            imagebody = MultipartBody.Part.createFormData("image",file.getName(),imagerequestFile);
                            WebApicall webApicall = new WebApicall();
                            webApicall.update_del_items(Edit_item.this,session_id,name,description,no_of_items, type,item_id,item_type,imagebody);
                        }
                    }else {
                        RequestBody session_id = RequestBody.create(MediaType.parse("multipart/form-data"),CSPreferences.readString(Edit_item.this,"sessioniid"));
                        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"),tital.getText().toString().trim());
                        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), discraption.getText().toString().trim());
                        RequestBody no_of_items = RequestBody.create(MediaType.parse("multipart/form-data"), no_of_item.getText().toString().trim());
                        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"),"1");
                        RequestBody item_id = RequestBody.create(MediaType.parse("multipart/form-data"),extras.getString("itemid"));
                        RequestBody item_type = RequestBody.create(MediaType.parse("multipart/form-data"),extras.getString("item_type"));

                        RequestBody imagerequestFile;
                        MultipartBody.Part imagebody;
                        if (file.toString().equals("null")){
                            WebApicall webApicall = new WebApicall();
                            webApicall.update_del_items(Edit_item.this,session_id,name,description,no_of_items, type,item_id,item_type,null);

                        }else {
                            imagerequestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            imagebody = MultipartBody.Part.createFormData("image",file.getName(),imagerequestFile);
                            WebApicall webApicall = new WebApicall();
                            webApicall.update_del_items(Edit_item.this,session_id,name,description,no_of_items, type,item_id,item_type,imagebody);
                        }
                    }


                }
            }
        });
    }

    public void showpicture(View.OnClickListener activity) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.txt_slct_option));
        String[] items = {getResources().getString(R.string.txt_gellery),
                getResources().getString(R.string.txt_cameray), getResources()
                .getString(R.string.txt_warning)};
        // dialog.setMessage("*for your security reason we blocked!");
        dialog.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                switch (which) {
                    case 0:
                        showFileChooser();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                }
            }
        });
        dialog.show();
    }


    private void takePhotoFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }
    private void showFileChooser() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,

                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent

        startActivityForResult(galleryIntent, requestCode);

    }

    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    public String getRealPathFromURI(Uri uri) {
        Cursor cursor =getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode && resultCode == RESULT_OK) {
            Uri picUri = data.getData();
            file = new File(getPath(picUri));
            Log.d("sfnsklfnlsnfl", String.valueOf(file));
            iamgevirew.setImageURI(picUri);

        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            iamgevirew.setImageBitmap(photo);
            Uri tempUri = getImageUri(this, photo);
            file = new File(getRealPathFromURI(tempUri));
            Log.d("sfnsklfnlsnfl", String.valueOf(file));

        }
    }
}
