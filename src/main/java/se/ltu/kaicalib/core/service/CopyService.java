package se.ltu.kaicalib.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ltu.kaicalib.core.repository.CopyRepository;

@Service
public class CopyService {

    CopyRepository copyRepository;

    @Autowired
    CopyService(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

}
