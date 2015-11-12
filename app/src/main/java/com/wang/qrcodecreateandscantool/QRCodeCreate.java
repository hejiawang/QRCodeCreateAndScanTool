package com.wang.qrcodecreateandscantool;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wang.qrcodecreateandscantool.util.QRCodeCreateUtil;

/**
 * 生成二维码的activity
 *
 * @author wang
 */
public class QRCodeCreate extends Activity implements View.OnClickListener {

    private static final String TAG = "QRCodeCreate";

    private LinearLayout ll_instructions;
    private LinearLayout ll_qrcode;
    private ImageView iv_qrcode_logo;
    private ImageView iv_qrcode_img;
    private EditText et_qrcode_context;
    private Button bt_qrcode_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_create);

        et_qrcode_context = (EditText) findViewById(R.id.et_qrcode_context);
        bt_qrcode_create = (Button) findViewById(R.id.bt_qrcode_create);
        ll_instructions = (LinearLayout) findViewById(R.id.ll_instructions);
        iv_qrcode_logo = (ImageView) findViewById(R.id.iv_qrcode_logo);
        ll_qrcode = (LinearLayout) findViewById(R.id.ll_qrcode);
        iv_qrcode_img = (ImageView) findViewById(R.id.iv_qrcode_img);
        bt_qrcode_create.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_qrcode_create:
                String context = et_qrcode_context.getText().toString().trim();
                if (context == null || context.equals("")) {
                    Toast.makeText(this, "请输入二维码内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, context);
                //生成二维码
                Bitmap bitmap = QRCodeCreateUtil.createNoLogoQRCode(context,200,200);
                iv_qrcode_img.setImageBitmap(bitmap);
                ll_instructions.setVisibility(View.INVISIBLE);
                ll_qrcode.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_qrcode_logo:
                Toast.makeText(this, "选择二维码logo", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
