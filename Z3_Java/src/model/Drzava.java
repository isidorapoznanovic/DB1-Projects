package model;

public class Drzava {
	private String id;
	private String naziv;
	
	public Drzava() {}

	public Drzava(String id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getnaziv() {
		return naziv;
	}

	public void setnaziv(String naziv) {
		this.naziv = naziv;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
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
		Drzava other = (Drzava) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%-3s %-35s", id, naziv);
	}
	
	public static String getFormattedHeader(){
		return String.format("%-3s %-35s", "IDD", "NAZIVD" );
	}
}
