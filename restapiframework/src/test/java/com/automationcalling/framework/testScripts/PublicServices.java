package com.automationcalling.framework.testScripts;

import com.automationcalling.framework.utils.CommonUtil;
import com.automationcalling.framework.utils.Constant;
import com.automationcalling.framework.wrapper.RestAssuredCore;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static com.automationcalling.framework.utils.Constant.STATUSCODE_200;
import static com.automationcalling.framework.wrapper.RestAssuredCore.closeLogger;
import static com.automationcalling.framework.wrapper.RestAssuredCore.initializeLogger;


/**
 * Created by Muthu on 06/15/2018.
 */
public class PublicServices {

    private RestAssuredCore restAssuredCore;

    @BeforeMethod
    public void init() throws IOException {
        initializeLogger();
        restAssuredCore = new RestAssuredCore((CommonUtil.returnProperties(Constant.PROPRETYFILEPATH, "serviceBaseURI"))
                , CommonUtil.returnProperties(Constant.PROPRETYFILEPATH, "serviceBasePath"));
    }

    @Test
    /**
     * Simple test validate in TestNG
     */
    public void validateResponseCode() {
        int statusCode = restAssuredCore.setURLEncodingStatus(false).invokeRestCall("GET", "get/IND/UP").getStatusCode();
        Assert.assertEquals(statusCode, STATUSCODE_200);
    }

    @Test
    public void addingDynamicHeaderAndQueryParam() {
        Map<String, String> headers = new LinkedHashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        params.put("country", "IND"); /*This will help to create dynamic path param with multiple values*/
        params.put("state", "UP");
        int returnStatusCode = restAssuredCore.setURLEncodingStatus(false)
                .setHeaders(headers)
                .setMultiPathParam(params) //same can be implemented in query param for its respective methods
                .invokeRestCall("GET", "get/{country}/{state}").getStatusCode();
        Assert.assertEquals(returnStatusCode, STATUSCODE_200);

    }

    @AfterMethod
    public void tearDown() {
        restAssuredCore.resetRestConfig("all");
        closeLogger();
    }

}
