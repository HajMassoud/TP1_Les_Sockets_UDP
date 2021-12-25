import java.util.Vector;

public class Groupe {
private String nom;
private String etats;

private Vector<User> members=new Vector<User>();


public Groupe() {
	super();
}
public Groupe(String nom) {
	super();
	this.nom = nom;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public Vector<User> getMembers() {
	return members;
}
public void setMembers(Vector<User> members) {
	this.members = members;
}
public String getEtats() {
	return etats;
}
public void setEtats(String etats) {
	this.etats = etats;
}

}
