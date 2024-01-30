package com.details.service.impl;

import com.details.dao.IRetailsDAO;
import com.details.dto.ActDTO;
import com.details.dto.ActRandomDTO;
import com.details.service.IRetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RetailsService implements IRetailsService {

    @Autowired
    private IRetailsDAO retailsDAO;

    @Override
    public ActDTO getDetail(Integer actId) {
        return retailsDAO.getDetail(actId);
    }

    @Override
    public List<ActRandomDTO> randomFourAct(Integer actTypeId, Integer actId) {

        List<ActRandomDTO> actRandomList = retailsDAO.randomFourAct(actTypeId, actId);
        List<ActRandomDTO> fourRandomList = new ArrayList<>();

        Collections.shuffle(actRandomList);

        for (int i = 0; i < 4; i++) {
            try {
                fourRandomList.add(actRandomList.get(i));
            } catch (IndexOutOfBoundsException e) { //超過索引就不再add 直接return
                return fourRandomList;
            }
        }
        return fourRandomList;
    }

}
