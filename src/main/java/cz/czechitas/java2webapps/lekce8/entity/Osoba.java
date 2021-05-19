package cz.czechitas.java2webapps.lekce8.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * Entita – údaje o jedné osobě.
 * <p>
 * Pojmenování properties musí zůstat zachováno, protože odpovídá názvům sloupečků v databázové tabulce. Stejně tak musí být zachováno jméno třídy, odpovídá
 * jménu tabulky v databázi.
 */
@Entity
public class Osoba {
  /**
   * Identifikátor osoby.
   *
   * Jedná se o databázový identifikátor (anotace {@link @Id} a jeho hodnota je automaticky přiřazována databází (anotace @@link @{@link GeneratedValue}}
   * s typem {@link GenerationType#IDENTITY}.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Křestní jméno.
   */
  @Length(max = 100)
  @NotBlank
  private String jmeno;

  /**
   * Příjmení.
   */
  @Length(max = 100)
  @NotBlank
  private String prijmeni;

  /**
   * Datum narození.
   *
   * Datum narození musí být v minulosti nebo dnes.
   */
  @PastOrPresent
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate datumNarozeni;

  /**
   * Adresa.
   */
  @Length(max = 200)
  @NotBlank
  private String adresa;

  /**
   * E-mail – nepovinný údaj.
   */
  @Length(max = 100)
  @Email
  private String email;

  /**
   * Telefon – nepovinný údaj.
   *
   * Pokud je uveden, musí mít délku 9–13 znaků, může začínat znakem „+“ a dále mohou být jen samé číslice.
   */
  @Length(min = 9, max = 13)
  @Pattern(regexp = "\\+?\\d+")
  private String telefon;

  public Osoba() {
  }

  public Osoba(Long id, String jmeno, String prijmeni, LocalDate datumNarozeni, String adresa, String email, String telefon) {
    this.id = id;
    this.jmeno = jmeno;
    this.prijmeni = prijmeni;
    this.datumNarozeni = datumNarozeni;
    this.adresa = adresa;
    this.email = email;
    this.telefon = telefon;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getJmeno() {
    return jmeno;
  }

  public void setJmeno(String jmeno) {
    this.jmeno = jmeno;
  }

  public String getPrijmeni() {
    return prijmeni;
  }

  public void setPrijmeni(String prijmeni) {
    this.prijmeni = prijmeni;
  }

  public LocalDate getDatumNarozeni() {
    return datumNarozeni;
  }

  public void setDatumNarozeni(LocalDate datumNarozeni) {
    this.datumNarozeni = datumNarozeni;
  }

  public String getAdresa() {
    return adresa;
  }

  public void setAdresa(String adresa) {
    this.adresa = adresa;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefon() {
    return telefon;
  }

  public void setTelefon(String telefon) {
    this.telefon = telefon;
  }

  public Integer getVek() {
    if (datumNarozeni == null) {
      return null;
    }
    return datumNarozeni.until(LocalDate.now()).getYears();
  }

}
