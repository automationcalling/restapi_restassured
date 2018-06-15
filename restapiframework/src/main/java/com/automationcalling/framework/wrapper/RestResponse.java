package com.automationcalling.framework.wrapper;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

/**
 * Author: Muthuraja R
 * Version: 1.0
 * Date of Creation: 06/15/2018
 * Class Description: This is Java bean class hold objects and also perform validation using hamcrest.Matchers
 * Built on top of Rest Assured
 */
public class RestResponse {
    private Response response;

    /**
     * @param res holds response object
     */
    public RestResponse(Response res) {
        response = res;
    }

    /**
     * @return return response as a string
     */
    public String getAPIResponseAsString() {
        return response.then().log().all().extract().asString();
    }

    /**
     * @return response return as pretty print format for eg., XML and Json alignment
     */
    public String getAPIResponseAsPrettyPrint() {
        return response.prettyPrint();
    }

    /**
     * @return statusline as string from response object
     */
    public String getStatusLine() {
        return response.then().log().all().extract().statusLine();
    }

    /**
     * @return Content Type as string from response object
     */
    public String getContentType() {
        return response.then().log().all().extract().contentType();
    }

    /**
     * @return return header from response object
     */
    public String getAllheadersAsString() {
        return response.then().log().all().extract().headers().toString();
    }

    /**
     * @return header size from response object
     */
    public int getHeaderSize() {
        return response.then().log().all().extract().headers().size();
    }

    /**
     * @return status code from response object
     */
    public int getStatusCode() {
        return response.then().log().all().extract().statusCode();
    }

    /**
     * @return cookies from response object
     */
    public int getCookiesSize() {
        return response.then().log().all().extract().cookies().size();
    }

    /**
     * @param statusCode pass statusCode to validate using hamcrest Matcher
     * @return current class object
     */
    public RestResponse hamcrestStatusCodeValidate(int statusCode) {
        response.then().assertThat().statusCode(statusCode);
        return this;
    }

    /**
     * @param statusLine pass statusLine to validate using hamcrest Matcher
     * @return current class object
     */
    public RestResponse hamcrestStatusLineValidate(String statusLine) {
        response.then().assertThat().statusLine(statusLine);
        return this;
    }

    /**
     * @param validationType  inbuild hamcreast methods
     * @param elementName     xml or json element
     * @param expectedResults to verify expected results
     * @return
     */
    public RestResponse hamcrestResponseBodyValidation(String validationType, String elementName, String expectedResults) {
        switch (validationType) {
            case "equalTo":
                response.then().log().all().body(elementName, equalTo(expectedResults)).log().ifValidationFails();
                break;
            case "hasSize":
                response.then().log().all().body(elementName, hasSize(Integer.parseInt(expectedResults))).log().ifValidationFails();
                break;
            case "hasItem":
                response.then().log().all().body(elementName, hasItem(Integer.parseInt(expectedResults))).log().ifValidationFails();
                break;
            case "hasItemString":
                response.then().log().all().body(elementName, hasItem(expectedResults)).log().ifValidationFails();
                break;
            case "hasXpath":
                response.then().log().all().body(elementName, hasXPath(expectedResults)).log().ifValidationFails();
                break;
            case "containsString":
                response.then().log().all().body(elementName, containsString(expectedResults)).log().ifValidationFails();
                break;
            case "isEmptyString":
                response.then().log().all().body(isEmptyString()).log().ifValidationFails();
                break;
            default:
                break;
        }
        return this;

    }

    /**
     * @param validationType Data Type of elements to be retrieved
     * @param elementName    element to be located
     *                       All elements return string value, Need to typecast if required
     * @return elementValue
     */
    public String getJsonPathReturnValue(String validationType, String elementName) {
        String elementValue = null;
        JsonPath jsonPath = new JsonPath(getAPIResponseAsString());
        switch (validationType) {
            case "int":
                elementValue = String.valueOf(jsonPath.getInt(elementName));
                break;
            case "string":
                elementValue = jsonPath.getString(elementName);
                break;
            case "char":
                elementValue = String.valueOf(jsonPath.getChar(elementName));
                break;
            case "float":
                elementValue = String.valueOf(jsonPath.getFloat(elementName));
                break;
            case "double":
                elementValue = String.valueOf(jsonPath.getDouble(elementName));
                break;
            default:
                break;
        }
        return elementValue;
    }

    /**
     * @param validationType Data Type of elements to be retrieved for xml response
     * @param elementName    element to be located
     *                       All elements return string value, Need to typecast if required
     * @return element value
     */
    public String getXMLPathReturnValue(String validationType, String elementName) {
        String elementValue;
        XmlPath xmlPath = new XmlPath(getAPIResponseAsString());
        switch (validationType) {
            case "int":
                elementValue = String.valueOf(xmlPath.getInt(elementName));
                break;
            case "string":
                elementValue = xmlPath.getString(elementName);
                break;
            case "char":
                elementValue = String.valueOf(xmlPath.getChar(elementName));
                break;
            case "float":
                elementValue = String.valueOf(xmlPath.getFloat(elementName));
                break;
            case "double":
                elementValue = String.valueOf(xmlPath.getDouble(elementName));
                break;
            default:
                elementValue = null;
                break;
        }

        return elementValue;
    }

    /**
     * @param validationType  Data Type of elements to be retrieved from json path
     * @param elementName     element to be located
     * @param expectedResults expected result for tests
     *                        All elements return string value, Need to type case if required
     */
    public void hamcrestJsonPathValidation(String validationType, String elementName, String expectedResults) {
        JsonPath jsonPath = new JsonPath(getAPIResponseAsString());
        switch (validationType) {
            case "int":
                response.then().body(elementName, equalTo(String.valueOf(jsonPath.getInt(expectedResults))));
                break;
            case "string":
                response.then().body(elementName, equalTo(jsonPath.getString(expectedResults)));
                break;
            case "char":
                response.then().body(elementName, equalTo(String.valueOf(jsonPath.getChar(expectedResults))));
                break;
            case "float":
                response.then().body(elementName, equalTo(String.valueOf(jsonPath.getFloat(expectedResults))));
                break;
            case "double":
                response.then().body(elementName, equalTo(String.valueOf(jsonPath.getDouble(expectedResults))));
                break;
            default:
                break;
        }
    }

    /**
     * @param validationType Data Type of elements to be retrieved from xmlpath
     * @param elementName    element to be located
     *                       All elements return string value, Need to typecast if required
     */
    public void hamcrestXMLPathValidation(String validationType, String elementName, String expectedResults) {
        XmlPath xmlPath = new XmlPath(getAPIResponseAsString());
        switch (validationType) {
            case "int":
                response.then().body(elementName, equalTo(String.valueOf(xmlPath.getInt(expectedResults))));
                break;
            case "string":
                response.then().body(elementName, equalTo(xmlPath.getString(expectedResults)));
                break;
            case "char":
                response.then().body(elementName, equalTo(String.valueOf(xmlPath.getChar(expectedResults))));
                break;
            case "float":
                response.then().body(elementName, equalTo(String.valueOf(xmlPath.getFloat(expectedResults))));
                break;
            case "double":
                response.then().body(elementName, equalTo(String.valueOf(xmlPath.getDouble(expectedResults))));
                break;
            default:
                break;
        }
    }

    /**
     * @param elementName path to search for eg., result.id
     * @return map with list of items matched
     */
    public Map<String, String> getJsonwithMap(String elementName) {
        JsonPath jsonPath = new JsonPath(getAPIResponseAsString());
        return jsonPath.getMap(elementName);
    }

    /**
     * @return Time taken to response rest call
     */
    public long getResponseTime() {
        return response.time();
    }

    /*
     *To validate response object using contains method by passing element
     */
    public boolean getResponseObjectContains(String element) {
        return getAPIResponseAsPrettyPrint().contains(element);
    }

    /**
     * Returning element with Araylist
     *
     * @param jsonArrayName element to be searched and return in List with collections
     */
    public <T> T responseUsingPathWithArrayList(String jsonArrayName) {
        return response.path(jsonArrayName);
    }

    /**
     * @param xmlElementName
     * @return Return elements as list, this is just another way
     */
    public List<String> getListofElementValuesfromXML(String xmlElementName) {
        return XmlPath.from(getAPIResponseAsString()).getList(xmlElementName);
    }

    /**
     * @param elementOrAttribute Supports Groovy commands
     * @return return element or Atribute list
     */
    public List<Node> getXmlEleorAttrUsingXMLPathFrom(String elementOrAttribute) {
        return XmlPath.from(getAPIResponseAsString()).get(elementOrAttribute);
    }

    /**
     * To return response element as a list
     *
     * @param jsonArrayName supply ArrayName to return as list with Key and value pair
     */
    public <T> T extractMultipleMapsObject(String jsonArrayName) {
        return JsonPath.from(getAPIResponseAsString()).get(jsonArrayName);

    }

    /**
     * To return response message as a list
     * Can pass normal root.element as well as support groovy commands
     *
     * @param jsonArrayName supply ArrayName to return as a list
     */
    public List<String> responseUsingPathArrays(String jsonArrayName) {
        return response.path(jsonArrayName);
    }

    /*
   To get multiple elements using Json Path method
  * parameter can be passed like this "RestResponse.result.country" which returns country value
  * */
    public List<String> responseValJsonPathArrays(String jsonArrayName) {
        return new JsonPath(getAPIResponseAsString()).get(jsonArrayName);
    }

    /*
   This will return value for specific element
   It supports both XML and Json value
   For Json: rootnode.node as well as groovy commands
    */
    public String getSingleElementValue(String jsonArrayName) {
        return response.path(jsonArrayName);
    }

    public List<Map<String, String>> getXMLAttributesWithMap(String xmlElementOrAttribute) {
        return XmlPath.from(getAPIResponseAsString()).get(xmlElementOrAttribute);
    }

    /**
     * @param elementName path to search for eg., result.id
     * @return list of elements returns matches id
     */
    public List<String> getJsonElementList(String elementName) {
        JsonPath jsonPath = new JsonPath((getAPIResponseAsString()));
        return jsonPath.getList(elementName);
    }

    /**
     * @param elementName path to search for eg., result.id
     * @return list of elements return matches id
     */
    public List<String> getXMLElementList(String elementName) {
        XmlPath xmlPath = new XmlPath(getAPIResponseAsString());
        return xmlPath.getList(elementName);
    }

    /**
     * @param elementName path to search for eg., result.id
     * @return list of elements return matches id
     */
    public Map<String, String> getXMLElementWithMap(String elementName) {
        XmlPath xmlPath = new XmlPath(getAPIResponseAsString());
        return xmlPath.getMap(elementName);
    }

}
