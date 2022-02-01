package dto.complexquery1;

import java.util.ArrayList;
import java.util.List;

import model.Skakaonica;
import model.Skok;

public class SkakaonicaSaSkokovimaDTO {
	private Skakaonica skakaonica;
	private List<Skok> skokovi = new ArrayList<Skok>();
	private int brSkakaca;
	
	public SkakaonicaSaSkokovimaDTO() {}

	public SkakaonicaSaSkokovimaDTO(Skakaonica skakaonica, List<Skok> skokovi, int brSkakaca) {
		super();
		this.skakaonica = skakaonica;
		this.skokovi = skokovi;
		this.brSkakaca = brSkakaca;
	}

	public Skakaonica getSkakaonica() {
		return skakaonica;
	}

	public void setSkakaonica(Skakaonica skakaonica) {
		this.skakaonica = skakaonica;
	}

	public List<Skok> getSkokovi() {
		return skokovi;
	}

	public void setSkokovi(List<Skok> skokovi) {
		this.skokovi = skokovi;
	}

	public int getBrSkakaca() {
		return brSkakaca;
	}

	public void setBrSkakaca(int brSkakaca) {
		this.brSkakaca = brSkakaca;
	}
	
}
