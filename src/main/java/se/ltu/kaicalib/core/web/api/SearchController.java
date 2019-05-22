package se.ltu.kaicalib.core.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.ltu.kaicalib.account.domain.User;
import se.ltu.kaicalib.account.service.UserServiceImpl;
import se.ltu.kaicalib.account.utils.RoleNotValidException;
import se.ltu.kaicalib.account.validator.UserValidator;
import se.ltu.kaicalib.core.domain.Title;
import se.ltu.kaicalib.core.domain.TitleSearchFormCommand;
import se.ltu.kaicalib.core.repository.TitleRepository;
import se.ltu.kaicalib.core.service.TitleService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    TitleService titleService;
    List<Title> titles = new ArrayList<>();

    @Autowired
    public SearchController(TitleService titleService) {
        this.titleService = titleService;
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

        //return "redirect:search_results";
        return "redirect:/search_results";
    }


    @GetMapping("/search_results")
    public String showSearchResultsBlank(Model model) {
        logger.error("GET request not supported on search_results");
        //model.addAttribute("error", "GET request not supported on search_results");
        model.addAttribute("titles", titles);
        return "core/search_results";
    }




    //@RequestAttribute(title) // todo Could use the new annotation and pass entire object...
    //@RequestParam(id)
    @GetMapping("/search_selection")
    public String generalSearchResultsPage(@RequestParam("id") Long id, Model model) {
        model.addAttribute("title", titleService.findById(id));

        return "core/search_selection";
    }


}

