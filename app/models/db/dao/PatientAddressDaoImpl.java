package models.db.dao;

import models.db.PatientAddress;
import models.db.dao.interfaces.PatientAddressDao;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class PatientAddressDaoImpl implements PatientAddressDao {
    @Override
    public CompletionStage<List<PatientAddress>> getPatientAddress(long patientId) {
        return CompletableFuture.completedFuture(PatientAddress.FindAllById(patientId));
    }

    @Override
    public CompletionStage<Boolean> deletePatientAddress(long addressId) {
        try {
            PatientAddress patient = PatientAddress.find.nativeSql("select * from patient_address where id="+addressId).findOne();
            System.out.println(patient.getNickname());
            patient.setEnabled(false);
            patient.update();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return CompletableFuture.completedFuture(false);
        }
        return CompletableFuture.completedFuture(true);

    }


    @Override
    public CompletionStage<Long> addPatientAddress(long patientId, PatientAddress address) {
        address.save();
        return CompletableFuture.completedFuture(address.getId());
    }
}
