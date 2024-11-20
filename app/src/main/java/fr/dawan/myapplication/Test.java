package fr.dawan.myapplication;

public class Test {

    //Pas de l'initialiser, car elle possède une valeur par défaut
    /*
    Types numériques: int, float, double = 0
    Type boolean: false
    Types complèxes: null

     */
    int x;

    //méthode d'instance
    public void methode1() {
        //variable locale: doit être initialisée
        int y = 0;

    }

    //méthode (static) de classe
    public static void methode2() {
        Test t = new Test();
    }
    /*
    3 types d'erreurs possibles dans un code:
    - Erreurs de compilations (de syntaxte): sont detectées automatiquement par l'IDE
    - Exception: est une erreur qui provoque l'arrêt de l'application
    - Code fonctionnel, qui renvoi un résultat inattendu (faire du debuggage)

    Pour éviter l'arrêt de l'application, o doit gérer l'exception
    Pour gérer une exception, on utilise e bloc try/catch
     */
}
/*
Une classe est un type de données. Elle a pour tâche principale de décrire la structure d'un objet. Elle définie
une sorte de template à partir duquel on crée nos objet.
Elle contient généralement 3 choses:
- Attributs - propriétés de l'objet (privés + geter/setter)
- Des méthodes
- Une méthode spéciale qui porte le nom de classe, appelée constructeur qui permet d'instancier (créer des objets) à partir
de la classe en question.

 */
