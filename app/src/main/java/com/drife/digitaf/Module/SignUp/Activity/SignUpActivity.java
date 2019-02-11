package com.drife.digitaf.Module.SignUp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.Spinner.SpinnerUtility;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.Module.Aktivasi.Activity.AktivasiActivity;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Dealer.DealerCallback;
import com.drife.digitaf.Service.Dealer.DealerPresenter;
import com.drife.digitaf.Service.Register.RegisterCallback;
import com.drife.digitaf.Service.Register.RegisterPresenter;
import com.drife.digitaf.retrofitmodule.Model.Dealer;
import com.drife.digitaf.retrofitmodule.Model.DealerSimple;
import com.drife.digitaf.retrofitmodule.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements DealerCallback,RegisterCallback{
    private String TAG = SignUpActivity.class.getSimpleName();

    @BindView(R.id.tvAktivasi)
    TextView tvAktivasi;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etNpk)
    EditText etNpk;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.spinDealer)
    AutoCompleteTextView spinDealer;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etRetypePassword)
    EditText etRetypePassword;
    @BindView(R.id.btDaftar)
    Button btDaftar;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.lyNpk)
    LinearLayout lyNpk;
    @BindView(R.id.tvNpk)
    TextView tvNpk;
    @BindView(R.id.iv_show_password)
    ImageView imgShowPass1;
    @BindView(R.id.iv_show_password_2)
    ImageView imgShowPass2;
    @BindView(R.id.layoutDealerName)
    LinearLayout layoutDealerName;

    private ProgressDialog progressDialog;
    private int REQUEST_AKTIVASI_USER = 1;

    private boolean isOfficer = true;
    private boolean showPassword1 = false;
    private boolean showPassword2 = false;
    private List<DealerSimple> dealers = new ArrayList<>();
    private List<String> dealerNames = new ArrayList<>();

    private DealerSimple selectedDealer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        initVariables();
        initListeners();
        callFunctions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_AKTIVASI_USER){
            if(resultCode==RESULT_OK){
                finish();
            }
        }
    }

    private void initVariables(){
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        etRetypePassword.setTransformationMethod(new PasswordTransformationMethod());
    }

    private void initListeners(){
//        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    String text = etEmail.getText().toString();
//                    if(TextUtility.isEmailFormat(text)){
//                        if(!validateDomainEmail(text)){
//                            tvNpk.setText("Nomor KTP");
//                            etNpk.setHint("Nomor KTP Anda");
//                            isOfficer = false;
//                        }else{
//                            tvNpk.setText("Nomor NPK");
//                            etNpk.setHint("Nomor NPK Anda");
//                            isOfficer = true;
//                        }
//                    }else{
//                        etEmail.setError("Email is not valid");
//                    }
//                }
//            }
//        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    String text = editable.toString();
                    if (TextUtility.isEmailFormat(text)) {
                        if(!validateDomainEmail(text)){
                            tvNpk.setText("Nomor KTP");
                            etNpk.setHint("Nomor KTP Anda");
                            etNpk.setInputType(InputType.TYPE_CLASS_NUMBER);
                            isOfficer = false;
                            selectedDealer = null;

                            layoutDealerName.setVisibility(View.VISIBLE);
                        } else {
                            tvNpk.setText("Nomor NPK");
                            etNpk.setHint("Nomor NPK Anda");
                            etNpk.setInputType(InputType.TYPE_CLASS_TEXT);
                            isOfficer = true;

                            layoutDealerName.setVisibility(View.GONE);
                        }
                    }else{
                        etEmail.setError("Email is not valid");
                    }
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAktivasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, AktivasiActivity.class);
                startActivityForResult(intent,REQUEST_AKTIVASI_USER);
            }
        });

        imgShowPass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPassword1) {
                    showPassword1 = false;
                    imgShowPass1.setImageResource(R.drawable.ic_hide);
                    etPassword.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    showPassword1 = true;
                    imgShowPass1.setImageResource(R.drawable.ic_show);
                    etPassword.setTransformationMethod(null);
                }

                if (etPassword.length() > 0) {
                    etPassword.setSelection(etPassword.length());
                }
            }
        });

        imgShowPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPassword2) {
                    showPassword2 = false;
                    imgShowPass2.setImageResource(R.drawable.ic_hide);
                    etRetypePassword.setTransformationMethod(new PasswordTransformationMethod());
                } else {
                    showPassword2 = true;
                    imgShowPass2.setImageResource(R.drawable.ic_show);
                    etRetypePassword.setTransformationMethod(null);
                }

                if (etRetypePassword.length() > 0) {
                    etRetypePassword.setSelection(etRetypePassword.length());
                }
            }
        });

        btDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String emailConfirmation = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String passwordConfirmation = etRetypePassword.getText().toString();
                String ktpNo = "";
                String npkNo = "";
                String officer = "";
                if (isOfficer) {
                    npkNo = etNpk.getText().toString();
                    officer = "1";
                } else {
                    ktpNo = etNpk.getText().toString();
                    officer = "0";
                }
                String fullname = etName.getText().toString();
//                String dealerId = dealers.get(spinDealer.getListSelection()).getId();
                String dealerId = selectedDealer != null ? selectedDealer.getId() : "";
                /*HashMap<String,String> param = new HashMap<>();
                param.put("email",email);
                param.put("email_confirmation",emailConfirmation);
                param.put("password",password);
                param.put("password_confirmation",passwordConfirmation);
                param.put("ktp_no",ktpNo);
                param.put("npk_no",npkNo);
                param.put("is_officer",officer);
                param.put("fullname",fullname);
                param.put("dealer_id",dealerId);

                Log.d("singo", "param : "+ JSONProcessor.toJSON(param));

                RegisterPresenter.register(param,SignUpActivity.this);*/
                if (!email.equals("") && ((!npkNo.equals("") && isOfficer) || (!ktpNo.equals("") && !isOfficer))
                        && !fullname.equals("") && ((selectedDealer != null && !isOfficer) || (selectedDealer == null && isOfficer))
                        && checkPassword(password, etPassword).equals("") && checkPassword(passwordConfirmation, etRetypePassword).equals("")
                        && password.equals(passwordConfirmation)) {

                    progressDialog = DialogUtility.createProgressDialog(SignUpActivity.this,"Register user","Please wait...");
                    progressDialog.show();

                    HashMap<String,String> param = new HashMap<>();
                    param.put("email",email);
                    param.put("email_confirmation",emailConfirmation);
                    param.put("password",password);
                    param.put("password_confirmation",passwordConfirmation);
                    param.put("ktp_no",ktpNo);
                    param.put("npk_no",npkNo);
                    param.put("is_officer",officer);
                    param.put("fullname",fullname);
                    param.put("dealer_id",dealerId);

                    Log.d("singo","param : "+JSONProcessor.toJSON(param));

                    RegisterPresenter.register(param,SignUpActivity.this);
                } else if (email.equals("")) {
                    Toast.makeText(SignUpActivity.this, R.string.text_email_tidak_boleh_kosong, Toast.LENGTH_LONG).show();
                } else if (npkNo.equals("") && isOfficer) {
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.text_npkno_tidak_boleh_kosong), Toast.LENGTH_LONG).show();
                } else if (ktpNo.equals("") && !isOfficer) {
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.text_ktpno_tidak_boleh_kosong), Toast.LENGTH_LONG).show();
                } else if (fullname.equals("")) {
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.text_fullname_tidak_boleh_kosong), Toast.LENGTH_LONG).show();
                } else if (selectedDealer == null && !isOfficer) {
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.text_dealername_tidak_boleh_kosong), Toast.LENGTH_LONG).show();
                } else if (!checkPassword(password, etPassword).equals("")) {
                    Toast.makeText(SignUpActivity.this, checkPassword(password, etPassword), Toast.LENGTH_LONG).show();
                } else if (!checkPassword(passwordConfirmation, etRetypePassword).equals("")) {
                    Toast.makeText(SignUpActivity.this, checkPassword(passwordConfirmation, etRetypePassword), Toast.LENGTH_LONG).show();
                } else if (!password.equals(passwordConfirmation)) {
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.text_password_tidak_sama), Toast.LENGTH_LONG).show();
                }
            }
        });

        spinDealer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDealer = (DealerSimple) spinDealer.getAdapter().getItem(i);
            }
        });

        spinDealer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i1 == 1) {
                   selectedDealer = null;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    spinDealer.showDropDown();
                }
            }
        });

        spinDealer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    spinDealer.showDropDown();
                }
            }
        });
    }

    private void callFunctions(){
        getDealerList();
    }

    private void getDealerList(){
        progressDialog = DialogUtility.createProgressDialog(SignUpActivity.this,"Get Dealer List","Please wait...");
        progressDialog.show();
        DealerPresenter.getDealerListString();
        DealerPresenter.getDealerList(SignUpActivity.this);
    }

    private boolean validateDomainEmail(String text){
        String[] domain = text.split("@");
        if(domain[1].equalsIgnoreCase("taf.co.id")){
            return true;
        }else{
            return false;
        }
    }

    private String checkPassword(String password, EditText editText) {
        String msg = "";
        String title = "Password";

        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean isAtLeast8   = password.length() >= 8;
        boolean hasSpecial   = !password.matches("[A-Za-z0-9]*");
        boolean isNotEmpty   = !password.equals("");

        if (editText == etPassword) {
            title = "Password";
        } else {
            title = "Password Konfirmasi";
        }

        if (!isNotEmpty) {
            msg = title + " tidak boleh kosong.";
        } else if (!isAtLeast8) {
            msg = "Panjang "+ title +" kurang dari 8 karakter.";
        } else if (!hasLowercase) {
            msg = title + " harus mengandung 1 huruf kecil.";
        } else if (!hasUppercase) {
            msg = title + " harus mengandung 1 huruf besar.";
        } else if (!hasSpecial) {
            msg = title + " harus mengandung kombinasi huruf, angka & special karakter.";
        } else {
            msg = "";
        }

        return msg;
    }

    @Override
    public void onSuccessGetDealer(List<DealerSimple> dealer) {
        progressDialog.dismiss();

        dealerNames.add("Pilih dealer anda");

        this.dealers = dealer;
        if(dealer != null){
            for (int i=0; i<dealers.size(); i++){
                String dealerName = dealers.get(i).getName();
                dealerNames.add(dealerName);
            }
//            SpinnerUtility.setSpinnerItem(getApplicationContext(),spinDealer,dealerNames);
            spinDealer.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, dealers));
        }
    }

    @Override
    public void onFailedGetDealer(String message) {
        progressDialog.dismiss();
    }

    @Override
    public void onSuccessRegister(User user) {
        Toast.makeText(this, "Akun Anda berhasil di daftarkan. Silahkan masukkan kode OTP.", Toast.LENGTH_LONG).show();

        progressDialog.dismiss();
        Intent intent = new Intent(SignUpActivity.this, AktivasiActivity.class);
        String otp_code = user.getOtp_code();
        intent.putExtra("otp",otp_code);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailedRegister(String message) {
        progressDialog.dismiss();

        AlertDialog.Builder builder = DialogUtility.createSingleOptionAlertDialog(SignUpActivity.this, "Failed register", message, new DialogUtility.AlertDialogCallback() {
            @Override
            public void onPositive() {

            }

            @Override
            public void onNegative() {

            }
        },"Ok");
        builder.show();
    }
}
