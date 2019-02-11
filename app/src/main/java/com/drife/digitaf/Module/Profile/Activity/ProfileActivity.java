package com.drife.digitaf.Module.Profile.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.CameraUtility.CameraUtility;
import com.drife.digitaf.GeneralUtility.ImageUtility.ImageUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.InputKredit.UploadDocument_v2.Activity.UploadDocumentActivity;
import com.drife.digitaf.Module.UbahPassword.UbahPasswordActivity;
import com.drife.digitaf.ORM.Database.Draft;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Profile.UbahProfileCallback;
import com.drife.digitaf.Service.Profile.UbahProfilePresenter;
import com.drife.digitaf.UIModel.UserSession;
import com.drife.digitaf.retrofitmodule.Model.Profile;
import com.drife.digitaf.retrofitmodule.SubmitParam.DocTcData;
import com.drife.digitaf.retrofitmodule.SubmitParam.ImageAttachment;
import com.nekoloop.base64image.Base64Image;
import com.nekoloop.base64image.RequestEncode;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements UbahProfileCallback {
    private String TAG = ProfileActivity.class.getSimpleName();
    public static final int REQUEST_UBAHPASSWORD = 1;

    @BindView(R.id.btnLogout)
    LinearLayout btnLogout;
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.imgEditImageProfile)
    ImageView imgEditImageProfile;
    @BindView(R.id.txtNIK)
    TextView txtNIK;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtPassword)
    TextView txtPassword;
    @BindView(R.id.toggleSidikJari)
    Switch toggleSidikJari;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.lyUbahPassword)
    LinearLayout lyUbahPassword;
    @BindView(R.id.txtDealerName)
    TextView txtDealerName;
    @BindView(R.id.txtTitleNik)
    TextView txtTitleNik;
    @BindView(R.id.txtTitleDealerName)
    TextView txtTitleDealerName;

    UserSession userSession;

    String imagePath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        ButterKnife.bind(this);
        initVariables();
        initListeners();
        callFunctions();
    }

    private void initVariables(){
        userSession = SharedPreferenceUtils.getUserSession(this);

        if (userSession.getUser().getImage() != null) {
            if (!userSession.getUser().getImage().equals("")) {
                loadImage(userSession.getUser().getImage());
            }
        }

        if (userSession.getUser().getType().equals("so")) {
            if (userSession.getUser().getResponseConfins() != null) {
                if (userSession.getUser().getResponseConfins().getEmployeeNo() != null) {
                    txtTitleNik.setText("NPK");
                    txtNIK.setText(userSession.getUser().getResponseConfins().getEmployeeNo());
                }

                if (userSession.getUser().getResponseConfins().getBranchName() != null) {
                    txtTitleDealerName.setText(getResources().getString(R.string.text_namabranch_setting));
                    txtDealerName.setText(userSession.getUser().getResponseConfins().getBranchName());
                }
            }
        } else {
            if (userSession.getUser().getKtp_no() != null) {
                txtNIK.setText(userSession.getUser().getKtp_no());
            }

            if (userSession.getUser().getResponseConfins() != null) {
                if (userSession.getUser().getResponseConfins().getDealerName() != null) {
                    txtTitleDealerName.setVisibility(View.VISIBLE);
                    txtDealerName.setVisibility(View.VISIBLE);
                    txtTitleDealerName.setText(getResources().getString(R.string.text_namadealer_setting));
                    txtDealerName.setText(userSession.getUser().getResponseConfins().getDealerName());
                }
            }
        }

        if (userSession.getUser().getProfile().getFullname() != null) {
            txtName.setText(userSession.getUser().getProfile().getFullname());
        }

        if (userSession.getUser().getEmail() != null) {
            txtEmail.setText(userSession.getUser().getEmail());
        }

        if (SharedPreferenceUtils.getFingerprint(this) != null) {
            if (SharedPreferenceUtils.getFingerprint(this).equals("1")) {
                toggleSidikJari.setClickable(false);
                toggleSidikJari.setChecked(true);
            } else {
                toggleSidikJari.setChecked(false);
            }
        }
    }

    private void initListeners(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this)
                        .setMessage("Data anda akan terhapus dan akan sync ulang semua data. Anda yakin tetap ingin logout?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Draft.deleteAll(Draft.class);
                                Intent intent = new Intent();
                                intent.putExtra("is_logout", true);
                                setResult(Activity.RESULT_OK, intent);
//                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        ;
                builder.show();

//                Draft.deleteAll(Draft.class);
//                Intent intent = new Intent();
//                intent.putExtra("is_logout", true);
//                setResult(Activity.RESULT_OK, intent);
////                setResult(Activity.RESULT_OK);
//                finish();
            }
        });

        lyUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, UbahPasswordActivity.class);
                startActivityForResult(intent, REQUEST_UBAHPASSWORD);;
            }
        });

        imgEditImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCameraOrGallery();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCameraOrGallery();
            }
        });

        toggleSidikJari.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleSidikJari.setClickable(false);
                    SharedPreferenceUtils.saveFingerprint(ProfileActivity.this, "1");
                }
            }
        });
    }

    private void callFunctions(){

    }

    @Override
    public void onSuccessUbahProfile(JSONObject object) {
        if (!object.optString("image").equals("")) {
            try {
                userSession.getUser().setImage(object.getString("image"));
                SharedPreferenceUtils.saveSession(ProfileActivity.this, userSession);
                loadImage(userSession.getUser().getImage());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFaileUbahProfile() {

    }

    private void loadImage(String image) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        imageLoader.loadImage(userSession.getUser().getImage(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imgProfile.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    private void openCameraOrGallery() {
        imagePath = "";

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Upload Gambar"/*+" (Maksimal file 500Kb)"*/)
                .setItems(new String[]{"Camera", "Gallery (Maksimal File Upload 500Kb)"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                File file = ImageUtility.createImageFile("profile","IMG_PROFILE");
                                imagePath = file.getAbsolutePath();
                                CameraUtility.openCamera(ProfileActivity.this, CameraUtility.REQUEST_UBAHPROFILE_CAMERA,file);

                                break;
                            case 1:
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, CameraUtility.REQUEST_UBAHPROFILE_GALLERY);
                                break;
                        }
                    }
                });
        alert.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CameraUtility.REQUEST_UBAHPROFILE_CAMERA) {
            if (resultCode == RESULT_OK) {
                Bitmap compressedBitmap = ImageUtility.resizeImage(ImageUtility.loadBitmapFromPath(imagePath));
                Base64Image.with(ProfileActivity.this)
                        .encode(compressedBitmap)
                        .into(new RequestEncode.Encode() {
                            @Override
                            public void onSuccess(String s) {
                                UbahProfilePresenter.gantiFotoProfil(ProfileActivity.this, "data:image/jpeg;base64,"+s, ProfileActivity.this);
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
            }
        } else if (requestCode == CameraUtility.REQUEST_UBAHPROFILE_GALLERY) {
            if (resultCode == RESULT_OK) {
                Bitmap compressedBitmap = ImageUtility.resizeImage(ImageUtility.loadBitmapFromPath(ImageUtility.getImageFromGallery(getApplicationContext(), data.getData()).getAbsolutePath()));
                Base64Image.with(ProfileActivity.this)
                        .encode(compressedBitmap)
                        .into(new RequestEncode.Encode() {
                            @Override
                            public void onSuccess(String s) {
                                UbahProfilePresenter.gantiFotoProfil(ProfileActivity.this, "data:image/jpeg;base64,"+s, ProfileActivity.this);
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
            }
        }
    }
}
