package model;

public class Skok {
	private String id;
	private int idSkakaca;
	private String idSkakkaonice;
	private int bodoviDuzina;
	private int bodoviStil;
	private int bodoviVetar;
	
	public Skok() {}

	public Skok(String id, int idSkakaca, String idSkakkaonice, int bodoviDuzina, int bodoviStil, int bodoviVetar) {
		super();
		this.id = id;
		this.idSkakaca = idSkakaca;
		this.idSkakkaonice = idSkakkaonice;
		this.bodoviDuzina = bodoviDuzina;
		this.bodoviStil = bodoviStil;
		this.bodoviVetar = bodoviVetar;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdSkakaca() {
		return idSkakaca;
	}

	public void setIdSkakaca(int idSkakaca) {
		this.idSkakaca = idSkakaca;
	}

	public String getIdSkakkaonice() {
		return idSkakkaonice;
	}

	public void setIdSkakkaonice(String idSkakkaonice) {
		this.idSkakkaonice = idSkakkaonice;
	}

	public int getBodoviDuzina() {
		return bodoviDuzina;
	}

	public void setBodoviDuzina(int bodoviDuzina) {
		this.bodoviDuzina = bodoviDuzina;
	}

	public int getBodoviStil() {
		return bodoviStil;
	}

	public void setBodoviStil(int bodoviStil) {
		this.bodoviStil = bodoviStil;
	}

	public int getBodoviVetar() {
		return bodoviVetar;
	}

	public void setBodoviVetar(int bodoviVetar) {
		this.bodoviVetar = bodoviVetar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bodoviDuzina;
		result = prime * result + bodoviStil;
		result = prime * result + bodoviVetar;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + idSkakaca;
		result = prime * result + ((idSkakkaonice == null) ? 0 : idSkakkaonice.hashCode());
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
		Skok other = (Skok) obj;
		if (bodoviDuzina != other.bodoviDuzina)
			return false;
		if (bodoviStil != other.bodoviStil)
			return false;
		if (bodoviVetar != other.bodoviVetar)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idSkakaca != other.idSkakaca)
			return false;
		if (idSkakkaonice == null) {
			if (other.idSkakkaonice != null)
				return false;
		} else if (!idSkakkaonice.equals(other.idSkakkaonice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%-4s %-4d %-6s %-7d %-5d, %-6d", id, idSkakaca, idSkakkaonice, bodoviDuzina, bodoviStil, bodoviVetar);
	}
	
	public static String getFormattedHeader(){
		return String.format("%-4s %-4s %-6s %-7s %-5s, %-6s", "IDSK", "IDSC", "IDSA", "BDUZINA", "BSTIL", "BVETAR");
	}
}
