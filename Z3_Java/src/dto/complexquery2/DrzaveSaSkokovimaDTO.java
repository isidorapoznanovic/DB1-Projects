package dto.complexquery2;

import java.util.ArrayList;
import java.util.List;

import model.Drzava;
import model.Skok;

public class DrzaveSaSkokovimaDTO {
	private Drzava drzava;
	private List<Skok> skokovi = new ArrayList<Skok>();
	
	public DrzaveSaSkokovimaDTO() {}
	
	public DrzaveSaSkokovimaDTO(Drzava drzava, List<Skok> skokovi) {
		super();
		this.drzava = drzava;
		this.skokovi = skokovi;
	}

	public Drzava getDrzava() {
		return drzava;
	}

	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}

	public List<Skok> getSkokovi() {
		return skokovi;
	}

	public void setSkokovi(List<Skok> skokovi) {
		this.skokovi = skokovi;
	}
	
	
}
