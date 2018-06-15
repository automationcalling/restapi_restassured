package com.automationcalling.framework.testScripts;

import com.automationcalling.framework.utils.CommonUtil;
import com.automationcalling.framework.utils.Constant;
import com.automationcalling.framework.wrapper.RestAssuredCore;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static com.automationcalling.framework.wrapper.RestAssuredCore.closeLogger;
import static com.automationcalling.framework.wrapper.RestAssuredCore.initializeLogger;


/**
 * Created by Muthu on 06/15/2018.
 */
public class PublicServicesResponseMsgValidation {

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
    public void returnResponseWithMap() {
        Map<String, String> returnList = restAssuredCore
                .setURLEncodingStatus(false)
                .invokeRestCall("GET", "get/IND/UP")
                .getJsonwithMap("RestResponse.result"); //should pass JsonArray so that it will bring key/value pair
    }

    @Test
    public void responseMessagecontainsValidation() {
        boolean status = restAssuredCore
                .setURLEncodingStatus(false)
                .invokeRestCall("GET", "get/IND/UP")
                .getResponseObjectContains("country"); //should pass element text or element name. This will validate the element present in overall response object
        System.out.println(status);
    }

    @Test
    public void responseObjectReturnList() throws IOException {

        restAssuredCore.resetRestConfig("All");
        restAssuredCore = new RestAssuredCore((CommonUtil.returnProperties(Constant.PROPRETYFILEPATH, "locationBaseURI"))
                , CommonUtil.returnProperties(Constant.PROPRETYFILEPATH, "locationbasePath"));
        ArrayList<HashMap<String, ?>> returnElement = restAssuredCore
                .setURLEncodingStatus(false)
                .invokeRestCall("GET", "2017/circuits.json")
                .extractMultipleMapsObject("MRData.CircuitTable.Circuits");
        System.out.println(returnElement);
    }


    @AfterMethod
    public void tearDown() {
        restAssuredCore.resetRestConfig("all");
        closeLogger();
    }

}
