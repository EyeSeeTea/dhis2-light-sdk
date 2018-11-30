package org.eyeseetea.dhis2.lightsdk;

import static org.junit.Assert.fail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.eyeseetea.dhis2.lightsdk.common.FileReader;
import org.eyeseetea.dhis2.lightsdk.common.MockD2ServerRule;
import org.eyeseetea.dhis2.lightsdk.common.TestExtensionsKt;
import org.eyeseetea.dhis2.lightsdk.common.models.D2CollectionResponse;
import org.eyeseetea.dhis2.lightsdk.optionsets.OptionSet;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

public class JavaExamples {

    private static final String OPTION_SETS_FILE = "OptionSets.json";
    private static final String UNAUTHORIZED_FILE = "Error401.html";
    private static final String INTERNAL_SERVER_ERROR_FILE = "Error500.json";

    @Rule
    public MockD2ServerRule mockD2ServerRule = new MockD2ServerRule(new FileReader());

    @Test
    public void return_expected_optionSets() {
        List<OptionSet> expectedOptionSets = givenExpectedOptionSets();

        mockD2ServerRule.enqueueFileMockResponse(200, OPTION_SETS_FILE);

        D2Api d2Api = givenD2Api();

        D2Response response = d2Api.optionSets().getAll();

        if (response.isSuccess()) {
            D2Response.Success<List<OptionSet>> success =
                    (D2Response.Success<List<OptionSet>>) response;

            Assert.assertEquals(success.getValue(), expectedOptionSets);
        } else {
            fail();
        }
    }

    @Test
    public void return_unauthorized_error_response() {
        mockD2ServerRule.enqueueFileMockResponse(401, UNAUTHORIZED_FILE);

        D2Api d2Api = givenD2Api();

        D2Response response = d2Api.optionSets().getAll();

        if (response.isError() && response instanceof D2Response.Error.HttpError) {
            D2Response.Error.HttpError errorResponse = (D2Response.Error.HttpError) response;

            Assert.assertEquals(errorResponse.getHttpStatusCode(), 401);
        } else {
            fail();
        }
    }

    @Test
    public void return_internal_server_error_response() {
        mockD2ServerRule.enqueueFileMockResponse(500, INTERNAL_SERVER_ERROR_FILE);

        D2Api d2Api = givenD2Api();

        D2Response response = d2Api.optionSets().getAll();

        if (response.isError() && response instanceof D2Response.Error.HttpError) {
            D2Response.Error.HttpError errorResponse = (D2Response.Error.HttpError) response;

            Assert.assertEquals(errorResponse.getHttpStatusCode(), 500);
        } else {
            fail();
        }
    }

    private List<OptionSet> givenExpectedOptionSets() {
        Gson d2Gson = TestExtensionsKt.createD2Gson(new GsonBuilder());

        D2CollectionResponse<OptionSet> expectedResponse = fromFile(d2Gson, OPTION_SETS_FILE);

        return expectedResponse.getItems();
    }

    private D2Api givenD2Api() {
        return new D2Api.Builder()
                .url(mockD2ServerRule.getBaseEndpoint())
                .credentials("some credentials", "some pasword")
                .build();
    }

    private <T> T fromFile(Gson d2Gson, String filename) {
        String jsonString = new FileReader().getStringFromFile(filename);

        Type listType = new TypeToken<D2CollectionResponse<OptionSet>>() {
        }.getType();

        return d2Gson.fromJson(jsonString, listType);
    }
}
