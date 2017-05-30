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
public class PhMhMapping {
    private Map<Long,Long> mappings = new HashMap<Long, Long>();

    public Long getMHForPH(Long ph){
        return mappings.get(ph);
    }

    public void addOrUpdateMapping(Long ph, Long mh){
        mappings.put(ph,mh);
    }

    public Long removeMHForPH(Long ph){
        return mappings.remove(ph);
    }



}
