package fr.pizzeria.console;

import java.util.Scanner;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaDataBaseDao;
import fr.pizzeria.dao.PizzaMemDao;
import fr.pizzeria.services.MenuService;
import fr.pizzeria.services.MenuServiceFactory;

public class PizzeriaAdminConsoleApp {

	public static void main(String[] args) {


		/* Initialisation du TDB de pizzas */
		IPizzaDao dao = new PizzaDataBaseDao();

		/* Initialisation du scanner */
		Scanner scanner = new Scanner(System.in);
		int choix = 0;

		/* Structure de répétition de la boucle principale */
		while(choix!=99) {
			println("***** Pizzeria Administration *****");
			println("1. Lister les pizzas ");
			println("2. Ajouter une nouvelle pizza ");
			println("3. Mettre à jour une pizza ");
			println("4. Supprimer une pizza ");
			println("5. Charger une liste prédéfinie de pizzas");
			println("99. Sortir ");

			System.out.print("Veuillez choisir une option:");
			choix = Integer.parseInt(scanner.nextLine());

			MenuService service = MenuServiceFactory.getInstance(choix);
			service.executeUC(scanner, dao);
		} 		
		scanner.close();
	}



	private static void println(String msg){
		System.out.println(msg);
	}
}
