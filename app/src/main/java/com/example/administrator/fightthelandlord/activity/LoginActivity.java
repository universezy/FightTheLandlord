package com.example.administrator.fightthelandlord.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.fightthelandlord.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText metID, metPassword;
    private Button mbtnRegister, mbtnLogin;

    private Handler LoginHandler = new Handler();
    private String UserID, UserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitLayout();
    }

    private void InitLayout() {
        metID = (EditText) findViewById(R.id.etID);
        metPassword = (EditText) findViewById(R.id.etPassword);

        mbtnLogin = (Button) findViewById(R.id.btnLogin);
        mbtnLogin.setOnClickListener(this);

        mbtnRegister = (Button) findViewById(R.id.btnRegister);
        mbtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                UserID = metID.getText().toString();
                UserPassword = metPassword.getText().toString();
                if (UserID.equals("") || UserPassword.equals("")) break;
                File file = new File(this.getFilesDir(), "user_data.txt");
                if (!file.exists()) {
                    Toast.makeText(LoginActivity.this, "No user data file found.\nPlease register firstly", Toast.LENGTH_SHORT).show();
                } else {
                    if (Check(file)) {
                        LoginHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intentLogin = new Intent(LoginActivity.this, MainActivity.class);
                                intentLogin.putExtra("UserID", UserID);
                                startActivity(intentLogin);
                                LoginActivity.this.finish();
                            }
                        }, 2000);
                    }
                }
                break;
            case R.id.btnRegister:
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
                break;
            default:
                break;
        }
    }

    private boolean Check(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String strReadLine;
            while ((strReadLine = br.readLine()) != null) {
                int indexID = strReadLine.indexOf("&&&");
                if (indexID > 0) {
                    if (strReadLine.substring(0, indexID).equals(UserID)) {
                        if (strReadLine.substring(indexID + 3).equals(UserPassword)) {
                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        Toast.makeText(LoginActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
            Toast.makeText(LoginActivity.this, "Nonexistent user.", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
