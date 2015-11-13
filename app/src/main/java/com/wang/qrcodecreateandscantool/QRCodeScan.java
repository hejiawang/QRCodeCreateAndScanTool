package com.wang.qrcodecreateandscantool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 扫描二维码的activity
 * <p/>
 * Created by wang on 2015/11/12.
 */
public class QRCodeScan extends Activity implements View.OnClickListener {

    private final static int SCANNIN_GREQUEST_CODE = 1;

    private TextView tv_create;
    private TextView tv_qrcode_result;
    private Button bt_qrcode_scan;
    private ImageView iv_qrcode_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);

        tv_create = (TextView) findViewById(R.id.tv_create);
        tv_qrcode_result = (TextView) findViewById(R.id.tv_qrcode_result);
        bt_qrcode_scan = (Button) findViewById(R.id.bt_qrcode_scan);
        iv_qrcode_bitmap = (ImageView) findViewById(R.id.iv_qrcode_bitmap);

        tv_create.setOnClickListener(this);
        bt_qrcode_scan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_create:
                Intent intentQRCodeScan = new Intent(this, QRCodeCreate.class);
                startActivity(intentQRCodeScan);
                break;

            case R.id.bt_qrcode_scan:
                Intent intent = new Intent();
                intent.setClass(this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    tv_qrcode_result.setText(bundle.getString("result"));
                    iv_qrcode_bitmap.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
        }
    }
}
