package com.automationcalling.framework.utils;

/**
 * Author: Muthuraja R
 * Version: 1.0
 * Date of Creation: 06/15/2018
 * Class Description: This class hold all final value here.
 */
public class Constant {

    private Constant() {
        throw new IllegalStateException("Constant Class");
    }
    public static final int STATUSCODE_200 = 200;
    public static final int STATUSCODE_400 = 400;
    public static final String STATUSLINE_200="HTTP/1.1 200 OK";
    public static final String CONTENTTYPEXML = "application/xml";
    public static final String CONTENTYPEJSON = "application/json";
    public static final String PROPRETYFILEPATH = "src/main/resources/sample.properties";

}
