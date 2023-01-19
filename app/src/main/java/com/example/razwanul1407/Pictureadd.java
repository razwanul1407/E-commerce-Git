package com.example.razwanul1407;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Pictureadd extends AppCompatActivity {


    Uri filepath;
    CircleImageView img;
    Button browse,sign,btn123;
    Bitmap bitmap;
    private FirebaseUser UProfile;
    private DatabaseReference Ureference;
    private String UUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictureadd);

        sign=(Button)findViewById(R.id.upload);
        browse=(Button)findViewById(R.id.browse);
        btn123=(Button)findViewById(R.id.button1234);


        img=(CircleImageView) findViewById(R.id.imgpic);
        UProfile = FirebaseAuth.getInstance().getCurrentUser();
        Ureference = FirebaseDatabase.getInstance().getReference("Users");
        UUserID = UProfile.getUid();


        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(Pictureadd.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadtofirebase();

            }
        });

        btn123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Pictureadd.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void uploadtofirebase() {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();



        img=(CircleImageView) findViewById(R.id.imgpic);


        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference upoader = storage.getReference("Imag1"+new Random().nextInt(50000000));

        upoader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        upoader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                dialog.dismiss();
                                FirebaseDatabase db=FirebaseDatabase.getInstance();
                                DatabaseReference root=db.getReference("UUU");

                                dataholder obj = new dataholder(uri.toString());
                                root.child(UUserID).setValue(obj);
                               // img.setImageResource(R.drawable.ic_launcher_background);
                                Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        float percent=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded : "+(int)percent+" %");
                    }
                });

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                filepath=data.getData();
                try {
                    InputStream inputStream=getContentResolver().openInputStream(filepath);
                    bitmap= BitmapFactory.decodeStream(inputStream);
                    img.setImageBitmap(bitmap);
                }
                catch (Exception ex)
                {

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}


class dataholder
{
    String pimage;

    public dataholder() {
    }

    public dataholder(String pimage) {

        this.pimage = pimage;
    }
    public String getPimage() {
        return pimage;
    }
    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}
