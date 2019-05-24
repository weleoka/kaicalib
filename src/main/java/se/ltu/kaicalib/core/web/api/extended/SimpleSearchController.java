package se.ltu.kaicalib.core.web.api.extended;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.ltu.kaicalib.core.domain.TitleSearchFormCommand;
import se.ltu.kaicalib.core.domain.entities.Title;
import se.ltu.kaicalib.core.repository.TitleRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "simple_search")
public class SimpleSearchController {

    TitleRepository titleRepository;
    List<Title> titles = new ArrayList<>();

    @Autowired
    public SimpleSearchController(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @ModelAttribute(name = "titles")
    public List<Title> getTitles() {
        List<Title> titles = new ArrayList<>();
        return titles;
    }

    @GetMapping
    public String showSearch(Model model) {
        TitleSearchFormCommand command = new TitleSearchFormCommand();

        model.addAttribute("command", command);

        return "core/simpleSearchTitleForm";
    }

    @PostMapping
    public String showSearchResult(
            @ModelAttribute("command") TitleSearchFormCommand command, Model model) {
        for (Title t : titleRepository.findFirst20ByNameContaining(command.getTitleSearchString())) {
            titles.add(t);
        }

        model.addAttribute("titles", titles);

        return "redirect:/simple_search/display_search_results";
    }

    @GetMapping("/display_search_results")
    public String displaySearchResult(Model model) {

        model.addAttribute("titles", titles);

        return "core/simpleSearchResult";
    }
}
