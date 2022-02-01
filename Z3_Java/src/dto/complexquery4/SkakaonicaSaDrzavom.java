package dto.complexquery4;

import model.Drzava;
import model.Skakaonica;

public class SkakaonicaSaDrzavom {
	private Skakaonica skakaonica;
	private Drzava drzava;
	
	public SkakaonicaSaDrzavom() {}

	public SkakaonicaSaDrzavom(Skakaonica skakaonica, Drzava drzava) {
		super();
		this.skakaonica = skakaonica;
		this.drzava = drzava;
	}

	public Skakaonica getSkakaonica() {
		return skakaonica;
	}

	public void setSkakaonica(Skakaonica skakaonica) {
		this.skakaonica = skakaonica;
	}

	public Drzava getDrzava() {
		return drzava;
	}

	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}
}
