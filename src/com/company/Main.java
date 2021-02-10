
package com.company;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Objectif: Jeu de multiplication qui demande au joueur une table et les réponses à dix expressions aléatoires. Calcule des statistiques à la fin. Autant de parties que le joueur veut.
 * @author Thomas Laporte
 * Session A2020 - 05/11/2020
 */

public class Main {

    public static final String ANSI_RESET = "\u001B[0m"; //Afficher sans couleur.
    public static final String ANSI_RED = "\u001B[31m"; //Afficher en rouge.
    public static final String ANSI_BLUE = "\u001B[34m"; //Afficher en bleu.

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("0.00");
        int tableChoisie; //Entier représentant la table choisie par le joueur.
        int nbAleatoire = 0; //Entier aléatoire représentant le second opérande.
        int reponse; //Entier représentant la réponse du joueur.
        int i = 0; //Variable de contrôle et compteur d'essais.
        int ii = 0; //Variable de contrôle d'erreurs d'entrée.
        int pointage; //Nombre de bonnes réponses.
        int compteurParties = 0; //Compte le nombre de parties jouées.
        int compteurGagnees = 0; //Compte le nombre de parties gagnées.
        int compteurPerdues = 0; //Compte le nombre de parties perdues.
        int[] resultats = new int[0]; //Tableau du résultat de chaque partie jouée.
        int nbElem = 1; //Entier représentant le nombre d'éléments dans résultats.
        boolean rejouer; //Variable de contrôle pour rejouer une partie.
        double moyenne = 0; //Moyenne de tous les résultats.

        do {
            tableChoisie = choisirTable();

            pointage = 0;
            do {
                if (i == ii)
                    nbAleatoire = genererNbAleatoire();

                System.out.print(ANSI_RESET + ("(Essais " + (i + 1) + "/10) " + tableChoisie + " * " + nbAleatoire + " = "));

                try {
                    reponse = scan.nextInt();
                    i++;
                    ii = i;

                    if (reponse != tableChoisie * nbAleatoire)
                        System.out.println(ANSI_RED + (" " + tableChoisie + " * " + nbAleatoire + " = " + tableChoisie * nbAleatoire));
                    else
                        pointage++;
                }

                catch (InputMismatchException e) {
                    ii--;
                    scan.next();
                }
            }
            while (i<10);

            i = 0;
            ii = 0;

            compteurParties++;

            for (int elem: resultats) {
                nbElem++;
            }

            resultats = addElemTableau(resultats, nbElem);
            resultats[resultats.length-1] = pointage;

            nbElem = 1;

            if (pointage >= 9) {
                System.out.println(ANSI_BLUE + "\n-> Bravo vous avez gagné! " + pointage + " /10\n");
                compteurGagnees++;
            }
            else {
                System.out.println(ANSI_BLUE + "\n-> Vous avez perdu! " + pointage + " /10\n");
                compteurPerdues++;
            }

            System.out.println("Nombre de parties jouées: " + compteurParties);
            System.out.println("Nombre de parties gagnées: " + compteurGagnees);
            System.out.println("Nombre de parties perdues: " + compteurPerdues);

            System.out.print("Résultat de toutes les parties jouées: ");
            for (int iii=0; iii<resultats.length; iii++)
                System.out.print(resultats[iii] + "/10 ");

            moyenne = moyenneTableau(resultats);
            System.out.println("\n\nMoyenne des résultats: " + df.format(moyenne) + "%");

            rejouer = validerRejouer();
        }
        while (rejouer == true);
    }

    /**
     * Objectif: Demander au joueur la table de multiplication.
     * @return l'entier correspondant à la table choisie.
     */

    public static int choisirTable(){
        Scanner scan = new Scanner(System.in);
        int table = 0; //Table de multiplication choisie.
        boolean valide = false; //Variable de contrôle.

        do {
            System.out.print("-> Choisir une table (2 à 12): ");

            try {
                table = scan.nextInt();

                if (table>1 && table<13)
                    valide = true;
            }

            catch (InputMismatchException e) {
                scan.next();
            }
        }
        while (!valide);

        System.out.print("\n");

        return table;
    }

    /**
     * Objectif: Générer un nombre aléeatoire de 2 à 12 (inclus).
     * @return l'entier aléatoire correspondant au second opérande.
     */

    public static int genererNbAleatoire(){
        Random rand = new Random();
        int nbAleatoire = rand.nextInt(11) + 2; //Entier aléatoire représentant le second opérande

        return nbAleatoire;
    }

    /**
     * Objectif: Demander au joueur s'il veut refaire une partie.
     * @return un booléen représentant la volonté du joueur.
     */

    public static boolean validerRejouer(){
        Scanner scan = new Scanner(System.in);
        boolean rejouer = false; //Variable pour savoir si on recommence ou pas.
        char reponse = 'n'; //Réponse du joueur.
        boolean valide = false; //Variable de contrôle.

        do {
            System.out.print("\n" + ANSI_RESET + "Voulez-vous rejouer encore (o/n) ? ");

            try {
                reponse = scan.next().charAt(0);

                if (reponse == 'o' || reponse == 'n' || reponse == 'O' || reponse == 'N')
                    valide = true;
            }

            catch (InputMismatchException e) {
                scan.next();
            }
        }
        while (!valide);

        System.out.print("\n");

        if (reponse == 'o' || reponse == 'O')
            rejouer = true;

        return rejouer;
    }

    /**
     * Objectif: Prendre le tableau des résultats et le recopier dans un nouveau tableau avec un élément en plus pour le nouveau résultat.
     * @param resultats (Le tableau avec un élément manquant.)
     * @param nbElem (Le nombre d'éléments dans le tableau.)
     * @return un nouveau tableau avec un élément en plus.
     */

    public static int[] addElemTableau(int[] resultats, int nbElem){
        int[] newResultats = new int[nbElem]; //Nouveau tableau avec une place pour le nouveau pointage.

        for (int i=0; i<nbElem-1; i++) {
            newResultats[i] = resultats[i];
        }
        
        return newResultats;
    }

    /**
     * Objectif: Faire la moyenne de tous les éléments d'un tableau.
     * @param resultats (Le tableau de résultats à calculer)
     * @return un réel correspondant à la moyenne. En pourcentage.
     */

    public static double moyenneTableau(int[] resultats) {
        double moyenne = 0; //Réel représentant la moyenne des résultats de toutes les parties.

        for (int i=0; i<resultats.length; i++){
            moyenne += resultats[i];
        }
        moyenne /= resultats.length;
        moyenne *= 10;

        return moyenne;
    }

}

