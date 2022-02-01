package dto.complexquery3;

import model.Skakac;
import model.Skok;

public class TehnikalijeTransakcijeDTO {
	private boolean postojiSkok;
	private boolean promenjenIgrac = true;
	private Skok skokStari;
	private Skok skokIzmenjen;
	private Skakac skakac;
	private Skakac skakacNovi;
	private int bvetar;
	
	public TehnikalijeTransakcijeDTO() {}
	
	public boolean isPromenjenIgrac() {
		return promenjenIgrac;
	}


	public void setPromenjenIgrac(boolean promenjenIgrac) {
		this.promenjenIgrac = promenjenIgrac;
	}

	public boolean isPostojiSkok() {
		return postojiSkok;
	}

	public void setPostojiSkok(boolean postojiSkok) {
		this.postojiSkok = postojiSkok;
	}

	public Skok getSkokStari() {
		return skokStari;
	}

	public void setSkokStari(Skok skokStari) {
		this.skokStari = skokStari;
	}

	public Skok getSkokIzmenjen() {
		return skokIzmenjen;
	}

	public void setSkokIzmenjen(Skok skokIzmenjen) {
		this.skokIzmenjen = skokIzmenjen;
	}

	public Skakac getSkakac() {
		return skakac;
	}

	public void setSkakac(Skakac skakac) {
		this.skakac = skakac;
	}

	public int getBvetar() {
		return bvetar;
	}

	public void setBvetar(int bvetar) {
		this.bvetar = bvetar;
	}

	public Skakac getSkakacNovi() {
		return skakacNovi;
	}

	public void setSkakacNovi(Skakac skakacNovi) {
		this.skakacNovi = skakacNovi;
	}
	
}
