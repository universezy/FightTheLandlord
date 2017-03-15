package com.example.administrator.fightthelandlord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mbtnBack, mbtnRegister;
    private EditText metID, metPassword, metConfirm;

    private String UserID,UserPassword;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InitLayout();
    }

    private void InitLayout() {
        mbtnBack = (Button) findViewById(R.id.btnBack);
        mbtnBack.setOnClickListener(this);
        mbtnRegister = (Button) findViewById(R.id.btnRegister);
        mbtnRegister.setOnClickListener(this);

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
                UserID=metID.getText().toString();
                        UserPassword = metPassword.getText().toString();
                if(UserConfirm.equals(UserPassword)){
                    file = new File(this.getFilesDir(), "user_data_"+UserID+".xml");
                    if(file.exists()){
                        Toast.makeText(RegisterActivity.this,"User exists!",Toast.LENGTH_SHORT).show();
                    }else{
                        if(Save()){
                            Toast.makeText(RegisterActivity.this,"Register successful!",Toast.LENGTH_SHORT).show();
                            RegisterActivity.this.finish();
                        }else {
                            Toast.makeText(RegisterActivity.this,"Register failed!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(RegisterActivity.this,"Two passwords are not same.",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }



    private boolean Save() {
        try {
            //获取XmlSerializer类的实例  通过xml这个工具类去获取
            XmlSerializer xmlSerializer = Xml.newSerializer();
            //设置XmlSerializer序列化参数
            FileOutputStream fos = new FileOutputStream(file);
            xmlSerializer.setOutput(fos, "utf-8");
            //开始写xml文档开头
            xmlSerializer.startDocument("utf-8", true);
            //写xml的根节点     namespace 命名空间
            //用户数据
            xmlSerializer.startTag(null, "user");

            xmlSerializer.startTag(null, "id");
            xmlSerializer.text("testid");
            xmlSerializer.endTag(null, "id");

            xmlSerializer.startTag(null, "password");
            xmlSerializer.text("testpassword");
            xmlSerializer.endTag(null, "password");

            xmlSerializer.endTag(null, "user");

            //进度数据
            xmlSerializer.startTag(null, "process");

            xmlSerializer.startTag(null, "landlord");
            xmlSerializer.text("computer2");
            xmlSerializer.endTag(null, "landlord");

            xmlSerializer.startTag(null, "countdown");
            xmlSerializer.text("testTime");
            xmlSerializer.endTag(null, "countdown");

            xmlSerializer.startTag(null, "nowplayer");
            xmlSerializer.text("player");
            xmlSerializer.endTag(null, "nowplayer");

            xmlSerializer.startTag(null, "restcard");

            xmlSerializer.startTag(null, "computer1");

            xmlSerializer.attribute(null, "rest", "");

            xmlSerializer.endTag(null, "computer1");

            xmlSerializer.startTag(null, "computer2");

            xmlSerializer.attribute(null, "rest", "");

            xmlSerializer.endTag(null, "computer2");

            xmlSerializer.startTag(null, "player");

            xmlSerializer.attribute(null, "rest", "");

            xmlSerializer.endTag(null, "player");

            xmlSerializer.endTag(null, "restcard");

            xmlSerializer.endTag(null, "process");

            //結束xml結尾
            xmlSerializer.endDocument();
            //关闭流
            fos.close();
            if ((new FileInputStream(file)).available() > 0)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
