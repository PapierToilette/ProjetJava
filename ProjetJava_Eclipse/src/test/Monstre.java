package test;

public class Monstre{
	//MONSTRE( idMonstre, nom, PV, PE, attaque, d�fense, sp�cial, vitesse, tauxCapture, description)

	public String nom;
	// diff�rence entre la jauge de PV maximun et les PV actuelles, pareil pour les PE
	protected int pvMax; protected int pvNow;				
	protected int peMax; protected int peNow;
	
	protected double attaque; protected double special;
	protected double defense; protected double vitesse;
	
	protected int niveau; protected int tauxCapture;
	// next_niveau_xp est le nombre de points d'xp qu'il faut pour passer au niveau suivant.
	protected int xpNow; protected int next_niveau_xp;
	
	//Malus ou Bonus sur la statistique
	protected double coefAttaque = 1.0;	protected double coefSpecial = 1.0;
	protected double coefDefense = 1.0;	protected double coefVitesse = 1.0;
	//Les attaques du monstre
	protected Capacite attaque1; protected Capacite attaque2;
	protected Capacite attaque3; protected Capacite attaque4;
	
	public Monstre(String nom, int pv, int pe, double attaque, double special, double defense, double vitesse, int tauxCapture) {
		this.nom = nom;
		this.pvMax = pv;
		this.peMax = pe;
		this.attaque = attaque;
		this.special = special;
		this.defense = defense;
		this.vitesse = vitesse;
		this.tauxCapture = tauxCapture;
		niveau = 1;
		next_niveau_xp = 15; //indicatif
		pvNow = this.pvMax;
		peNow = this.peMax;
	}

	public String getNom() {
		return nom;
	}
	public int getPvMax() {
		return pvMax;
	}
	public void setPvMax(int pvMax) {
		this.pvMax = pvMax;
	}
    public int getPvNow() {
		return pvNow;
	}
	public void setPvNow(int pvNow) {
		this.pvNow = pvNow;
	}
    public int getPeNow() {
		return peNow;
	}
	public void setPeNow(int peNow) {
		this.peNow = peNow;
	}
	
	public double getAttaque() {
		return attaque;
	}
	public double getSpecial() {
		return special;
	}
	public double getDefense() {
		return defense;
	}
	public double getVitesse() {
		return vitesse;
	}
	public double getTauxCapture() {
		return tauxCapture;
	}
	
	public double getCoefAttaque() {
		return coefAttaque;
	}
	public void setCoefAttaque(double coefAttaque) {
		this.coefAttaque = coefAttaque;
	}
	public double getCoefSpecial() {
		return coefSpecial;
	}
	public void setCoefSpecial(double coefSpecial) {
		this.coefSpecial = coefSpecial;
	}
	public double getCoefDefense() {
		return coefDefense;
	}
	public void setCoefDefense(double coefDefense) {
		this.coefDefense = coefDefense;
	}
	public double getCoefVitesse() {
		return coefVitesse;
	}
	public void setCoefVitesse(double coefVitesse) {
		this.coefVitesse = coefVitesse;
	}


	private void gain_xp(int gain) {
		this.xpNow += gain;
		while (this.xpNow >= this.next_niveau_xp) {
			this.xpNow = this.xpNow - this.next_niveau_xp;
			this.niveau++;
			this.next_niveau_xp += (int)(this.next_niveau_xp* 1.2);
			
			System.out.println("Vous �tes d�sormais niveau "+ this.niveau);
			
			//valeurs indicatives pour les testes
			this.pvMax += Combat.random(1, 3);		
			pvNow = this.pvMax;
			this.peMax += Combat.random(1, 3);
			peNow = this.peMax;
			this.attaque += Combat.random(1, 5);
			this.special += Combat.random(1, 5);
			this.defense += Combat.random(1, 5);
			this.vitesse += Combat.random(1, 5);
		}
	}
	
	/* calcul des dommages : (different entre l'attaque et le special ???)
	pvPerdus =(int) ((((niveau*0.5+2)*(attaqueAttaquant*coefboost)*puissanceCapacite)/(defenseCible*coefBoost))/50)
	*/
	public void Attaquer(Capacite capacite, Monstre cible) {
		if (Combat.random(1, 100) <= capacite.getPrecision()) { //chance de r�ussir l'attaque
			if (capacite.getCout()== 0) {	//si la capacit� est d'attaque
				int pvPerdus = (int) ((((this.niveau*0.5+2)*(this.attaque*this.coefAttaque)*capacite.getPuissance())/(cible.getDefense()*cible.getCoefDefense()))/50);
				cible.setPvNow(cible.getPvNow()-pvPerdus); //gestion de la mort dans Combat
				System.out.println("L'ennemis a perdu " + pvPerdus);
				
			} else {
				if (this.getPeNow() >= capacite.getCout()) {
					if (capacite.getPuissance() == 0) { //statut ?
						if (capacite.getEffet()!= null) { //petite v�rif suppl�mentaire
							Capacite.Alteration(cible, capacite.getEffet());
						}
						this.setPeNow(this.peNow- capacite.getCout()); //mettre le cout d'�nergie de la capacite
						
					} else {
						int pvPerdus = (int) ((((this.niveau*0.5+2)*(this.special*this.coefSpecial)*capacite.getPuissance())/(cible.getDefense()*cible.getCoefDefense()))/50);
						cible.setPvNow(cible.getPvNow()-pvPerdus);
						this.setPeNow(this.peNow - capacite.getCout());	//mettre le cout d'�nergie de la capacite
						System.out.println("L'ennemis a perdu " + pvPerdus);
					}
					
				} else {
					System.out.println(this.nom + " n'a pas assez d'�nergie pour lancer son attaque");
				}
			}
			
		} else {System.out.println("L'attaque a �chou�");}	
	}

	public void Denfendre() {
		//prioritaire +1
		//augment le coef de defense pendant CE TOUR UNIQUEMENT
		this.setCoefSpecial(this.coefDefense+0.3); //jsp combien augmenter le coef
		
		System.out.println(this.nom + " attend en position d�fensive");
	}

	public String InformationsRapides() {
		return nom + ", niveau: " + niveau
				+ "\nPV : " + pvNow + "/" + pvMax + " et PE :" + peNow + "/" + peMax;
	}
	
	public String toString() {
		return nom + "   niveau: " + niveau + ", Exp�rience actuelle=" + xpNow + "/" + next_niveau_xp
				+ "\nPV : " + pvNow + "/" + pvMax + "		PE :" + peNow + "/" + peMax
				+ "\n - Attaque: " + attaque
				+ ";\n - Sp�cial: " + special
				+ ";\n - D�fense: " + defense
				+ ";\n - Vitesse: " + vitesse + ";";
	}


}