package com.gfdz.springdata.service;

import com.gfdz.springdata.repsotory.PersonRepsotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2015/12/11.
 */
@Service
public class PersionService {
    @Autowired
    private PersonRepsotory personRepsotory;
    @Transactional
    public void updatePersion(String email,Integer id){
        personRepsotory.updatePeson(email,id);
    }
}
