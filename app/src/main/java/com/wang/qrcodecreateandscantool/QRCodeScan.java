package com.wang.qrcodecreateandscantool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * 扫描二维码的activity
 *
 * Created by wang on 2015/11/12.
 */
public class QRCodeScan extends Activity implements View.OnClickListener {

    private TextView tv_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);

        tv_create = (TextView)findViewById(R.id.tv_create);

        tv_create.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_create :
                Intent intentQRCodeScan = new Intent(this,QRCodeCreate.class);
                startActivity(intentQRCodeScan);
                break;
        }
    }
}
