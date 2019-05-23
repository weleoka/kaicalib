package se.ltu.kaicalib.core.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.ltu.kaicalib.core.domain.entities.Title;
import se.ltu.kaicalib.core.repository.TitleRepository;

@ControllerAdvice
@RequestMapping(path = "/librarian_home/add_title")
@SessionAttributes("title")
//@Secured("ADMIN")
public class AddTitleController {

    private TitleRepository titleRepository;

    @Autowired
    public AddTitleController(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @GetMapping
    public String addTitle(Model model) {
        model.addAttribute("title", new Title());
        return "core/addTitleForm";
    }

    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("titles", titleRepository.findAll());
        return "redirect:/";
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public String postTitle( @ModelAttribute Title title) {
        //TODO Return date logic goes here
        //title.setRetDate(LocalDate.now().plusMonths(2));
        //title.setStatus("available");
        titleRepository.save(title);

        return "redirect:/librarian_home/add_title";
    }

    @PostMapping("/redirect")
    public String redirect() {
        return "redirect:/search_add_copy";
    }

}
