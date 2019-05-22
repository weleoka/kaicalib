package se.ltu.kaicalib.core.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.ltu.kaicalib.core.domain.Copy;
import se.ltu.kaicalib.core.domain.Title;
import se.ltu.kaicalib.core.domain.TitleSearchFormCommand;
import se.ltu.kaicalib.core.repository.CopyRepository;
import se.ltu.kaicalib.core.service.CopyService;
import se.ltu.kaicalib.core.service.TitleService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    TitleService titleService;
    CopyService copyService;
    CopyRepository copyRepository; // Yes going controller to repository is considered fine
    List<Title> titles = new ArrayList<>();

    @Autowired
    public SearchController(TitleService titleService, CopyService copyService, CopyRepository copyRepository) {
        this.titleService = titleService;
        this.copyService = copyService;
        this.copyRepository = copyRepository;
    }


    @GetMapping("/search")
    public String generalSearchPage(Model model) {
        TitleSearchFormCommand command = new TitleSearchFormCommand();
        model.addAttribute("command", command);

        return "core/search";
    }


    @PostMapping("/search_results")
    public String showSearchResult(@ModelAttribute("command") TitleSearchFormCommand command, Model model) {
        titles.clear();

        for (Title t : titleService.findByTitle(command.getTitleSearchString())) {

            if (!(titles.contains(t))) {
                titles.add(t);
            }
        }
        logger.error("Debug: no Titles");
        model.addAttribute("titles", titles);

        return "redirect:/search_results";
    }


    @GetMapping("/search_results")
    public String showSearchResultsBlank(Model model) {
        model.addAttribute("titles", titles);

        return "core/search_results";
    }


    //@RequestAttribute(title) // todo Could use the new annotation and pass entire object...
    @GetMapping("/search_selection")
    public String generalSearchResultsPage(@RequestParam("id") Long id, Model model) {
        Title title = titleService.findById(id);
        List<Copy> copies = copyRepository.findAllAvailableCopiesByTitle(title);
        logger.debug("Fount a title and a few copies");
        model.addAttribute("title", title);
        model.addAttribute("copies", copies);

        return "core/search_selection";
    }
}

