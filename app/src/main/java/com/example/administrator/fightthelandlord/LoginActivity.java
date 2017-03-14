package com.example.administrator.fightthelandlord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText metID, metPassword;
    private Button mbtnLogin;
    private TextView mtvRegister;


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

        mtvRegister = (TextView) findViewById(R.id.tvRegister);
        mtvRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                Intent intentLogin = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intentLogin);
                LoginActivity.this.finish();
                break;
            case R.id.tvRegister:
                Intent intentRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intentRegister);
                break;
            default:
                break;
        }
    }
}
