package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingleConnection {
    private static Connection connect;


    private SingleConnection() {
        String user = "root";
        String mdp = "root"; // !!!!!
        String url = "jdbc:mysql://localhost:3306/jeu_java?useSSL=false&serverTimezone=UTC";

        try {
            // Création de la connexion
            connect = DriverManager.getConnection(url, user, mdp);
            System.out.println("Connexion reussie");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connexion echouee");
        }
    }

    // Méthode pour obtenir l'instance unique
    public static Connection getInstance() {
        if (connect == null) {
            new SingleConnection(); // Appel au constructeur privé
            return connect;
        }
        return connect;
    }


    public static void close() {
        try {
            if (connect != null && !connect.isClosed()) {
                connect.close();
                System.out.println("Connexion fermée");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
