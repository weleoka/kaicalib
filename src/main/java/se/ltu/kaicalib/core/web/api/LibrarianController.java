package se.ltu.kaicalib.core.web.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.ltu.kaicalib.core.domain.entities.Copy;
import se.ltu.kaicalib.core.domain.entities.Title;
import se.ltu.kaicalib.core.repository.CopyRepository;
import se.ltu.kaicalib.core.repository.TitleRepository;

import static se.ltu.kaicalib.core.utils.GenUtil.toArr;


/**
 * A controller handling the routes that an admin, i.e. librarian would
 * require in order to interact with the system.
 *
 * The whole program infrastructure from here and onwards is not dependable, nor complete.
 *
 * todo work
 *
 */
@Controller
@Secured("ADMIN")
public class LibrarianController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private CopyRepository copyRepository;
    private TitleRepository titleRepository;
    private MessageSource messageSource;


    @Autowired
    public LibrarianController(
        CopyRepository copyRepository,
        TitleRepository titleRepository,
        MessageSource messageSource) {
        this.copyRepository = copyRepository;
        this.titleRepository = titleRepository;
        this.messageSource = messageSource;
    }


    @GetMapping("admin/add_title")
    public String viewAddTitle(Model model) {
        model.addAttribute("title", new Title());

        return "core/admin/add_title";
    }


    @PostMapping("/admin/add_title")
    public String operationAddTitle(Model model, @ModelAttribute Title title) {
        titleRepository.save(title);
        String msg = messageSource.getMessage("Admin.title.added.success", toArr(title.getName()), null);
        model.addAttribute("success", msg);

        return "redirect:/admin/profile";
    }


    @GetMapping("/admin/add_copy")
    public String viewAddCopy(Model model) {
        model.addAttribute("copy", new Copy());
        //todo here we fill with the search later
        model.addAttribute("title", new Title());

        return "core/admin/add_copy";
    }


    @PostMapping("/admin/add_copy")
    public String operationAddCopy(Model model, @ModelAttribute Copy copy) {
        copyRepository.save(copy);
        String msg = messageSource.getMessage("Admin.copy.added.success", toArr(copy.getId().toString()), null);
        model.addAttribute("success", msg);

        return "redirect:/admin/profile";
    }

/*    @PostMapping
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
    }*/
}
