package com.wang.qrcodecreateandscantool.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * 生成二维码的工具类
 * <p/>
 * Created by wang on 2015/11/12.
 */
public class QRCodeCreateUtil {

    /**
     * 生成没有logo的二维码Bitmap
     *
     * @param context   二维码内容
     * @param widthPix  二维码宽度
     * @param heightPix 二维码高度
     * @return Bitmap
     */
    public static Bitmap createNoLogoQRCode(String context, int widthPix, int heightPix) {
        Bitmap bitmap = null;
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(context, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }
            bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 生成有logo的二维码Bitmap
     *
     * @param context   二维码内容
     * @param widthPix  二维码宽度
     * @param heightPix 二维码高度
     * @param logo      二维码logo的bitmap
     * @return Bitmap
     */
    public static Bitmap createHaveLogoQRCode(String context, int widthPix, int heightPix, Bitmap logo) {

        Bitmap haveLogoQRCodeBitmap = null;
        Bitmap noLogoQRCodeBitmap = createNoLogoQRCode(context, widthPix, heightPix);
        haveLogoQRCodeBitmap = addLogo(noLogoQRCodeBitmap, logo);
        return haveLogoQRCodeBitmap;
    }


    /**
     * 向原来的bitmap上面添加一个bitmap
     *
     * @param src  原始Bitmap
     * @param logo 添加的Bitmap
     * @return Bitmap
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
        float scaleFactor = srcWidth * 1.0f / 8 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }
}

