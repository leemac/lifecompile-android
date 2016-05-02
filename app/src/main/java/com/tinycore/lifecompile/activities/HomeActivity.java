package com.tinycore.lifecompile.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tinycore.lifecompile.R;
import com.tinycore.lifecompile.models.Note;
import com.tinycore.lifecompile.models.Token;
import com.tinycore.lifecompile.network.LifeCompileServiceHelper;
import com.tinycore.lifecompile.services.LifeCompileService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private LifeCompileService _lifcompileService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button fab = (Button) findViewById(R.id.fab);
        assert fab != null;

        final Context context = this;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("token");
                editor.apply();

                finish();
            }
        });

        _lifcompileService = LifeCompileServiceHelper.GetService();
        
        final Call<List<Note>> loginCall = _lifcompileService.getNotes();

        Log.d("Home", "Home Loading");
        loginCall.enqueue(new Callback<List<Note>>() {

            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                Log.d("Home", response.toString());

                if (response.isSuccessful()) {
                    Log.d("Home Loaded", "Dododo");
                    List<Note> tokenResponse = response.body();

                    if (!tokenResponse.isEmpty()) {
                        Toast.makeText(context, "Loaded Notes!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Got no Notes!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ResponseBody errorBody = response.errorBody();
                    Log.d("Home Error", errorBody.toString());
                    Toast.makeText(context, errorBody.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                Log.d("Home Failed", t.getMessage());
                Toast.makeText(context, "Failure! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
