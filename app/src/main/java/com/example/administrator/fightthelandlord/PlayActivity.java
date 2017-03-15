package com.example.administrator.fightthelandlord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mbtnBack, mbtnPass, mbtnHint, mbtnPlay;
    private TextView mtvTime, mtvRestComputer1, mtvWordsComputer1, mtvRestComputer2, mtvWordsComputer2, mtvRestPlayer, mtvWordsPlayer;
    private ImageView ivComputer1, ivComputer2;
    private TableView mvTable;
    private LinearLayout LinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        InitLayout();
    }

    private void InitLayout() {
        mbtnBack = (Button) findViewById(R.id.btnBack);
        mbtnBack.setOnClickListener(this);
        mbtnPass = (Button) findViewById(R.id.btnPass);
        mbtnPass.setOnClickListener(this);
        mbtnHint = (Button) findViewById(R.id.btnHint);
        mbtnHint.setOnClickListener(this);
        mbtnPlay = (Button) findViewById(R.id.btnPlay);
        mbtnPlay.setOnClickListener(this);

        mtvTime = (TextView) findViewById(R.id.tvTime);
        mtvTime.setOnClickListener(this);
        mtvRestComputer1 = (TextView) findViewById(R.id.tvRestComputer1);
        mtvWordsComputer1 = (TextView) findViewById(R.id.tvWordsComputer1);
        mtvRestComputer2 = (TextView) findViewById(R.id.tvRestComputer2);
        mtvWordsComputer2 = (TextView) findViewById(R.id.tvWordsComputer2);
        mtvRestPlayer = (TextView) findViewById(R.id.tvRestPlayer);
        mtvWordsPlayer = (TextView) findViewById(R.id.tvWordsPlayer);

        ivComputer1 = (ImageView) findViewById(R.id.ivComputer1);
        ivComputer2 = (ImageView) findViewById(R.id.ivComputer2);

        mvTable = (TableView)findViewById(R.id.vTable);


        LinearLayout = (LinearLayout) findViewById(R.id.llButton);

        LinearLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTime:
                LinearLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.btnBack:
                PlayActivity.this.finish();
                break;
            case R.id.btnPass:
                LinearLayout.setVisibility(View.INVISIBLE);
                break;
            case R.id.btnHint:

                break;
            case R.id.btnPlay:
                mvTable.setContent("test");
                mvTable.invalidate();
                break;
            default:
                break;
        }
    }
}
