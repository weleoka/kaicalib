package se.ltu.kaicalib.core.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.ltu.kaicalib.core.domain.Title;
import se.ltu.kaicalib.core.domain.TitleSearchFormCommand;
import se.ltu.kaicalib.core.repository.TitleRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/patron/patron_search")
// @Secured("PATRON") // todo CLEAR the auth level on this controller
public class PatronSearchController {
    /*
    todo is the search neccessarily a patron "thing"? Cant adding the copy to basket be a patron thing?
     */

    TitleRepository titleRepository;
    List<Title> titles = new ArrayList<>();

    @Autowired
    public PatronSearchController(TitleRepository titleRepository) {
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

        return "core/patronSearchTitleForm";
    }

    @PostMapping
    public String showSearchResult(
            @ModelAttribute("command") TitleSearchFormCommand command, Model model) {

        titles.clear();

        for (Title t : titleRepository.findFirst20ByNameContaining(command.getTitleSearchString())) {

            if (!(titles.contains(t))) {
                titles.add(t);
            }
        }

        model.addAttribute("titles", titles);

        return "redirect:/display_search_results";
    }

    @GetMapping("/display_search_results")
    public String displaySearchResult(Model model) {

        model.addAttribute("titles", titles);

        return "core/patronSearchResult";
    }
}
