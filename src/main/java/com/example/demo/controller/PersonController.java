package com.example.demo.controller;

import com.example.demo.DAO.DAO;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;


@RestController
@RequestMapping("/")
public class PersonController {
    private  DAO personDAO;
    @Autowired
    public void PeopleController(DAO personDAO) {
        this.personDAO = personDAO;
    }
    @GetMapping("/persons")
    public ModelAndView index(Model model) {
        model.addAttribute("people", personDAO.index());
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
    @GetMapping("/persons/{id}")
    public ModelAndView show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        ModelAndView mav = new ModelAndView("show");
        return mav;
    }
    @GetMapping("/persons/new")
    public ModelAndView newPerson(@ModelAttribute("person") Person person) {
        ModelAndView mav = new ModelAndView("new");
        return mav;
    }
    @PostMapping("/persons")
    public RedirectView create(@ModelAttribute("person")@Valid Person person,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors())
        {
            return new RedirectView("/persons", true);

        }
        personDAO.save(person);
        return new RedirectView("/persons", true);

    }
    @GetMapping("persons/{id}/edit")
    public ModelAndView edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));

        ModelAndView mav = new ModelAndView("edit");
        return mav;
    }

    @PatchMapping("persons/{id}")
    public RedirectView update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                               @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
        {
            return new RedirectView("/persons", true);
        }
        personDAO.update(id, person);
        return new RedirectView("/persons", true);
    }

    @DeleteMapping("persons/{id}")
    public RedirectView delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return new RedirectView("/persons", true);
    }


}