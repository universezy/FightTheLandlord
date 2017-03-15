package com.example.administrator.fightthelandlord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText metID, metPassword;
    private Button mbtnRegister, mbtnLogin;


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
                Intent intentLogin = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intentLogin);
                LoginActivity.this.finish();
                break;
            case R.id.btnRegister:
                Intent intentRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intentRegister);
                break;
            default:
                break;
        }
    }
    private void Check() {

        File file = new File(this.getFilesDir() + File.separator + "user_data.xml");
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //调用我们定义  解析xml的业务方法
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(is, "utf-8");
            //开始解析事件
            int eventType = xmlPullParser.getEventType();
            String UserID,UserPassword;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (xmlPullParser.getName().equals("id")){
                    xmlPullParser.next();
                    UserID = XmlPullParser.TEXT+"";
                }
                if (xmlPullParser.getName().equals("password")){
                    xmlPullParser.next();
                    UserPassword = XmlPullParser.TEXT+"";
                }
                eventType = xmlPullParser.next();
            }

//            //处理事件，不碰到文档结束就一直处理
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                Log.e("eventType", eventType + "");
//                //因为定义了一堆静态常量，所以这里可以用switch
//                switch (eventType) {
//                    case XmlPullParser.START_DOCUMENT:
//                        // 不做任何操作或初开始化数据
//                        break;
//                    case XmlPullParser.START_TAG:
//                        // 解析XML节点数据
//                        // 获取当前标签名字
//                        String tagName = xmlPullParser.getName();
//                        Log.e(tagName, xmlPullParser.getText() + "");
////                        if (tagName.equals("computer1")) {
////                            // 通过getAttributeValue 和 nextText解析节点的属性值和节点值
////                            for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
////                                Log.e(xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i));
////                            }
////                        }
//
//                        if (tagName.equals("id")) {
//                            // 通过getAttributeValue 和 nextText解析节点的属性值和节点值
//
//                        }
//                        break;
//                    case XmlPullParser.TEXT:
//                        String text = xmlPullParser.getText();
//                        Log.e(text, xmlPullParser.getText() + "");
//                        break;
//                    case XmlPullParser.END_TAG:
//                        // 单节点完成，可往集合里边添加新的数据
//                        break;
//                    case XmlPullParser.END_DOCUMENT:
//                        break;
//                }
//                // 别忘了用next方法处理下一个事件，不然就会死循环
//                eventType = xmlPullParser.next();
//            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
