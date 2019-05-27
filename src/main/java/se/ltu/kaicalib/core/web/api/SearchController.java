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
import se.ltu.kaicalib.core.domain.TitleSearchFormCommand;
import se.ltu.kaicalib.core.domain.entities.Copy;
import se.ltu.kaicalib.core.domain.entities.Title;
import se.ltu.kaicalib.core.repository.CopyRepository;
import se.ltu.kaicalib.core.service.TitleService;

import java.util.ArrayList;
import java.util.List;


/**
 * This controller covers routes concerning implementing searches,
 * displaying search results and also receiving search selections.
 *
 *
 * @author
 */
@Controller
public class SearchController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private CopyRepository copyRepository;
    private TitleService titleService;
    private List<Title> titles = new ArrayList<>();


    @Autowired
    public SearchController(
        CopyRepository copyRepository,
        TitleService titleService) {
        this.copyRepository = copyRepository;
        this.titleService = titleService;
    }


    @GetMapping("/search")
    public String generalSearchPage(Model model) {
        TitleSearchFormCommand command = new TitleSearchFormCommand();
        model.addAttribute("command", command);

        return "core/search";
    }


    @GetMapping("/search_results")
    public String showSearchResultsBlank(Model model) {
        model.addAttribute("titles", titles);

        return "core/search_results";
    }


    @PostMapping("/search_results")
    public String showSearchResult(@ModelAttribute("command") TitleSearchFormCommand command, Model model) {
        titles.clear();

        for (Title t : titleService.findByTitle(command.getTitleSearchString())) {

            if (!(titles.contains(t))) {
                titles.add(t);
            }
        }
        model.addAttribute("titles", titles);
        logger.debug("Found {} titles matchng search {}", titles.size(), command.getTitleSearchString());
        
        return "redirect:/search_results";
    }


    @GetMapping("/search_selection") // todo Could use the new annotation and pass entire object...
    public String generalSearchResultsPage(@RequestParam("id") Long id, Model model) {
        Title title = titleService.findById(id);
        List<Copy> copies = copyRepository.findAllAvailableCopiesByTitle(title);
        model.addAttribute("title", title);
        model.addAttribute("copies", copies);
        logger.debug("Found a title and a few copies");

        return "core/search_selection";
    }
}

