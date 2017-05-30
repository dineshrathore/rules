package com.dinesh.test;

import com.dinesh.test.domain.*;
import org.mvel2.MVEL;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dinesh.rathore on 30/05/17.
 */
public class MVELTest {
    public static void main(String[] args) {
        Facilities facilities = new Facilities();
        facilities.addFacility(1L,"KML", FacilityType.DELIVERY_HUB, "KML","BLR","KA","SOUTH","560034");
        facilities.addFacility(2L,"KMLP", FacilityType.PICKUP_HUB, "KML","BLR","KA",null,"560034");
        facilities.addFacility(3L,"HSR", FacilityType.DELIVERY_HUB, "HSR","BLR","KA","SOUTH","560068");
        facilities.addFacility(4L,"HSRP", FacilityType.PICKUP_HUB, "HSRP","BLR","KA","SOUTH","560068");
        facilities.addFacility(5L,"BLRMH", FacilityType.MOTHER_HUB, "BLRMH","BLR","KA","SOUTH","560067");

        PincodeServiceability pincodeServiceability = new PincodeServiceability();
        pincodeServiceability.addOrUpdateServiceability("560034", BusinessTask.RECEIVE_TO_DELIVER,1L);
        pincodeServiceability.addOrUpdateServiceability("560033", BusinessTask.RECEIVE_TO_DELIVER,1L);
        pincodeServiceability.addOrUpdateServiceability("560068", BusinessTask.RECEIVE_TO_DELIVER,3L);
        pincodeServiceability.addOrUpdateServiceability("560069", BusinessTask.RECEIVE_TO_DELIVER,3L);
        pincodeServiceability.addOrUpdateServiceability("560034", BusinessTask.PICK_FROM_SELLER,2L);
        pincodeServiceability.addOrUpdateServiceability("560033", BusinessTask.PICK_FROM_SELLER,2L);
        pincodeServiceability.addOrUpdateServiceability("560068", BusinessTask.PICK_FROM_SELLER,4L);
        pincodeServiceability.addOrUpdateServiceability("560069", BusinessTask.PICK_FROM_SELLER,4L);

        PhMhMapping phMhMapping = new PhMhMapping();
        phMhMapping.addOrUpdateMapping(2L,5L);
        phMhMapping.addOrUpdateMapping(4L,5L);


        Serviceability serviceability = new Serviceability();
        serviceability.addOrUpdatePickupServiceability("560034", "560067");
        serviceability.addOrUpdatePickupServiceability("560033", "560067");
        serviceability.addOrUpdatePickupServiceability("560068", "560067");
        serviceability.addOrUpdatePickupServiceability("560069", "560067");


        {
            long startTime = System.currentTimeMillis();
            for (Facility facility : facilities.getAllFacilities()) {
                Map<String, Object> input = new HashMap<String, Object>();
                input.put("facility", facility);

                try {
                    System.out.println("Facility "+ facility.getId() + ":" + MVEL.evalFile(new File("/Users/dinesh.rathore/flipkart/e2e/rules/src/main/resources/facility_single.mvel"), input));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);

        }
        {
            long startTime = System.currentTimeMillis();
            Map<String, Object> input = new HashMap<String, Object>();
            input.put("facilities", facilities);

            try {
                System.out.println("Test facilities: Invalid facilities:" + MVEL.evalFile(new File("/Users/dinesh.rathore/flipkart/e2e/rules/src/main/resources/facility_multiple.mvel"), input));
            } catch (IOException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }



        {
            long startTime = System.currentTimeMillis();
            for (Map.Entry<String,Map<BusinessTask,Long>> entry : pincodeServiceability.getServiceabilities().entrySet()) {
                for(Map.Entry<BusinessTask,Long> entry1 : entry.getValue().entrySet()){
                    Map<String, Object> input = new HashMap<String, Object>();
                    input.put("facilities", facilities);
                    input.put("pincode", entry.getKey());
                    input.put("task", entry1.getKey());
                    input.put("hub", entry1.getValue());

                    try {
                        System.out.println("Serviceability:"+ entry.getKey() +"," + entry1.getKey()+ "," + entry1.getValue() +":" + MVEL.evalFile(new File("/Users/dinesh.rathore/flipkart/e2e/rules/src/main/resources/pincode_serviceability_single.mvel"), input));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);

        }


        {
            long startTime = System.currentTimeMillis();
            for (Map.Entry<Long,Long> entry : phMhMapping.getMappings().entrySet()) {
                    Map<String, Object> input = new HashMap<String, Object>();
                    input.put("facilities", facilities);
                    input.put("ph", entry.getKey());
                    input.put("mh", entry.getValue());

                    try {
                        System.out.println("PH-MH:" + entry.getKey()+ "," + entry.getValue() +":" + MVEL.evalFile(new File("/Users/dinesh.rathore/flipkart/e2e/rules/src/main/resources/ph_mh_single.mvel"), input));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);

        }

        {
            long startTime = System.currentTimeMillis();
            for (Map.Entry<String,String> entry : serviceability.getPromiseServiceability().entrySet()) {
                Map<String, Object> input = new HashMap<String, Object>();
                input.put("sourcePincode", entry.getKey());
                input.put("destinationPincode", entry.getValue());
                input.put("businessTask", BusinessTask.PICK_FROM_SELLER);


                input.put("facilities", facilities);
                input.put("pincodeServiceability", pincodeServiceability);
                input.put("phMhMapping", phMhMapping);

                try {
                    System.out.println("Serviceability:" + entry.getKey()+ "," + entry.getValue() +":" + MVEL.evalFile(new File("/Users/dinesh.rathore/flipkart/e2e/rules/src/main/resources/serviceability_single.mvel"), input));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);

        }




    }
}
