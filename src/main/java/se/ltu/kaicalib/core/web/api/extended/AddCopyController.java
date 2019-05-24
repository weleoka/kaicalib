package se.ltu.kaicalib.core.web.api.extended;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.ltu.kaicalib.core.domain.entities.Copy;
import se.ltu.kaicalib.core.domain.entities.CopyType;
import se.ltu.kaicalib.core.domain.entities.NormalCopyType;
import se.ltu.kaicalib.core.domain.entities.Title;
import se.ltu.kaicalib.core.repository.CopyRepository;
import se.ltu.kaicalib.core.repository.CopyTypeRepository;
import se.ltu.kaicalib.core.repository.TitleRepository;

import java.time.LocalDate;

@ControllerAdvice
@RequestMapping(path = "/add_copy")
//@Secured("ADMIN")
public class AddCopyController {

    private CopyRepository copyRepository;
    private TitleRepository titleRepository;
    private CopyTypeRepository copyTypeRepository;

    @Autowired
    public AddCopyController(CopyRepository copyRepository, TitleRepository titleRepository) {
        this.copyRepository = copyRepository;
        this.titleRepository = titleRepository;
    }

    @ModelAttribute("title")
    public Title getTitle() {
        //TODO replace with proper text search
        Title title = null;

        return title;
    }

    @GetMapping
    public String addCopy(Model model) {
        model.addAttribute("copy", new Copy());
        //todo here we fill with the search later
        model.addAttribute("title", new Title());

        return "core/addCopyForm";
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public String postCopy( @ModelAttribute("copy") Copy copy, @ModelAttribute("title") Title title) {
        title = titleRepository.getOne(1l);
        //TODO needs to search by title, or id or smthn. Logic could become somewhat complex,
        // using placeholder method of using known, static, ID for now
        copy.setTitle(title);
        copy.setStatus("available");
        //TODO PLACEHOLDER, move out of copy?
        copy.setRetDate(LocalDate.now());

        //TODO STATIC CREATION
        CopyType copyType = new NormalCopyType(copy);

        copy.setCopyType(copyType);
        //TODO add the CopyType objects as well
        copyRepository.save(copy);

        return "redirect:/add_copy";
    }

}
