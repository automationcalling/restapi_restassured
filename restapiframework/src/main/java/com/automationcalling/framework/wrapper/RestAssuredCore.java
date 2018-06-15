package com.automationcalling.framework.wrapper;

import com.automationcalling.framework.utils.CommonUtil;
import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigBuilder;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.*;
import java.util.Map;
import static com.automationcalling.framework.utils.Constant.PROPRETYFILEPATH;

/**
 * Author: Muthuraja R
 * Version: 1.0
 * Date of Creation: 06/15/2018
 * Class Description: This is Rest API wrapper covers all Rest Call Methods built on top of Rest Assured
 */
public class RestAssuredCore {

    private static PrintStream printLogFile;
    private RequestSpecification request;
    private Response response;


    public RestAssuredCore() {
    }

    /**
     *
     * @param baseURI Initialize baseURI in RestAssuredCore constructor
     * @param basePath you can pass basepath alone or including basepath and relative path
     */
    public RestAssuredCore(String baseURI, String basePath) {
        RestAssured.baseURI = baseURI;
        RestAssured.basePath = basePath;
        request = RestAssured.given().log().all();
    }

    /**
     *
     * @param baseURI Initialize baseURI in RestAssuredCore constructor
     * @param basePath you can pass basepath alone or including basepath and relative path
     * @param authenticationType Support as of now basic authentication type
     * @param usernameOrCertPath Can pass either certification path or username, keep one variable so that can use two conditions
     * @param password supply password
     */
    public RestAssuredCore(String baseURI, String basePath, String authenticationType, String usernameOrCertPath, String password) {
        RestAssured.baseURI = baseURI;
        RestAssured.basePath = basePath;
        if (authenticationType.equalsIgnoreCase("ssl")) {
            RestAssured.config = RestAssuredConfig.newConfig().sslConfig(SSLConfig.sslConfig().allowAllHostnames());
            RestAssured.authentication = RestAssured.certificate(usernameOrCertPath, password);
            request = RestAssured.given().log().all();
        } else if (authenticationType.equalsIgnoreCase("basic")) {
            request = RestAssured.given().auth().preemptive().basic(usernameOrCertPath, password).log().all();
        } else {
            RestAssured.useRelaxedHTTPSValidation();
            request = RestAssured.given();
        }
    }

    /**
     *
     * @param baseURI Reset basetURI value as null
     */
    private static void resetBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    /**
     *
     * @param basePath Reset basePath value as null
     */
    private static void resetBasePath(String basePath) {
        RestAssured.basePath = basePath;
    }

    /**
     * It reset all like baseURI, basePATH etc.,
     */
    private static void resetAll() {
        RestAssured.reset();
    }

    /**
     *
     * @throws IOException This is automatic logger which can be called before starting test
     */
    public static void initializeLogger() throws IOException {
        printLogFile = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(CommonUtil.returnProperties(PROPRETYFILEPATH, "logConfigFile")), true)));
        RestAssured.config = RestAssured.config().logConfig(new LogConfig().defaultStream(printLogFile));
    }

    /**
     * Closing log files
     */
    public static void closeLogger() {
        printLogFile.close();
    }

    /**
     *
     * @param state must be set True or False. This will encypt when you set True
     * @return current class object
     */
    public RestAssuredCore setURLEncodingStatus(boolean state) {
        request.urlEncodingEnabled(state);
        return this;
    }

    /**
     * @param configureEnv to verify condition
     */
    public RestAssuredCore reConfigureEnv(String configureEnv) {
        switch (configureEnv) {
            case "baseURI":
                resetBaseURI(configureEnv);
                break;

            case "basePath":
                resetBasePath(configureEnv);
                break;

            default:
                break;
        }
        return this;
    }

    /**
     * @param resetType One method to reset specific requirement
     */
    public RestAssuredCore resetRestConfig(String resetType) {
        switch (resetType) {
            case "baseURI":
                resetBaseURI(null);
                break;

            case "basePath":
                resetBasePath(null);
                break;

            default:
                resetAll();
                break;
        }
        return this;
    }

    /**
     *
     * @param requestType supply keys like "GET","PUT","POST","DELETE","PATCH"
     * @param resourcePath is combination of baseURI, basePATH and resourcePATH
     * @return Instantiating Response class to hold response object after invoking request
     */
    public RestResponse invokeRestCall(String requestType, String resourcePath) {
        switch (requestType) {
            case "GET":
                response = request.log().all().get(resourcePath);
                break;

            case "PUT":
                response = request.log().all().put(resourcePath);
                break;

            case "POST":
                response = request.log().all().post(resourcePath);
                break;

            case "DELETE":
                response = request.log().all().delete(resourcePath);
                break;

            case "PATCH":
                response = request.log().all().patch(resourcePath);
                break;
            default:
                break;
        }
        return new RestResponse(response);
    }

    /**
     *
     * @param contentType set content type
     * @return current class object
     */
    public RestAssuredCore setContentType(String contentType) {
        request = request.contentType(contentType);
        return this;
    }

    /**
     *
     * @param headerType set header
     * @param o object
     * @return current class object
     */
    public RestAssuredCore setHeader(String headerType, Object o) {
        request = request.headers(headerType, o);
        return this;
    }


    /**
     *
     * @param headerType Pass entire header set as map
     * @return current class object
     */
    public RestAssuredCore setHeaders(Map<String, ?> headerType) {
        request = request.headers(headerType);
        return this;
    }

    /**
     *
     * @param filePayload Pass payload in file format
     * @return current class object
     */
    public RestAssuredCore setBody(String filePayload) {
        File file = new File(filePayload);
        request = request.body(file).log().all();
        return this;
    }

    /**
     *
     * @param query accept single query and its param
     * @param param value
     * @return return current class object
     */
    public RestAssuredCore setQueryParam(String query, String param) {
        request = request.queryParam(query, param);
        return this;
    }

    /**
     * Intializing multipathparam
     *
     * @param pathParamList Initializing dynamic value and use variable in rest endpoint
     *                      for eg., test/{value} can add multiple values
     */
    public RestAssuredCore setMultiPathParam(Map<String, ?> pathParamList) {
        request = request.pathParams(pathParamList).log().ifValidationFails();
        return this;
    }

    /**
     * Intializing multiQueryParams
     *
     * @param queryParamList Initializing dynamic query param
     */
    public RestAssuredCore setMultiQueryParams(Map<String, ?> queryParamList) {
        request = request.queryParams(queryParamList).log().ifValidationFails();
        return this;
    }

    /**
     * Intializing multiformParams
     *
     * @param formParamList Initializing dynamic form param list
     */
    public RestAssuredCore setFormParams(Map<String, ?> formParamList) {
        request = request.formParams(formParamList);
        return this;
    }

    /**
     * Intializing attachment for API request
     *
     * @param sAttachment supports file attachments
     */
    public RestAssuredCore setMultiPart(String sAttachment) {
        File file = new File(sAttachment);
        request = request.multiPart(file);
        return this;
    }

    /**
     * @param multiHeaderLists set multiple headers
     * @return current class object
     */
    public RestAssuredCore setMultiHeaders(Map<String, ?> multiHeaderLists) {
        request = request.headers(multiHeaderLists).log().ifValidationFails();
        return this;
    }

    /**
     * This support curl request, each rest invocation converts curl request
     * @return current class object
     */
    public RestAssuredCore curlConfig() {
        request = request.config(new CurlLoggingRestAssuredConfigBuilder().logStacktrace().build()).redirects().follow(false);
        return this;
    }
}
