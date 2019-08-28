package com.example.list_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.list_view.model.Contact;
import com.example.list_view.presenter.Presenter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Interactor.View{


    private DatabaseHandler db;
    private ContactAdapter contactAdapter;
    private Contact dataModel;
    private Bitmap bp;
    private Interactor.Presenter mPresenter;

    private EditText fname;
    private ImageView pic;
    private  ListView lv;

    private final int CAMERA_REQUEST_CODE_FOR_INTENT = 100;
    private String f_name;
    private byte[] photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.list1);
        pic= (ImageView) findViewById(R.id.pic);
        fname=(EditText) findViewById(R.id.txt1);

        db = new DatabaseHandler(this);
        mPresenter = new Presenter();
    }

    public void buttonClicked(View v){
        int id=v.getId();

        switch(id){

            case R.id.save:
                if(fname.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Name edit text is empty, Enter name", Toast.LENGTH_LONG).show();
                } else {
                    Contact contact = new Contact();
                    contact.setImageName(fname.getText().toString());
                    mPresenter.addContact(db, contact);
                }
                break;

            case R.id.display:
                mPresenter.loadExistingData(db);
                break;

            case R.id.pic:
                selectImage();
                break;
        }
    }

    public void selectImage(){
        /*Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);*/
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= 24) {
            // set flag to give temporary permission to external app to use your FileProvider
            cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                    FileProvider.getUriForFile(MainActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            new File(Environment.getExternalStorageDirectory() + "/" + getImageName())));

            // validate that the device can open your File!
            PackageManager pm = this.getPackageManager();
            if (cameraIntent.resolveActivity(pm) != null) {
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE_FOR_INTENT);
            }
        } else {
            cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/" + getImageName())));
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE_FOR_INTENT);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if(requestCode == CAMERA_REQUEST_CODE_FOR_INTENT && resultCode == RESULT_OK  ){
                    Uri choosenImage = data.getData();
                    choosenImage.getPath();
                    /*if(choosenImage !=null){

                        bp=decodeUri(choosenImage, 400);
                        pic.setImageBitmap(bp);
                    }*/
                }
    }


    //COnvert and resize our image to 400dp for faster uploading our images to DB
    /*protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }*/

    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }





    //Insert contactAdapter to the database
    /*private void addContact(){
        getValues();

        db.addContacts();

    }*/

    // function to get values from the Edittext and image
    private void getValues(){
        f_name = fname.getText().toString();
        photo = profileImage(bp);
    }


    //Retrieve contactAdapter from the database and set to the list view
    /*private void ShowRecords(){
        final ArrayList<Contact> contacts = new ArrayList<>(db.getAllContacts());
        contactAdapter=new ContactAdapter(this, contacts);

        lv.setAdapter((ListAdapter) contactAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dataModel = contacts.get(position);

                Toast.makeText(getApplicationContext(),String.valueOf(dataModel.getID()), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private String getImageName() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyHHmmss", Locale.ENGLISH);
        String currentDateandTime = sdf.format(cal.getTime());
        return "IMG_" + currentDateandTime;
    }

    @Override
    public void loadData(final ArrayList<Contact> contacts) {
        ContactAdapter contactAdapter = new ContactAdapter(this, contacts);

        lv.setAdapter((ListAdapter) contactAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dataModel = contacts.get(position);
                Toast.makeText(getApplicationContext(),String.valueOf(dataModel.getImageID()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showRecords(ArrayList<Contact> contacts) {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(MainActivity.this,message, Toast.LENGTH_LONG).show();
    }
}


