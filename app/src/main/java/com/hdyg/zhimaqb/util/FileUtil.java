package com.hdyg.zhimaqb.util;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/12/4.
 */

public class FileUtil {
    private final static String PATH = Environment.getExternalStorageDirectory().getPath() + "/zmzj/";
    public final static String ID_FRONT = "idFront.jpg";
    public final static String ID_BEHIND = "idBehind.jpg";
    public final static String CARD_FRONT = "cardFront.jpg";
    public final static String CARD_BEHIND = "cardBehind.jpg";
    public final static String ID_CARD = "idCard.jpg";

    public static String getFilePath(String fileName) {
        String filePath = PATH + fileName;
        File file = new File(PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        return filePath;
    }
}
