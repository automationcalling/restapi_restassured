package com.automationcalling.framework.testScripts;
import com.automationcalling.framework.utils.CommonUtil;
import com.automationcalling.framework.utils.Constant;
import com.automationcalling.framework.wrapper.RestAssuredCore;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;
import static com.automationcalling.framework.utils.Constant.STATUSCODE_200;
import static com.automationcalling.framework.utils.Constant.STATUSLINE_200;
import static com.automationcalling.framework.wrapper.RestAssuredCore.closeLogger;
import static com.automationcalling.framework.wrapper.RestAssuredCore.initializeLogger;


/**
 * Created by Muthu on 06/15/2018.
 */
public class WeatherTestScript {

    private RestAssuredCore restAssuredCore;

    @BeforeMethod
    public void init() throws IOException {
        initializeLogger();
        restAssuredCore = new RestAssuredCore((CommonUtil.returnProperties(Constant.PROPRETYFILEPATH, "weatherBaseURI"))
                , CommonUtil.returnProperties(Constant.PROPRETYFILEPATH, "weatherBasePath"));
    }

    @Test
    /**
     * Validate using TestNG
     */
    public void validateResponseCode() {
        int statusCode = restAssuredCore.setURLEncodingStatus(false).invokeRestCall("GET", "?query=san").getStatusCode();
        Assert.assertEquals(statusCode, STATUSCODE_200);
    }

    @Test
    /**
     * Validate using Hamcrest Matcher in RestResponse Wrapper
     */
    public void validatestatusCodeUsingHamcrest() {
        restAssuredCore
                .setURLEncodingStatus(false)
                .invokeRestCall("GET", "?query=san")
                .hamcrestStatusCodeValidate(STATUSCODE_200);
    }

    @Test
    /**
     * Supply element name return values as List.
     */
    public void validateResponseElement() {
        List<String> responseElement = restAssuredCore.setURLEncodingStatus(false).invokeRestCall("GET", "?query=san").responseUsingPathArrays("title");
        Assert.assertEquals("San Francisco", responseElement.get(0));
    }

    @Test
    /**
     * Supply element name return values as List.
     */
    public void validateResponseElement1() {
        restAssuredCore
                .setURLEncodingStatus(false)
                .invokeRestCall("GET", "?query=san")
                .hamcrestResponseBodyValidation("hasSize", "title", "11");
    }

    @Test
    public void validateResponseElement2() {
        Assert.assertEquals(STATUSLINE_200, restAssuredCore
                .setURLEncodingStatus(false)
                .invokeRestCall("GET", "?query=san")
                .getStatusLine());
    }


    @AfterMethod
    public void tearDown() {
        restAssuredCore.resetRestConfig("all");
        closeLogger();
    }
}
