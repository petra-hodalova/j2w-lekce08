package cz.czechitas.java2webapps.lekce8.controller;

import cz.czechitas.java2webapps.lekce8.entity.Osoba;
import cz.czechitas.java2webapps.lekce8.repository.OsobaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class OsobaController {

  private final OsobaRepository repository;

  @Autowired
  public OsobaController(OsobaRepository repository) {
    this.repository = repository;
  }

  @InitBinder
  public void nullStringBinding(WebDataBinder binder) {
    //prázdné textové řetězce nahradit null hodnotou
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  @GetMapping("/")
  public Object seznam() {
    //TODO načíst seznam osob
    return new ModelAndView("seznam")
            .addObject("osoby", repository.findAll());
  }

  @GetMapping("/novy")
  public Object novy() {
    return new ModelAndView("detail")
            .addObject("osoba", new Osoba());
  }

  @PostMapping("/novy")
  public Object pridat(@ModelAttribute("osoba") @Valid Osoba osoba, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "detail";
    }
    osoba.setId(null);
    repository.save(osoba);
    return "redirect:/";
  }

  @GetMapping("/{id:[0-9]+}")
  public Object detail(@PathVariable long id) {
    Optional<Osoba> osoba = repository.findById(id);
    if (osoba.isEmpty()) {
      return ResponseEntity.notFound();
    }
    return new ModelAndView("detail")
            .addObject("osoba", osoba.get());
  }

  @PostMapping("/{id:[0-9]+}")
  public Object ulozit(@PathVariable long id, @ModelAttribute("osoba") @Valid Osoba osoba, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "detail";
    }
    osoba.setId(id);
    repository.save(osoba);
    return "redirect:/";
  }

  @PostMapping(value = "/{id:[0-9]+}", params = "akce=smazat")
  public Object smazat(@PathVariable long id) {
    repository.deleteById(id);
    return "redirect:/";
  }

}
