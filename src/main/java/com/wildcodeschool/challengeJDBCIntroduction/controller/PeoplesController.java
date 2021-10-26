package com.wildcodeschool.challengeJDBCIntroduction.controller;

import com.wildcodeschool.challengeJDBCIntroduction.entity.Peoples;
import com.wildcodeschool.challengeJDBCIntroduction.repository.PeoplesRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PeoplesController {
    private PeoplesRepository repository = new PeoplesRepository();

    @GetMapping("/peoples")
    public String getAll(Model model) {
        model.addAttribute("peoples", repository.findAll());

        return "peoples";
    };

    @GetMapping("/people")
    public String getPeople(Model model,
                            @RequestParam(required = false) Long id) {

        Peoples peoples = new Peoples();
        if (id != null) {
            peoples = repository.findById(id);
        }

        model.addAttribute("peoples", peoples);

        return "people";
    }

    @PostMapping("/peoples")
    public String postPeople(@ModelAttribute Peoples people) {
        if (people.getId() != null) {
            repository.update(people);
        } else {
            repository.save(people);
        }

        return "redirect:/peoples";
    }

    @GetMapping("/people/delete")
    public String deleteSchool(@RequestParam Long id) {
        repository.deleteById(id);

        return "redirect:/peoples";
    }
}
