package test;

import DAO.MonstreDAO;


public class Test {
	
	public static void main(String[] args) {
		System.out.println("uwu");
		
		MonstreDAO m = new MonstreDAO();
		Monstre pikachu = m.read("Pikachu");
		
		System.out.println(pikachu);
	}
	
}
