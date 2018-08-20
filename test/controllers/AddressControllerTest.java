package controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.ebeaninternal.api.HelpScopeTrans;
import models.db.PatientAddress;
import org.junit.Assert.*;
import org.junit.*;
import play.Application;
import play.DefaultApplication;
import play.Logger;
import play.core.j.JavaHelpers;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.test.*;
import static play.test.Helpers.*;
import play.mvc.*;
import play.mvc.Http.RequestBuilder;
import utils.AppUtil;
import play.http.*;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import play.mvc.Results;
import play.core.j.JavaResultExtractor;
public class AddressControllerTest extends WithApplication {

    @Inject
    Application application;
    public AddressControllerTest(){
    }

    @Test
    public void testGetPatientMissing(){
        RequestBuilder request = Helpers.fakeRequest().method(GET).uri("/address/-1");
        Result result = route(app, request);
        Assert.assertEquals("application/json", result.body().contentType().get());
        Assert.assertEquals(
                Results.ok(
                        (new AppUtil()).getSuccessObject(Json.toJson(new ArrayList<PatientAddress>()))).body().contentLength(),
                result.body().contentLength()
        );
    }
    @BodyParser.Of(BodyParser.Text.class)
    @Test
    public void addPatientTest(){
        //This test will always fail because our class fields and JSON fields don't match
        //change class variables or json field names
        RequestBuilder request = Helpers.fakeRequest().method(POST).uri("/address");
        request = request.header("patient_id", "314159");
        JsonNode patientJson = Json.parse("{\"enabled\":true,\"patient_id\":3145,\"nickname\":\"Roshni\",\"province\":\"Ramapuram\",\"postal_code\":\"600089\",\"street_address\":\"Homefinders\",\"city\":\"Chennai\",\"country\":\"IN\"}");
        request = request.bodyJson(patientJson);
        Result result = route(app, request);
        Assert.assertEquals(Http.Status.OK, result.status());
        Assert.assertEquals(
                patientJson.toString(),
                Json.toJson(PatientAddress.find.nativeSql(
                        "select * from patient_address where patient_id=" + 314159
                ).findOne()).toString()
        );

    }


}
