package com.dinesh.test.domain;

import lombok.*;

/**
 * Created by dinesh.rathore on 30/05/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Facility {
    private Long id;
    private String name;
    private FacilityType type;
    private String code;
    private String cluster;
    private String circle;
    private String zone;
    private String pincode;

}