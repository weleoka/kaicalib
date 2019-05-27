package se.ltu.kaicalib.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ltu.kaicalib.core.domain.entities.Copy;
import se.ltu.kaicalib.core.repository.CopyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Facilitates easy retrieveal of copy objects for the use in
 * higher layers, mostly controllers.
 *
 *
 * @author
 */
@Transactional(value = "coreTransactionManager")
@Service
public class CopyService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private CopyRepository copyRepository;


    @Autowired
    public CopyService(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }


    @Transactional(value = "coreTransactionManager", readOnly = true)
    public Copy findCopyById(Long id) {
        Copy copy = null;

        try {
            Optional<Copy> tmpOpt = copyRepository.findById(id);

            if (tmpOpt.isPresent()) {
                copy = tmpOpt.get();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }

        return copy;
    }


    @Transactional(value = "coreTransactionManager", readOnly = true)
    public List<Copy> getAllCopiesByIdList(List<Long> idList) {
        List<Copy> tmpList = new ArrayList<>();

        for (Long id : idList) {
            tmpList.add(findCopyById(id));
        }
        logger.debug("Fetched {} Loans from db into a list.", tmpList.size());

        return tmpList;
    }
}
