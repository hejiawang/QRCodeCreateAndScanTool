package com.wang.qrcodecreateandscantool;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.FileChooserActivity;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.wang.qrcodecreateandscantool.util.FileRootUtil;
import com.wang.qrcodecreateandscantool.util.QRCodeCreateUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 生成二维码的activity
 *
 * @author wang
 */
public class QRCodeCreate extends Activity implements View.OnClickListener {

    private boolean isHaveLogo = false;

    private static final String TAG = "QRCodeCreate";
    private final static int REQUEST_CODE_FILE_CHOOSE = 0x01;

    private LinearLayout ll_instructions;
    private LinearLayout ll_qrcode;
    private ImageView iv_qrcode_logo;
    private ImageView iv_qrcode_img;
    private EditText et_qrcode_context;
    private Button bt_qrcode_logo;
    private Button bt_qrcode_create;
    private Button bt_reset;
    private Button bt_save;
    private TextView tv_scan;

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
        bt_save = (Button) findViewById(R.id.bt_save);
        tv_scan = (TextView) findViewById(R.id.tv_scan);

        bt_qrcode_logo.setOnClickListener(this);
        bt_qrcode_create.setOnClickListener(this);
        bt_reset.setOnClickListener(this);
        bt_save.setOnClickListener(this);
        tv_scan.setOnClickListener(this);
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
                Bitmap qrCodeBitmap = null;
                if (isHaveLogo) {   //生成有logo的二维码
                    BitmapDrawable draw = (BitmapDrawable) iv_qrcode_logo.getDrawable();
                    Bitmap qrCodeLogoBitmap = draw.getBitmap();
                    qrCodeBitmap = QRCodeCreateUtil.createHaveLogoQRCode(context, 800, 800, qrCodeLogoBitmap);
                } else { //生成没有logo的二维码
                    qrCodeBitmap = QRCodeCreateUtil.createNoLogoQRCode(context, 800, 800);
                }
                iv_qrcode_img.setImageBitmap(qrCodeBitmap);
                ll_instructions.setVisibility(View.INVISIBLE);
                iv_qrcode_logo.setVisibility(View.INVISIBLE);
                ll_qrcode.setVisibility(View.VISIBLE);
                break;

            case R.id.bt_qrcode_logo:  //选择二维码logo
                isHaveLogo = true;
                Intent intent = new Intent();
                intent.setClass(this, FileChooserActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FILE_CHOOSE);
                break;

            case R.id.bt_reset: //重置
                isHaveLogo = false;
                et_qrcode_context.setText("");
                ll_qrcode.setVisibility(View.INVISIBLE);
                iv_qrcode_logo.setVisibility(View.INVISIBLE);
                ll_instructions.setVisibility(View.VISIBLE);
                break;

            case R.id.bt_save:  //保存生成的二维码
                try {
                    String filePath = FileRootUtil.getFileRoot() + File.separator + "qr_" + System.currentTimeMillis() + ".jpg";
                    BitmapDrawable draw = (BitmapDrawable) iv_qrcode_img.getDrawable();
                    Bitmap qrCodeImg = draw.getBitmap();
                    qrCodeImg.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(filePath));
                    iv_qrcode_img.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    Toast.makeText(this, "以存放到手机根目录下", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_scan : //转到扫描二维码的activity
                Intent intentQRCodeScan = new Intent(this,QRCodeScan.class);
                startActivity(intentQRCodeScan);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_FILE_CHOOSE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        final Uri uri = data.getData();
                        try {
                            final String path = FileUtils.getPath(this, uri);
                            if (path.endsWith(".png") || path.endsWith(".jpg")) {
                                Log.i(TAG, path);
                                ContentResolver cr = this.getContentResolver();
                                Bitmap bmp = MediaStore.Images.Media.getBitmap(cr, uri);
                                iv_qrcode_logo.setImageBitmap(bmp);
                                ll_instructions.setVisibility(View.INVISIBLE);
                                ll_qrcode.setVisibility(View.INVISIBLE);
                                iv_qrcode_logo.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(this, "请选择图片资源", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            default:
                break;
        }
    }
}
