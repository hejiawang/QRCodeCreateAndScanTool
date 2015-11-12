package com.wang.qrcodecreateandscantool;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 生成二维码的activity
 *
 * @author wang
 */
public class QRCodeCreate extends Activity implements View.OnClickListener {

    private static final String TAG = "QRCodeCreate";

    private EditText et_qrcode_context;
    private Button bt_qrcode_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_create);

        et_qrcode_context = (EditText) findViewById(R.id.et_qrcode_context);
        bt_qrcode_create = (Button) findViewById(R.id.bt_qrcode_create);

        bt_qrcode_create.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case  R.id.bt_qrcode_create:
                String context = et_qrcode_context.getText().toString().trim();
                if( context == null || context.equals("") ) {
                    Toast.makeText(this, "请输入二维码内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, context);







                break;
            default:
                break;
        }
    }
}
