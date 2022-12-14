package dti.g25.projet_s.domaine.interacteurs;

import dti.g25.projet_s.domaine.entité.Role;
import dti.g25.projet_s.domaine.entité.Utilisateur;

public class CréeationUtilisateur implements ICéeationUtilisateur {

    public Utilisateur CréerUtilisateur(int id, String unNom, Role unRôle) throws Exception {


        if(unNom.equals("") || unNom.isEmpty())
            throw new Exception("l'utilisateur n'a pas de nom est vide");
        if(unRôle.equals(null))
            throw new Exception("l'utilisateur n'a pas de rôle");

        return new Utilisateur(id, unNom, unRôle);
    }

    public Utilisateur CréerUtilisateur(String unNom, Role unRôle) throws Exception {


        if(unNom.equals("") || unNom.isEmpty())
            throw new Exception("l'utilisateur n'a pas de nom est vide");
        if(unRôle == null)
            throw new Exception("l'utilisateur n'a pas de rôle");

        return new Utilisateur(unNom, unRôle);
    }

}
