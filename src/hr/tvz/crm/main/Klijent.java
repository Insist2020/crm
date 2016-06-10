package hr.tvz.crm.main;

public class Klijent {
	private String ime, prezime, adresa, kontakt, email;
	private int id, registracija;

	public Klijent(int id, String ime, String prezime, String adresa, String mobitel, String email, int registracija) {
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.kontakt = mobitel;
		this.email = email;
		this.registracija = registracija;
	}
	
	public Klijent(String ime, String prezime, String adresa, String mobitel, String email, int registracija) {
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.kontakt = mobitel;
		this.email = email;
		this.registracija = registracija;
	}
	
	public Klijent(String ime, String prezime, String adresa, String mobitel, String email) {
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.kontakt = mobitel;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return ime + " " + prezime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getKontakt() {
		return kontakt;
	}

	public void setKontakt(String mobitel) {
		this.kontakt = mobitel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getRegistracija() {
		return registracija;
	}

	public void setRegistracija(int registracija) {
		this.registracija = registracija;
	}
}
