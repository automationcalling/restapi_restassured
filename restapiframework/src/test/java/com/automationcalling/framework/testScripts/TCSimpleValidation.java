package com.automationcalling.framework.testScripts;
import com.automationcalling.framework.utils.CommonUtil;
import com.automationcalling.framework.utils.Constant;
import com.automationcalling.framework.wrapper.RestAssuredCore;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import static com.automationcalling.framework.utils.Constant.STATUSCODE_200;
import static com.automationcalling.framework.wrapper.RestAssuredCore.closeLogger;
import static com.automationcalling.framework.wrapper.RestAssuredCore.initializeLogger;


/**
 * Created by Muthu on 06/15/2018.
 */
public class TCSimpleValidation {

    private RestAssuredCore restAssuredCore;

    @BeforeMethod
    public void init() throws IOException {
        initializeLogger();
        restAssuredCore = new RestAssuredCore((CommonUtil.returnProperties(Constant.PROPRETYFILEPATH, "countrybaseURI"))
                , CommonUtil.returnProperties(Constant.PROPRETYFILEPATH, "countrybasepath"));
    }

    @Test
    public void validateResponseCode() {
        int statusCode = restAssuredCore.invokeRestCall("GET", "/all").getStatusCode();
        Assert.assertEquals(statusCode, STATUSCODE_200);
    }

    @Test
    public void getHeader() {
        String headers = restAssuredCore.invokeRestCall("GET", "/all").getAllheadersAsString();
        System.out.println(headers);
    }

    @Test
    public void getHeaderSize() {
        System.out.println(restAssuredCore.invokeRestCall("GET", "/all").getHeaderSize());
    }

    @Test
    public void getContentType() {
        String contentType = restAssuredCore.invokeRestCall("GET", "/all").getContentType();
        System.out.println(contentType);
    }

    @Test
    public void returnResponseTime() {
        long resonseTime = restAssuredCore.invokeRestCall("GET", "/all").getResponseTime();
        System.out.println(resonseTime);
    }

    @AfterMethod
    public void tearDown() {
        restAssuredCore.resetRestConfig("all");
        closeLogger();
    }
}
