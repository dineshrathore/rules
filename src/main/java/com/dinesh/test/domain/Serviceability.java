package com.dinesh.test.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dinesh.rathore on 30/05/17.
 */

@Setter
@Getter
@ToString
public class Serviceability {
    private Map<String,String> promiseServiceability = new HashMap<String, String>();

    public String getPickupServiceability(String sourcePincode){
        return promiseServiceability.get(sourcePincode);
    }

    public void addOrUpdatePickupServiceability(String sourcePincode, String destPincode){
        promiseServiceability.put(sourcePincode,destPincode);
    }

    public String removePickupServiceability(String sourcePincode){
        return promiseServiceability.remove(sourcePincode);
    }

}
