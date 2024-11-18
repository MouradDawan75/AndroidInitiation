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
