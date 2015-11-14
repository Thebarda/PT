package modele;
/**
 * 
 * @author ThebardaPNK
 *@version 1
 */
public class Station {
	private int id;
	private String nom;
	private String instructionCourte;
	private String instructionLongue;
	private int idUnite;
	private int frequence;
	private int seuilHaut;
	private int seuilBas;
	
	
	
	
	public Station(int id, String nom, String instructionCourte, String instructionLongue, int unite, int frequence,
			int seuilHaut, int seuilBas) {
		super();
		this.id = id;
		this.nom = nom;
		this.instructionCourte = instructionCourte;
		this.instructionLongue = instructionLongue;
		this.idUnite = unite;
		this.frequence = frequence;
		this.seuilHaut = seuilHaut;
		this.seuilBas = seuilBas;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getInstructionCourte() {
		return instructionCourte;
	}
	public void setInstructionCourte(String instructionCourte) {
		this.instructionCourte = instructionCourte;
	}
	public String getInstructionLongue() {
		return instructionLongue;
	}
	public void setInstructionLongue(String instructionLongue) {
		this.instructionLongue = instructionLongue;
	}
	public int getIdUnite() {
		return idUnite;
	}
	public void setUnite(int unite) {
		this.idUnite = unite;
	}
	public int getFrequence() {
		return frequence;
	}
	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}
	public int getSeuilHaut() {
		return seuilHaut;
	}
	public void setSeuilHaut(int seuilHaut) {
		this.seuilHaut = seuilHaut;
	}
	public int getSeuilBas() {
		return seuilBas;
	}
	public void setSeuilBas(int seuilBas) {
		this.seuilBas = seuilBas;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Station other = (Station) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Station [id=" + id + ", nom=" + nom + ", instructionCourte=" + instructionCourte
				+ ", instructionLongue=" + instructionLongue + ", unite=" + idUnite + ", frequence=" + frequence
				+ ", seuilHaut=" + seuilHaut + ", seuilBas=" + seuilBas + "]";
	}
	
}
