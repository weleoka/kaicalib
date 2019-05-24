package se.ltu.kaicalib.core.web.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.ltu.kaicalib.core.domain.entities.Copy;
import se.ltu.kaicalib.core.domain.entities.CopyType;
import se.ltu.kaicalib.core.domain.entities.NormalCopyType;
import se.ltu.kaicalib.core.domain.entities.Title;
import se.ltu.kaicalib.core.repository.CopyRepository;
import se.ltu.kaicalib.core.repository.TitleRepository;

import java.time.LocalDate;


/**
 * A controller handling the routes that an admin, i.e. librarian would
 * require in order to interact with the system.
 *
 * The whole program infrastructure from here and onwards is not dependable, nor complete.
 *
 * todo work
 */
@Controller
@Secured("ADMIN")
public class LibrarianController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private CopyRepository copyRepository;
    private TitleRepository titleRepository;


    @Autowired
    public LibrarianController(
        CopyRepository copyRepository,
        TitleRepository titleRepository) {
        this.copyRepository = copyRepository;
        this.titleRepository = titleRepository;
    }


    @GetMapping("admin/add_title")
    public String addTitle(Model model) {
        model.addAttribute("title", new Title());

        return "core/admin/add_title";
    }


    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("titles", titleRepository.findAll());

        return "core/search_results";
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public String postTitle( @ModelAttribute Title title) {
        //TODO Return date logic goes here
        //title.setRetDate(LocalDate.now().plusMonths(2));
        //title.setStatus("available");
        titleRepository.save(title);

        return "redirect:/admin/add_title";
    }

    @PostMapping("/redirect")
    public String redirect() {
        return "redirect:/search_add_copy";
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
