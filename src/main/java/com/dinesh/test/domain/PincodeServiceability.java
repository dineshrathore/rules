package com.dinesh.test.domain;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dinesh.rathore on 30/05/17.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PincodeServiceability {
    private Map<String,Map<BusinessTask,Long>> serviceabilities = new HashMap<String, Map<BusinessTask, Long>>();

    public void addOrUpdateServiceability(String pincode,BusinessTask businessTask,Long facilityId){
        if(!serviceabilities.containsKey(pincode)){
            serviceabilities.put(pincode,new HashMap<BusinessTask, Long>());
        }
        serviceabilities.get(pincode).put(businessTask,facilityId);
    }


    public Long getServiceability(String pincode,BusinessTask businessTask){
        if(!serviceabilities.containsKey(pincode)){
            return null;
        }
        return serviceabilities.get(pincode).get(businessTask);
    }
}
