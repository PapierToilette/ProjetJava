package application;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import DAO.MonstreDAO;
import metier.Joueur;
import metier.Monstre;

public class Main {

	private static ArrayList<Monstre> combattants = new ArrayList<Monstre>(); // liste des monstres participant au combat (en vie)
	private static ArrayList<Monstre> allies = new ArrayList<Monstre>(); // ceux dont ont peut choisir l'action
	private static ArrayList<Monstre> ennemis = new ArrayList<Monstre>(); //liste d'ennemis = combattants - allies
	private static ArrayList<Monstre> ordrePrio = null;
	
	public static int random(int min, int max) { return ((int) (Math.random() * (max - min+1)) + min); }

	@SuppressWarnings("unchecked")
	public static ArrayList<Monstre> ordonner() {
		
		ordrePrio = (ArrayList<Monstre>) combattants.clone(); //copie les valeurs et pas l'adresse	des valeurs	
		//Trie par vitesse effective
		ordrePrio.sort((m1,m2) -> 
		Double.compare(m2.getVitesse() * m2.getCoefVitesse(), m1.getVitesse() * m1.getCoefVitesse()));

		ordrePrio.forEach(System.out::println);

		return ordrePrio;
	}
	
	public static void ajoutList(Monstre monstreToAdd, ArrayList<Monstre> liste) { liste.add(monstreToAdd); } //Ajoute un monstre � la liste des combattants
	
	public static void suppList(Monstre monstreToRemove) {	
        // Recherche le monstre par son nom
        Optional<Monstre> mRemove = combattants.stream()
                                              .filter(p -> p.getNom().equals(monstreToRemove.getNom()))
                                              .findFirst();
        // Si le monstre existe, on la supprime de combattants
        mRemove.ifPresent(combattants::remove);
		// Puis on cherche a le supprimer d'allies ou d'ennemis
        mRemove.ifPresent(allies::remove);
        mRemove.ifPresent(ennemis::remove);  // PAS SUR QUE CA FONCTIONNE BIENG !!!!!!!!!!!!!!!
        
	}
	
	public static void main(String[] args) {
		System.out.println("uwu");
		
		MonstreDAO m = new MonstreDAO();
		Monstre pikachu = m.read("Pikachu");
		Monstre dracofeu = m.read("Dracofeu");
		Monstre bulbizarre = m .read("Bulbizarre");
		Monstre ectoplasma = m.read("Ectoplasma");
		Monstre ronflex = m.read("Ronflex");
		Monstre ABZ = new Joueur("ABZ", 120, 100, 120, 120, 120, 120, 0, null);
		
		
		
		ajoutList(ABZ, combattants); ajoutList(ronflex, combattants); ajoutList(ectoplasma, combattants); 
		ajoutList(bulbizarre, combattants); ajoutList(dracofeu, combattants); ajoutList(pikachu, combattants);	

		/*
        Scanner scanner = new Scanner(System.in);
        String texte = scanner.nextLine();
        scanner.close();
        
		 */
		
		
		System.out.println("D�but du jeu");
		ajoutList(ABZ, allies);
		//1. choix des �quipiers parmis la liste dispo (monstre de base au d�but + monstres captur�es par la suite
		ajoutList(ectoplasma, allies); ajoutList(pikachu, allies);
		ennemis = (ArrayList<Monstre>) combattants.stream() // la difference entre les combattants et les allies = ennemis
				.filter(e -> !allies.contains(e))
				.collect(Collectors.toList());
		
		
		
	}
}