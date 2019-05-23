package se.ltu.kaicalib.core.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import se.ltu.kaicalib.core.domain.entities.Title;
import se.ltu.kaicalib.core.repository.CopyRepository;
import se.ltu.kaicalib.core.repository.TitleRepository;

import java.util.List;

@ControllerAdvice
@RequestMapping(path = "/search_add")
public class ExpController {

    private CopyRepository copyRepository;
    private TitleRepository titleRepository;

    @Autowired
    public ExpController(CopyRepository copyRepository, TitleRepository titleRepository) {
        this.copyRepository = copyRepository;
        this.titleRepository = titleRepository;
    }

    @GetMapping("/search_add")
    public List<Title> titleByTitleName(@PathVariable("name") String name) {
        List<Title> titles = titleRepository.findFirst20ByNameContaining(name);

        return titles;
    }

}
