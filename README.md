# restapi_restassured

RestAssuredCore is a wrapper build on top of Rest Assured Library. The idea is used to customize Rest Assured Library Methods and implemented custom methods and reusable components what exactly is required for specific project requirements.

Features and Scope of this Framework:
=====================================
Built using Method Chaining in Java
Supports http and https RestAPI call
Supports curl
In-build validation for XML and Json response using Hamcrest Matcher as well as extract value for TestNG Assert validation
Code Quality and standard compliance with Sonar
Can easily integrate this wrapper with your Selenium Hybrid or Cucumber Frameworks
Supports Authentication like Basic, SSL (Certificates) but OAUTH needs to be added soon
Wrapper supports for End-to-End Automation
Can help to start automation from the day 1 and easy integration
Supports Payloads(File type) for PUT/POST request.
Possible to reduce your code as well as able to implement 1 line code.

Test Script Implementation Strategy:
====================================
All RestAssuredCore Method must be called before Call the method “InvokeRestCall”, after invoked it doesn’t return RestAssuredCore implementation method rather it display Response Class Method and Hamcrest Matcher Validation Methods. So, the idea is you must initialize/call all your request related attributes for eg., initialize baseURI, basePATH, resourcePath, setting body, header, content type etc before Invoking “InvokeRestCall” method, this is same technique as how Rest Assure Library implemented.

Examples:
=========
Here’s an example of how to make a request and validate the JSON or XML response
This framework supports two types of validation for Response Object
Hamcrest Matcher: Added wrapper method which supports in-build in “Response Class

try {
    new RestAssuredCore((CommonUtil.returnProperties(Constant.PROPRETYFILEPATH, "serviceBaseURI"))
            , CommonUtil.returnProperties(Constant.PROPRETYFILEPATH, "serviceBasePath"))
            .setURLEncodingStatus(false)
            .invokeRestCall("GET", "get/IND/UP")
            .hamcrestStatusCodeValidate(200);
} catch (Exception e) {
    e.printStackTrace();
}

The above snapshot we pass baseURI, basePATH, resourcePath and url encoding and final invoke rest call and validate status code in using hamcrest matcher validation method.

TestNG Validation: In Response class, you have to use getXXXX (Method start with get) where it return either integer or string based on method. Having return value you can implement TestNG assertion. For eg.,

@Test
public void validateResponseCode() {
 int statusCode = restAssuredCore.invokeRestCall("GET", "/all").getStatusCode();
 Assert.assertEquals(statusCode, STATUSCODE_200);
}

