package dti.g25.projet_s.présentation;

import android.content.Intent;

import dti.g25.projet_s.domaine.entité.Role;
import dti.g25.projet_s.domaine.entité.Utilisateur;

public interface ContratVuePrésenteurVoirListeÉlèves {

    public interface IPrésenteurVoirListeÉlèves {

        int getNombresItems();

        Utilisateur getUtilisateurParPosition(int position);

        String getPrésenceUtilisateurParPos(int position) throws Exception;

        void requeteVoirÉlèves(int position);

        void commencerListeÉlèvesPrésence(int positionSeance, int positionCoursGroupe, String cléUtilisateur) throws Exception;

        Boolean getpeutPrendrePrésence();

        void rafraîchir();
    }

    public interface IVueVoirListeÉlèves {

        void rafraichir();

        void setBoutonPrésence(boolean b);
    }
}
