package com.xcrino.moro.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.BankData;
import com.xcrino.moro.PojoModels.BankDetailsResponse;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.Utilities.Validations;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CashWithdrawalActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private AppPreferencesShared appPreferencesShared;
    private EditText account_holderName, bank_Name, account_Number, ifsc_Code, amountFloat, branch_Address;
    private ImageView back_arrow;
    private Button submit_Transaction;
    private CardView add_NewAccount, add_new_TransferAccount;
    private TextView toolbar_title, not_transactions_list, not_withdrawal_list;

    private RecyclerView recycler_view, withdrawal_recycler_view;
    private LinearLayout accounts_added_list, withdrawal_history_list;
    private BankData bankData;
    private Validations validations;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferencesShared = new AppPreferencesShared(this);
        Locale myLocale = new Locale(appPreferencesShared.getLocale());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        if (appPreferencesShared.getmDayNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_cash_withdrawal);

        setLayoutUiInit();

        mContext = this;
        toolbar_title.setText("Cash Withdrawal");
        validations = new Validations();

        back_arrow.setOnClickListener(this);
        submit_Transaction.setOnClickListener(this);
        add_new_TransferAccount.setOnClickListener(this);
        add_NewAccount.setOnClickListener(this);

        Gson gSonUserProfile = new Gson();
        String jsonUserProfile = appPreferencesShared.getUserDetails();
        userProfile = gSonUserProfile.fromJson(jsonUserProfile, UserProfile.class);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getBankDetailsHistory();
        } else {
            Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getBankDetailsHistory() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<BankDetailsResponse> bankDetailsResponseCall = apiInterface.getBankDetails("Mogo_api/get_bank_details/" +
                appPreferencesShared.getUserId());
        bankDetailsResponseCall.enqueue(new Callback<BankDetailsResponse>() {
            @Override
            public void onResponse(Call<BankDetailsResponse> call, Response<BankDetailsResponse> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.body() != null) {
                            bankData = response.body().getBankData();
                            account_holderName.setText(response.body().getBankData().getUbdName());
                            bank_Name.setText(response.body().getBankData().getUbdBankName());
                            account_Number.setText(response.body().getBankData().getUbdAccountNo());
                            ifsc_Code.setText(response.body().getBankData().getUbdIfsc());
                            amountFloat.setText(" ");
                            branch_Address.setText(response.body().getBankData().getUbdBranch());

                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Please enter all fields !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<BankDetailsResponse> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setLayoutUiInit() {
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        not_transactions_list = (TextView) findViewById(R.id.not_transactions_list);
        not_withdrawal_list = (TextView) findViewById(R.id.not_withdrawal_list);
        add_NewAccount = (CardView) findViewById(R.id.add_NewAccount);
        add_new_TransferAccount = (CardView) findViewById(R.id.add_new_TransferAccount);
        submit_Transaction = (Button) findViewById(R.id.submit_Transaction);
        account_holderName = (EditText) findViewById(R.id.account_holderName);
        bank_Name = (EditText) findViewById(R.id.bank_Name);
        account_Number = (EditText) findViewById(R.id.account_Number);
        ifsc_Code = (EditText) findViewById(R.id.ifsc_Code);
        amountFloat = (EditText) findViewById(R.id.amountFloat);
        branch_Address = (EditText) findViewById(R.id.branch_Address);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        withdrawal_recycler_view = (RecyclerView) findViewById(R.id.withdrawal_recycler_view);
        accounts_added_list = (LinearLayout) findViewById(R.id.accounts_added_list);
        withdrawal_history_list = (LinearLayout) findViewById(R.id.withdrawal_history_list);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.submit_Transaction:
                if (validations.isMandatory(account_holderName.getText().toString(), account_holderName) &&
                        validations.isMandatory(bank_Name.getText().toString(), bank_Name) &&
                        validations.isMandatory(account_Number.getText().toString(), account_Number) &&
                        validations.isMandatory(ifsc_Code.getText().toString(), ifsc_Code) &&
                        validations.isMandatory(amountFloat.getText().toString(), amountFloat) &&
                        validations.isMandatory(branch_Address.getText().toString(), branch_Address)) {
                    if (NetworkStatus.isNetworkAvailable(mContext)) {
                        postWithdrawWalletAmount();
                    } else {
                        Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(mContext, "All fields are mandatory !", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.add_new_TransferAccount:
                break;

            case R.id.add_NewAccount:
                break;

        }
    }

    private void postWithdrawWalletAmount() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<BankDetailsResponse> detailsResponseCall = apiInterface.postWithdrawWalletAmount(appPreferencesShared.getUserId(),
                account_holderName.getText().toString(), bank_Name.getText().toString(), account_Number.getText().toString(),
                ifsc_Code.getText().toString(), amountFloat.getText().toString(), branch_Address.getText().toString());

        detailsResponseCall.enqueue(new Callback<BankDetailsResponse>() {
            @Override
            public void onResponse(Call<BankDetailsResponse> call, Response<BankDetailsResponse> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.body() != null) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                            alertDialogBuilder.setCancelable(false);
                            if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india") ||
                                    userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India")) {
                                alertDialogBuilder.setMessage("You will get your balance within 30 working days after verification, minimum amount withdraw is 500 INR");
                            } else {
                                alertDialogBuilder.setMessage("You will get your balance within 30 working days after verification, minimum amount withdraw is 100 USD");
                            }
                            alertDialogBuilder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            finish();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<BankDetailsResponse> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}