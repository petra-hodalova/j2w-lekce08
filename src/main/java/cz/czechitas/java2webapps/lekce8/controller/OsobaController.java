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

  /**
   * Nastavení „bindování“ vstupů od uživatele do Java objektů – prázdné řetězce se nastaví jako {@code null} hodnota.
   */
  @InitBinder
  public void nullStringBinding(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  /**
   * Zobrazí seznam všech osob.
   */
  @GetMapping("/")
  public Object seznam() {
    return new ModelAndView("seznam")
            .addObject("osoby", repository.findAll());
  }

  /**
   * Zobrazí formulář pro zadání nové osoby.
   */
  @GetMapping("/novy")
  public Object novy() {
    return new ModelAndView("detail")
            .addObject("osoba", new Osoba());
  }

  /**
   * Uloží novou osobu do databáze.
   * <p>
   * Pokud není splněna některá validace, znovu zobrazí formulář pro zadání osoby.
   */
  @PostMapping("/novy")
  public Object pridat(@ModelAttribute("osoba") @Valid Osoba osoba, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "detail";
    }
    osoba.setId(null);
    repository.save(osoba);
    return "redirect:/";
  }

  /**
   * Zobrazí detail osoby.
   *
   * @param id Identifikátor osoby zadaný v URL. Identifikátor může obsahovat jen číslice (musí být alespoň jedna).
   */
  @GetMapping("/{id:[0-9]+}")
  public Object detail(@PathVariable long id) {
    Optional<Osoba> osoba = repository.findById(id);
    if (osoba.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return new ModelAndView("detail")
            .addObject("osoba", osoba.get());
  }

  /**
   * Uloží změněné údaje o osobě do databáze (aktualizuje záznam).
   *
   * @param osoba         Údaje o osobě zadané uživatel plus identifikátor osoby převzatý z URL.
   * @param bindingResult Výsledek validace.
   */
  @PostMapping("/{id:[0-9]+}")
  public Object ulozit(@PathVariable long id, @ModelAttribute("osoba") @Valid Osoba osoba, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "detail";
    }
    repository.save(osoba);
    return "redirect:/";
  }


  /**
   * Smaže údaje o osobě z databáze.
   *
   * @param id Identifikátor osoby zadaný v URL. Identifikátor může obsahovat jen číslice (musí být alespoň jedna).
   */
  @PostMapping(value = "/{id:[0-9]+}", params = "akce=smazat")
  public Object smazat(@PathVariable long id) {
    repository.deleteById(id);
    return "redirect:/";
  }

}
