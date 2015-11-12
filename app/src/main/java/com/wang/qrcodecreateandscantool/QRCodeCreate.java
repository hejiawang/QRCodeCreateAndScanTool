package com.wang.qrcodecreateandscantool;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

    private boolean isHaveLogo = false;

    private static final String TAG = "QRCodeCreate";

    private LinearLayout ll_instructions;
    private LinearLayout ll_qrcode;
    private ImageView iv_qrcode_logo;
    private ImageView iv_qrcode_img;
    private EditText et_qrcode_context;
    private Button bt_qrcode_logo;
    private Button bt_qrcode_create;
    private Button bt_reset;

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
        bt_qrcode_logo = (Button) findViewById(R.id.bt_qrcode_logo);
        bt_reset = (Button) findViewById(R.id.bt_reset);

        bt_qrcode_logo.setOnClickListener(this);
        bt_qrcode_create.setOnClickListener(this);
        bt_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_qrcode_create: //生成二维码
                String context = et_qrcode_context.getText().toString().trim();
                if (context == null || context.equals("")) {
                    Toast.makeText(this, "请输入二维码内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, context);
                Bitmap qrCodeBitmap = null;
                if (isHaveLogo) {   //生成有logo的二维码
                    //iv_qrcode_logo.setDrawingCacheEnabled(true);
                    //Bitmap qrCodeLogoBitmap = iv_qrcode_logo.getDrawingCache();
                    //iv_qrcode_logo.setDrawingCacheEnabled(false);


                   // BitmapDrawable draw = (BitmapDrawable) getResources().getDrawable(R.drawable.logo);
                    BitmapDrawable draw = (BitmapDrawable) iv_qrcode_logo.getDrawable();
                    Bitmap qrCodeLogoBitmap = draw.getBitmap();


                    qrCodeBitmap = QRCodeCreateUtil.createHaveLogoQRCode(context, 200, 200, qrCodeLogoBitmap);
                } else { //生成没有logo的二维码
                    qrCodeBitmap = QRCodeCreateUtil.createNoLogoQRCode(context, 200, 200);
                }
                iv_qrcode_img.setImageBitmap(qrCodeBitmap);
                ll_instructions.setVisibility(View.INVISIBLE);
                iv_qrcode_logo.setVisibility(View.INVISIBLE);
                ll_qrcode.setVisibility(View.VISIBLE);
                break;

            case R.id.bt_qrcode_logo:  //选择二维码logo
                Log.i(TAG, "bt_qrcode_logo is click");
                isHaveLogo = true;
                //开启图库
                iv_qrcode_logo.setImageResource(R.drawable.logo);
                //从图库返回图片的bitmap
                ll_instructions.setVisibility(View.INVISIBLE);
                ll_qrcode.setVisibility(View.INVISIBLE);
                iv_qrcode_logo.setVisibility(View.VISIBLE);
                break;

            case R.id.bt_reset: //重置
                isHaveLogo = false;
                et_qrcode_context.setText("");
                ll_qrcode.setVisibility(View.INVISIBLE);
                iv_qrcode_logo.setVisibility(View.INVISIBLE);
                ll_instructions.setVisibility(View.VISIBLE);
                break;
        }
    }
}
