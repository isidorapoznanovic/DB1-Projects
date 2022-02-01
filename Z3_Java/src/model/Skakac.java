package model;

public class Skakac {
	private int id;
	private String ime;
	private String prezime;
	private String idDrzave;
	private int titule;
	private float rekord;
	
	public Skakac() {}

	public Skakac(int id, String ime, String prezime, String idDrzave, int titule, float rekord) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.idDrzave = idDrzave;
		this.titule = titule;
		this.rekord = rekord;
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

	public String getIdDrzave() {
		return idDrzave;
	}

	public void setIdDrzave(String idDrzave) {
		this.idDrzave = idDrzave;
	}

	public int getTitule() {
		return titule;
	}

	public void setTitule(int titule) {
		this.titule = titule;
	}

	public float getRekord() {
		return rekord;
	}

	public void setRekord(float rekord) {
		this.rekord = rekord;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((idDrzave == null) ? 0 : idDrzave.hashCode());
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		result = prime * result + ((prezime == null) ? 0 : prezime.hashCode());
		result = prime * result + Float.floatToIntBits(rekord);
		result = prime * result + titule;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Skakac other = (Skakac) obj;
		if (id != other.id)
			return false;
		if (idDrzave == null) {
			if (other.idDrzave != null)
				return false;
		} else if (!idDrzave.equals(other.idDrzave))
			return false;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		if (prezime == null) {
			if (other.prezime != null)
				return false;
		} else if (!prezime.equals(other.prezime))
			return false;
		if (Float.floatToIntBits(rekord) != Float.floatToIntBits(other.rekord))
			return false;
		if (titule != other.titule)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%-4d %-20s %-20s %-3s %-6d %-7f", id, ime, prezime, idDrzave, titule, rekord);
	}
	
	public static String getFormattedHeader(){
		return String.format("%-4s %-20s %-20s %-3s %-6s %-7s", "IDSC", "IMESC", "PRZSC", "IDD", "TITULE", "PBSC" );
	}
}
