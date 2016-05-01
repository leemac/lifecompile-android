package com.tinycore.lifecompile.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tinycore.lifecompile.R;
import com.tinycore.lifecompile.models.Token;
import com.tinycore.lifecompile.network.LifeCompileServiceHelper;
import com.tinycore.lifecompile.services.LifeCompileService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LifeCompileService _gardenrrService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _gardenrrService = LifeCompileServiceHelper.GetService();
    }

    public void loginClick(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);


        SetLoading(true);

        TextView emailTextView = (TextView) this.findViewById(R.id.textboxEmail);
        TextView passwordTextView = (TextView) this.findViewById(R.id.textboxPassword);

        final Call<Token> loginCall = _gardenrrService.login(emailTextView.getText().toString(), passwordTextView.getText().toString());

        final Context context = this;

        loginCall.enqueue(new Callback<Token>() {

            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Token tokenResponse = response.body();

                    if (tokenResponse.Success) {
                        SharedPreferences settings = PreferenceManager
                                .getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("token", tokenResponse.Token);
                        editor.apply();

                        Intent intent = new Intent(context, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(context, tokenResponse.Message, Toast.LENGTH_SHORT).show();

                        SetLoading(false);
                    }
                } else {
                    ResponseBody errorBody = response.errorBody();
                    Toast.makeText(context, errorBody.toString(), Toast.LENGTH_SHORT).show();

                    SetLoading(false);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(context, "Failure! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                SetLoading(false);
            }
        });
    }

    private void SetLoading(boolean isLoading) {
        View panelLogin = this.findViewById(R.id.panelLogin);
        View progressBar = this.findViewById(R.id.progressBarLoading);

        if (isLoading) {
            panelLogin.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            panelLogin.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
