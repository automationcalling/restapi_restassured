package com.automationcalling.framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Author: Muthuraja R
 * Version: 1.0
 * Date of Creation: 06/15/2018
 * Class Description: This class holds all common util functions, may be static method implemenation
 */
public class CommonUtil {

    private CommonUtil() {
        throw new IllegalStateException("CommonUtil class");
    }

    public static String returnProperties(String filePath, String keyName) throws IOException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream(filePath);
        prop.load(input);
        return prop.getProperty(keyName);
    }
}
