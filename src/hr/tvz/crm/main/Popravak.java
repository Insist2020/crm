package hr.tvz.crm.main;

public class Popravak {
	private 
		String naziv, opis, vozilo;
		int id, idKlijent, tip;
		Double cijena;
		Long datum;

	@Override
	public String toString() {
		return "Popravak [naziv=" + naziv + ", opis=" + opis + ", vozilo="
				+ vozilo + ", id=" + id + ", cijena=" + cijena
				+ ", idKlijent=" + idKlijent + ", datum=" + datum + "]";
	}

	public Popravak(int id, String naziv, String opis, String vozilo, Double cijena, int idKlijent, Long datum, int tip) {
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.vozilo = vozilo;
		this.cijena = cijena;
		this.idKlijent = idKlijent;
		this.datum = datum;
		this.tip = tip;
	}
	
	public Popravak(String naziv, String opis, String vozilo, Double cijena, int idKlijent, int tip) {
		this.naziv = naziv;
		this.opis = opis;
		this.vozilo = vozilo;
		this.cijena = cijena;
		this.idKlijent = idKlijent;
		this.tip = tip;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getVozilo() {
		return vozilo;
	}

	public void setVozilo(String vozilo) {
		this.vozilo = vozilo;
	}

	public Double getCijena() {
		return cijena;
	}

	public void setCijena(Double cijena) {
		this.cijena = cijena;
	}
	
	public int getIdKlijent() {
		return idKlijent;
	}

	public void setIdKlijent(int idKlijent) {
		this.idKlijent = idKlijent;
	}
	
	public Long getDatum() {
		return datum;
	}

	public void setDatum(Long datum) {
		this.datum = datum;
	}
	
	public int getTip() {
		return tip;
	}

	public void setTip(int tip) {
		this.tip = tip;
	}
	
}
