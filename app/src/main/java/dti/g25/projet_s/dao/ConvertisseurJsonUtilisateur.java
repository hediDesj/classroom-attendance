package dti.g25.projet_s.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dti.g25.projet_s.domaine.entité.Horaire;
import dti.g25.projet_s.domaine.entité.Role;
import dti.g25.projet_s.domaine.entité.Seance;
import dti.g25.projet_s.domaine.entité.Utilisateur;
import dti.g25.projet_s.domaine.interacteurs.CréeationUtilisateur;
import dti.g25.projet_s.domaine.interacteurs.GestionSeance;

public class ConvertisseurJsonUtilisateur {

    public List<Utilisateur> obtenirListeUtilisateurs(JSONObject résultat) throws Exception {
        List<Utilisateur> utilisateurs= new ArrayList<>();

        résultat = (JSONObject) résultat.get("_embedded");
        Log.d("embedded", String.valueOf(résultat));
        résultat = (JSONObject) résultat.get("membres");
        JSONArray listeSeance = résultat.names();

        for(int i = 0; i < listeSeance.length(); i++) {
            JSONObject objectAcuel = (JSONObject) résultat.get(listeSeance.getString(i));

            Log.d("une seance", String.valueOf(objectAcuel));

            Utilisateur utilisateur = new CréeationUtilisateur().CréerUtilisateur(objectAcuel.getInt("id"), objectAcuel.getString("nom"), obtenirRôle(objectAcuel.getInt("id")));

            utilisateurs.add(utilisateur);
        }

        return utilisateurs;
    }


    private Role obtenirRôle(int i) {
        if(i == 2) {
            return Role.PROFESSEUR;
        } else if( i == 3) {
            return Role.ADMINISTRATEUR;
        }

        return Role.ÉLÈVE;
    }
}