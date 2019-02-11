package com.drife.digitaf.Module.UbahPassword;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.ChangePassword.ChangePasswordCallback;
import com.drife.digitaf.Service.ChangePassword.ChangePasswordPresenter;
import com.drife.digitaf.Service.Login.LoginPresenter;
import com.drife.digitaf.Service.Login.LupaPasswordCallback;
import com.drife.digitaf.UIModel.UserSession;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UbahPasswordActivity extends AppCompatActivity {
    private String TAG = UbahPasswordActivity.class.getSimpleName();

    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.etPasswordLama)
    EditText etPasswordLama;
    @BindView(R.id.etPasswordBaru)
    EditText etPasswordBaru;
    @BindView(R.id.etKonfirmasiPasswordBaru)
    EditText etKonfirmasiPasswordBaru;
    @BindView(R.id.btnSaveNewPassword)
    Button btnSaveNewPassword;
    @BindView(R.id.iv_show_password_1)
    ImageView imgShowPass1;
    @BindView(R.id.iv_show_password_2)
    ImageView imgShowPass2;
    @BindView(R.id.iv_show_password_3)
    ImageView imgShowPass3;

    UserSession userSession;

    private boolean showPassword1 = false;
    private boolean showPassword2 = false;
    private boolean showPassword3 = false;

    public ProgressDialog progressDialog;
    private boolean isPeriodically = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubahpassword);
        ButterKnife.bind(this);
        initVariables();
        initListeners();
        callFunctions();
    }

    private void initVariables(){
        etPasswordLama.setTransformationMethod(new PasswordTransformationMethod());
        etPasswordBaru.setTransformationMethod(new PasswordTransformationMethod());
        etKonfirmasiPasswordBaru.setTransformationMethod(new PasswordTransformationMethod());
        isPeriodically = getIntent().getBooleanExtra("isPeriodically",false);
    }

    private void initListeners(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSaveNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordLama = etPasswordLama.getText().toString();
                String passwordBaru = etPasswordBaru.getText().toString();
                String konfirmasiPasswordBaru = etKonfirmasiPasswordBaru.getText().toString();

                if (TextUtility.checkPassword(passwordLama, etPasswordLama, "Password Lama").equals("")
                        && TextUtility.checkPassword(passwordBaru, etPasswordBaru, "Password Baru").equals("")
                        && TextUtility.checkPassword(konfirmasiPasswordBaru, etKonfirmasiPasswordBaru, "Konfirmasi Password Baru").equals("")
                        && !passwordLama.equals(passwordBaru) && passwordBaru.equals(konfirmasiPasswordBaru)) {

                    progressDialog = new ProgressDialog(UbahPasswordActivity.this);
                    progressDialog.setMessage("Mengubah password...");
                    progressDialog.show();

                    ChangePasswordPresenter.gantiPassword(UbahPasswordActivity.this, passwordLama, passwordBaru, konfirmasiPasswordBaru, new ChangePasswordCallback() {
                        @Override
                        public void onSuccessChangePassword() {
                            progressDialog.dismiss();

                            if(isPeriodically){
                                AlertDialog alertDialog = new AlertDialog.Builder(UbahPasswordActivity.this).setTitle("Change Password")
                                        .setTitle("Sukses ganti password")
                                        .setMessage("Silahkan login menggunakan password yang baru")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                setResult(Activity.RESULT_OK);
                                                finish();
                                            }
                                        }).create();
                                alertDialog.show();
                            }else{
                                AlertDialog alertDialog = new AlertDialog.Builder(UbahPasswordActivity.this).setTitle("Change Password")
                                        .setMessage("Successfully change password.")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                            }
                                        }).create();
                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onFaileChangePassword() {
                            progressDialog.dismiss();

                            AlertDialog alertDialog = new AlertDialog.Builder(UbahPasswordActivity.this).setTitle("Change Password")
                                    .setMessage("Gagal update password")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).create();
                            alertDialog.show();
                        }
                    });

                } else if (!TextUtility.checkPassword(passwordLama, etPasswordLama, "Password Lama").equals("")) {
                    Toast.makeText(UbahPasswordActivity.this, TextUtility.checkPassword(passwordLama, etPasswordLama, "Password Lama"), Toast.LENGTH_LONG).show();
                } else if (!TextUtility.checkPassword(passwordBaru, etPasswordBaru, "Password Baru").equals("")) {
                    Toast.makeText(UbahPasswordActivity.this, TextUtility.checkPassword(passwordBaru, etPasswordBaru, "Password Baru"), Toast.LENGTH_LONG).show();
                } else if (!TextUtility.checkPassword(konfirmasiPasswordBaru, etKonfirmasiPasswordBaru, "Konfirmasi Password Baru").equals("")) {
                    Toast.makeText(UbahPasswordActivity.this, TextUtility.checkPassword(konfirmasiPasswordBaru, etKonfirmasiPasswordBaru, "Konfirmasi Password Baru"), Toast.LENGTH_LONG).show();
                } else if (passwordLama.equals(passwordBaru)) {
                    Toast.makeText(UbahPasswordActivity.this, getResources().getString(R.string.pass_lamadanbarutidakbolehsama), Toast.LENGTH_LONG).show();
                } else if (!passwordBaru.equals(konfirmasiPasswordBaru)) {
                    Toast.makeText(UbahPasswordActivity.this, getResources().getString(R.string.pass_barudankonfirmasitidaksama), Toast.LENGTH_LONG).show();
                }
            }
        });

        imgShowPass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPassword1) {
                    showPassword1 = false;
                    imgShowPass1.setImageResource(R.drawable.ic_hide);
                    etPasswordLama.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    showPassword1 = true;
                    imgShowPass1.setImageResource(R.drawable.ic_show);
                    etPasswordLama.setTransformationMethod(null);
                }

                if (etPasswordLama.length() > 0) {
                    etPasswordLama.setSelection(etPasswordLama.length());
                }
            }
        });

        imgShowPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPassword2) {
                    showPassword2 = false;
                    imgShowPass2.setImageResource(R.drawable.ic_hide);
                    etPasswordBaru.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    showPassword2 = true;
                    imgShowPass2.setImageResource(R.drawable.ic_show);
                    etPasswordBaru.setTransformationMethod(null);
                }

                if (etPasswordBaru.length() > 0) {
                    etPasswordBaru.setSelection(etPasswordBaru.length());
                }
            }
        });

        imgShowPass3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPassword3) {
                    showPassword3 = false;
                    imgShowPass3.setImageResource(R.drawable.ic_hide);
                    etKonfirmasiPasswordBaru.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    showPassword3 = true;
                    imgShowPass3.setImageResource(R.drawable.ic_show);
                    etKonfirmasiPasswordBaru.setTransformationMethod(null);
                }

                if (etKonfirmasiPasswordBaru.length() > 0) {
                    etKonfirmasiPasswordBaru.setSelection(etKonfirmasiPasswordBaru.length());
                }
            }
        });
    }

    private void callFunctions(){

    }
}
