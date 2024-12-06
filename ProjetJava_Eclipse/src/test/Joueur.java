package test;
//import java.util.ArrayList;

public class Joueur extends Monstre{

	private int argent;
	//private ArrayList<Item> inventaire;  A changer avec la partie gestion d'inventaire

	public Joueur(String nom, int pv, int pe, double attaque, double special, double defense, double vitesse, int tauxCapture) {
		super(nom, pe, pe, vitesse, vitesse, vitesse, vitesse, tauxCapture);
		this.niveau = 1;
		this.next_niveau_xp = 15;
		this.argent = 0;
		this.pvNow = this.pvMax;
		this.peNow = this.peMax;
	}

	public int getArgent() {
		return argent;
	}
	public void setArgent(int argent) {
		this.argent = argent;
	}

	@Override
	public String toString() {
		return nom + "   niveau: " + niveau + ", Exp�rience actuelle=" + xpNow + "/" + next_niveau_xp
				+ "\nPV : " + pvNow + "/" + pvMax + "		PE :" + peNow + "/" + peMax
				+ "\n - Attaque: " + attaque
				+ ";\n - Sp�cial: " + special
				+ ";\n - D�fense: " + defense
				+ ";\n - Vitesse: " + vitesse
				+ ";\n zennys: " + argent;
	}

	public void Capture(Monstre cible) {
		//prioritaire +3
		double x = ((1.0-(2.0/3.0)*(cible.getPvNow()/cible.getPvMax()))*cible.getTauxCapture());	//ratio des PV
		double resultat = (Math.pow(((65535.0*Math.pow((x/255.0), 0.25)+1.0)/65535.0), 4.0))*100;	//pourcentage final
		
		boolean reussite = false;
		for (int i = 1; i < 5; i++) { //test 4 fois la possibilit� de capturer le monstre
			if (Combat.random(1, 100) <= resultat) {	
				reussite = true;	//si il y a au moins une r�ussite, il est captur�
			}
 		}
		if (reussite) {
			System.out.println("Capture r�ussie");
			
		} else {
			System.out.println("Echec de la capture");
		}
	}
	
	// Inventaire -> prioritaire +2 lors de l'utilisation durant le tour
	
}