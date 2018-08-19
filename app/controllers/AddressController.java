package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.db.PatientAddress;
import models.db.dao.PatientAddressDaoImpl;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utils.AppUtil;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AddressController extends Controller {



    private HttpExecutionContext httpExecutionContext;
    @Inject
    public AddressController(HttpExecutionContext httpExecutionContext){
        this.httpExecutionContext = httpExecutionContext;
    }
    /**
     * Retrieves the List of addresses which are enabled corresponding to the patientId
     * (presented in the header as patient_id)
     * present in the header
     *
     * We need to return only those addresses which have enabled flag set
     * as True {@see models.db.BaseModel#enabled}
     *
     *
     * The resultant JSON would look like:
     * {
     *     "success": true,
     *     "addresses": [
     *          {
     *              "nickname": "John Doe",
     *              "province": "Manitoba",
     *              "postal_code": "V0S0B1",
     *              "street_address": "410, Hasbrouck, Gali number 37, Sector 4",
     *              "city": "Toronto",
     *          },
     *          {
     *              "nickname": "Batman",
     *              ...
     *          },
     *          ...
     *     ]
     * }
     * The addresses are given in the table
     * @see models.db.PatientAddress
     *
     * @return List<Address> encapsulated in
     * @see utils.AppUtil#getSuccessObject(com.fasterxml.jackson.databind.JsonNode) if successful or
     * @see utils.AppUtil#getBadRequestObject(String) if unsuccessful
     */
    public CompletionStage<Result> getPatientAddress(long patientId) {

       // return CompletableFuture.completedFuture(ok(Json.toJson(PatientAddress.find.all())));

        CompletionStage<Result> res;
        try{
            res = (new PatientAddressDaoImpl().getPatientAddress(patientId)).thenApplyAsync(
                    ans -> {
                        System.out.println(ans.size() + " Elements Present ");
                        return ok(new AppUtil().getSuccessObject(Json.toJson(ans)));
                    }
            , this.httpExecutionContext.current());
        }
        catch(Exception e){
            res =  CompletableFuture.completedFuture(
                    ok(
                            new AppUtil().getBadRequestObject(e.getMessage().toString())
                    )
            );
        }

        return res;

    }

    /**
     * @see models.db.PatientAddress and the body are bound together.
     * The json body will look like as follows:
     * {
     *     "nickname": "John Doe",
     *     "province": "Manitoba",
     *     "postal_code": "V0S0B1",
     *     "street_address": "410, Hasbrouck, Gali number 37, Sector 4",
     *     "city": "Toronto",
     * }
     * The patientId is present in the header (presented in the header as patient_id)
     *
     * This object is saved in the table with enabled as True
     *
     * @return json with the id of address encapsulated in
     * @see utils.AppUtil#getSuccessObject(com.fasterxml.jackson.databind.JsonNode) if successfull
     * @see utils.AppUtil#getBadRequestObject(String) if unsuccessfull
     */
    public CompletionStage<Result> addPatientAddress() {
        System.out.println("Data Recieved " + request().body().asJson().toString());
        CompletionStage<Result> res;
        try{

            Long patientId = new Long(request().header("patient_id").get());
            /*ObjectNode jsonData = (ObjectNode) request().body().asJson();
            jsonData.put("patient_id", patientId);
            System.out.println("now we have " + (JsonNode)jsonData);
           //// PatientAddress patientToAdd = Json.fromJson((JsonNode)jsonData, PatientAddress.class);
            ///// cannot directly construct from json because properties differenct from JSON attributes
            */
            PatientAddress patientToAdd = new PatientAddress(
                            patientId,
                            request().body().asJson().get("nickname").asText(),
                            request().body().asJson().get("province").asText(),
                            request().body().asJson().get("postal_code").asText(),
                            request().body().asJson().get("street_address").asText(),
                            request().body().asJson().get("city").asText(),
                            request().body().asJson().get("country").asText()
                    );


            patientToAdd.setPatientId(patientId);
            res = (new PatientAddressDaoImpl().addPatientAddress(patientId, patientToAdd))
                    .thenApplyAsync(
                            insertionId ->{
                                System.out.println(patientId + " was at " + insertionId);
                                if(insertionId != -1)
                                    return ok(insertionId.toString());
                                else
                                    return internalServerError("Record Not Inserted");
                            }
                   ,this.httpExecutionContext.current() );

        }
        catch(Exception e){
            System.out.println("Exception Occurred " + e);
            res = CompletableFuture.completedFuture(internalServerError(e.getMessage()));
        }
        return res;
    }


    /**
     * @see models.db.PatientAddress entry is deleted corresponding to the {@code addressId}
     * For deleting a particular address, we just set the
     * enabled flag as false {@see models.db.BaseModel#enabled}
     *
     * @param addressId which we need to delete
     * @return json denoting success or failure encapsulated in
     * @see utils.AppUtil#getSuccessObject(com.fasterxml.jackson.databind.JsonNode)  if successfully deleted
     * @see utils.AppUtil#getBadRequestObject(String) if unsuccessfull
     */
    public CompletionStage<Result> deletePatientAddress(long addressId) {
        CompletionStage<Result> res;
        try {
            res = (new PatientAddressDaoImpl().deletePatientAddress(addressId)).thenApplyAsync(
                    ans -> {
                        if (ans == true)
                            return ok("true");
                        else
                            return internalServerError("false");
                    }
                    , this.httpExecutionContext.current());
        }
        catch(Exception e){
               return CompletableFuture.completedFuture(internalServerError(e.getMessage()));
        }
        return res;

    }


}
