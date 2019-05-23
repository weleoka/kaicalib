package se.ltu.kaicalib.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ltu.kaicalib.core.domain.entities.Title;
import se.ltu.kaicalib.core.repository.TitleRepository;

import java.util.List;
import java.util.Optional;

@Transactional(value = "coreTransactionManager")
@Service
public class TitleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TitleRepository titleRepository;

    public List<Title> findByTitle (String str) {
        return titleRepository.findFirst20ByNameContaining(str);
    }


    @Transactional(value = "coreTransactionManager", readOnly = true) // todo check on method Vs. class annotation for transactional.
    public Title findById(Long id) {
        Title title = null; // = titleRepository.findById(id);

        try {
            Optional<Title> tmpOpt = titleRepository.findById(id);

            if (tmpOpt.isPresent()) {
                title = tmpOpt.get();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return title;
    }


}
