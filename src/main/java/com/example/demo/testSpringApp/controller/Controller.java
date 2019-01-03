package com.example.demo.testSpringApp.controller;
import com.example.demo.testSpringApp.model.PageModel;
import com.example.demo.testSpringApp.model.PersonForm;
import com.example.demo.testSpringApp.model.Quote;
import com.example.demo.testSpringApp.model.User;
import com.example.demo.testSpringApp.repository.QuoteRepo;
import com.example.demo.testSpringApp.service.QuoteService;
import com.example.demo.testSpringApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.stereotype.Controller
public class Controller {

    private final int USER_NAME_LENGHT = 255;
    private final int QUOTE_TEXT_LENGHT = 65535;
    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = { 5, 10};
    private String sortDateMethod = "DESC";

    @Autowired
    QuoteService quoteService;
    @Autowired
    UserService userService;
    @Autowired
    QuoteRepo quoteRepo;

    @Value("${error.message1}")
    private String errorMessage1;
    @Value("${error.message2}")
    private String errorMessage2;

    @GetMapping("/")
    public ModelAndView showQuotes(@RequestParam("pageSize") Optional<Integer> pageSize,
                                   @RequestParam("page") Optional<Integer> page) {

        ModelAndView modelAndView = new ModelAndView("listQuotes");
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Page<Quote> quotesList = null;
        switch (sortDateMethod) {
            case "ASC":
                quotesList = quoteRepo.findAll(new PageRequest(evalPage, evalPageSize,
                        Sort.Direction.ASC,"date"));
                break;
            case "DESC":
                quotesList = quoteRepo.findAll(new PageRequest(evalPage, evalPageSize,
                        Sort.Direction.DESC,"date"));
                break;
        }

        PageModel pager = new PageModel(quotesList.getTotalPages(),quotesList.getNumber(),BUTTONS_TO_SHOW);
        modelAndView.addObject("quotesList",quotesList);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("sort", sortDateMethod);
        return modelAndView;
    }

    @GetMapping("/sort/{sortDate}")
    public String sortChoose(@PathVariable String sortDate) {
        sortDateMethod = sortDate;
        return "redirect:/";
    }

    @RequestMapping(value = {"/addQuote"}, method = RequestMethod.GET)
    public ModelAndView addQuote() {
       List <User> users = userService.getAll();
        ModelAndView md = new ModelAndView("addQuote","users",users);
        md.addObject("personForm", new PersonForm());
        return  md;
    }

    @RequestMapping(value = { "/addQuote" }, method = RequestMethod.POST)
    public String saveQuote(Model model,
                            @ModelAttribute("personForm")PersonForm personForm) {
        User user = checkUserExist(userService,personForm);
        Quote quote = new Quote(personForm.getText(),new Date(), user);

        if (personForm.getName().length() > USER_NAME_LENGHT ||
                personForm.getText().length() > QUOTE_TEXT_LENGHT){
                model.addAttribute("errorMessage2", errorMessage2);
                return "/addQuote";
        } else if (personForm.getName() != null && personForm.getName().length() > 0 &&
                personForm.getText() != null && personForm.getText().length() > 0){
                userService.addUser(user);
                quoteService.addQuote(quote);
                return "redirect:/";
        } else {
            model.addAttribute("errorMessage1", errorMessage1);
        }
                return "/addQuote";
        }

        private User checkUserExist (UserService us, PersonForm pf){
            for (User u : us.getAll()){
                if (u.getName().equals(pf.getName())){
                    return u;
                }
            }
            return new User(pf.getName());
        }

}
