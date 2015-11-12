package com.wang.qrcodecreateandscantool.util;

import android.os.Environment;

import java.io.File;

/**
 * 获取二维码存放路径工具类
 * <p/>
 * Created by wang on 2015/11/12.
 */
public class FileRootUtil {

    /**
     * 生成的二维码图片存放路径.
     * 若有sd卡，存在sd卡根路径上;
     * 否则存在手机内存的根路径上.
     *
     * @return 存放路径
     */
    public static String getFileRoot() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);//是否有sdk卡
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();

        } else {
            sdDir = Environment.getRootDirectory();
        }
        String fileRoot = sdDir.toString();
        return fileRoot;
    }
}
