package com.dinesh.test.domain;

import lombok.*;
import sun.rmi.runtime.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dinesh.rathore on 30/05/17.
 */
@ToString
public class Facilities {

    private Map<Long,Facility> fs = new HashMap<Long, Facility>();

    public Facility addFacility(Long id, String name,FacilityType type, String code, String cluster, String circle, String zone, String pincode){
        Facility facility = new Facility(id,name,type,code,cluster,circle,zone,pincode);
        fs.put(id,facility);
        return facility;
    }

    public Collection<Facility> getAllFacilities(){
        return  fs.values();
    }

    public Facility getById(Long id){
        return fs.get(id);
    }

}