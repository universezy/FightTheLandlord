package com.example.administrator.fightthelandlord.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.fightthelandlord.R;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 注册
 **/
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mbtnBack, mbtnRegister;
    private EditText metName, metID, metPassword, metConfirm;

    private Handler RegisterHandler = new Handler();
    private String UserName, UserID, UserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initLayout();
    }

    /**
     * 初始化布局
     **/
    private void initLayout() {
        mbtnBack = (Button) findViewById(R.id.btnBack);
        mbtnBack.setOnClickListener(this);
        mbtnRegister = (Button) findViewById(R.id.btnRegister);
        mbtnRegister.setOnClickListener(this);

        metName = (EditText) findViewById(R.id.etName);
        metID = (EditText) findViewById(R.id.etID);
        metPassword = (EditText) findViewById(R.id.etPassword);
        metConfirm = (EditText) findViewById(R.id.etConfirm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                RegisterActivity.this.finish();
                break;
            case R.id.btnRegister:
                String UserConfirm = metConfirm.getText().toString();
                UserName = metName.getText().toString();
                UserID = metID.getText().toString();
                UserPassword = metPassword.getText().toString();
                if (UserName.equals("") || UserID.equals("") || UserPassword.equals("")) break;
                if (UserConfirm.equals(UserPassword)) {
                    File file = new File(this.getFilesDir(), "user_data.txt");
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (save(file)) {
                        RegisterHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                RegisterActivity.this.finish();
                            }
                        }, 1000);
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Two passwords are not same.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 用户数据存档
     **/
    private boolean save(File file) {
        try {
            FileReader fr1 = new FileReader(file);
            BufferedReader br1 = new BufferedReader(fr1);
            String strReadLine;
            while ((strReadLine = br1.readLine()) != null) {
                int indexID = strReadLine.indexOf("&&&");
                if (indexID < 0) {
                    if (strReadLine.substring(0, indexID).equals(UserID)) {
                        Toast.makeText(RegisterActivity.this, "User exist.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
            FileWriter fw1 = new FileWriter(file, true);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.newLine();
            bw1.write(UserID + "&&&" + UserPassword);
            bw1.flush();
            bw1.close();
            fw1.close();
            br1.close();
            fr1.close();

            File UserFile = new File(this.getFilesDir(), UserID + "_user_data.xml");
            //获取XmlSerializer类的实例  通过xml这个工具类去获取
            XmlSerializer xmlSerializer = Xml.newSerializer();
            //设置XmlSerializer序列化参数
            FileOutputStream fos = new FileOutputStream(UserFile);
            xmlSerializer.setOutput(fos, "utf-8");
            //开始写xml文档开头
            xmlSerializer.startDocument("utf-8", true);
            //写xml的根节点     namespace 命名空间
            //用户数据
            xmlSerializer.startTag(null, "user");

            xmlSerializer.startTag(null, "name");
            xmlSerializer.text(UserName);
            xmlSerializer.endTag(null, "name");

            xmlSerializer.startTag(null, "level");
            xmlSerializer.text("菜鸟");
            xmlSerializer.endTag(null, "level");

            xmlSerializer.startTag(null, "record_win");
            xmlSerializer.text("0");
            xmlSerializer.endTag(null, "record_win");

            xmlSerializer.startTag(null, "record_lose");
            xmlSerializer.text("0");
            xmlSerializer.endTag(null, "record_lose");

            xmlSerializer.endTag(null, "user");

            //結束xml結尾
            xmlSerializer.endDocument();
            //关闭流
            fos.close();

            Toast.makeText(RegisterActivity.this, "Register successful!", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(RegisterActivity.this, "Register unsuccessful.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
