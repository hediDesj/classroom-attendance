package dti.g25.projet_s.présentation.présenteur;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import dti.g25.projet_s.dao.DAOFactoryRESTAPI;
import dti.g25.projet_s.dao.DAOUtilisateurRESTAPI;
import dti.g25.projet_s.présentation.IContatVuePresenteurVoirCoursGroupe;
import dti.g25.projet_s.présentation.modèle.Modèle;
import dti.g25.projet_s.présentation.modèle.dao.ModèleDAO;
import dti.g25.projet_s.ui.activité.ConnexionActivité;
import dti.g25.projet_s.ui.activité.VoirUnCourGroupeActivity;

public class PresenteurVoirCoursGroupe implements IContatVuePresenteurVoirCoursGroupe.IPresenteurVoirCoursGroupe {
    private static final String EXTRA_CLÉ_CONNEXION = "dti.g25.projet_s.cléConnexion";
    private static final String EXTRA_POSITION_GROUPE = "dti.g25.projet_s.positionCourGroupe";
    private static final int REQUEST_CODE_CONEXION= 55;
    private String cléConnexion;

    private IContatVuePresenteurVoirCoursGroupe.IVueVoirCoursGroupe vueVoirCoursGroupe;
    private Modèle modèle;
    private Activity activity;

    public PresenteurVoirCoursGroupe(IContatVuePresenteurVoirCoursGroupe.IVueVoirCoursGroupe vueVoirCoursGroupe, Modèle modèle, Activity activity) {
        this.vueVoirCoursGroupe = vueVoirCoursGroupe;
        this.modèle = modèle;
        this.activity = activity;
        if(modèle.getUtilisateurConnecte() == null)
            activity.startActivityForResult(new Intent(activity, ConnexionActivité.class), REQUEST_CODE_CONEXION);
    }

    @Override
    public void rafraîchir() {
        vueVoirCoursGroupe.rafraichir();
    }

//    public void telechargerCoursGroupeUtilisateur(final Intent data){
//
//        //C'est ici qu'on lance le traitement sur le fil esclave
//        Thread esclave = new Thread(
//                new Runnable(){
//                    // Ce Runnable s'exécute dans le fil esclave
//                    public void run(){
//                        try{
//                            //L'interacteur fait la requête à l'API
//                            cléConnexion=data.getStringExtra(EXTRA_CLÉ_CONNEXION);
//                            modèle.setCléConnexion(cléConnexion);
//                            DAOUtilisateurRESTAPI daoUtilisateurRESTAPI = new DAOUtilisateurRESTAPI(cléConnexion, activity);
//                            Log.i("Id utilisateurDAO", Integer.toString(daoUtilisateurRESTAPI.getId()));
//                            modèle.setUtilisateur(new DAOUtilisateurRESTAPI(1, cléConnexion, null,activity));
//                            modèle.chargerCoursGroupeUtilisateur();
//                            //Lancera exception si le cours groupe est null
//                            //Lorsqu'il a terminé, il lance une tâche sur le fil principal (UI)
//                            activity.runOnUiThread(
//                                    new Runnable(){
//                                        //Ce Runnable s'exécute dans le fil principal (UI)
//                                        public void run(){
//                                            try{
//                                                Log.d("Présenteur", "rafraichit vue");
//                                                vueVoirCoursGroupe.rafraichir();
//                                            } catch ( Exception e) {
//                                                Log.e("CoursGroupe", "Erreur d'écriture");
//                                            }
//
//                                        }
//                                    });
//                        }
//                        catch(final Exception e){
//                            //Si un problème est survenu, on envoit l'erreur au IReceveurDeDonnées
//                            activity.runOnUiThread(new Runnable(){
//                                //Ce Runnable s'exécute dans le fil principal (UI)
//                                public void run(){
//                                    Log.e("nombres", "Erreur d'accès à l'API", e);
//                                }
//                            });
//                        }
//
//                    }
//                });
//
//        //Finalement, tout est prêt à être lancé
//        esclave.start();
//
//    }


    @Override
    public int getNombresItems() {
        if(modèle.getCoursGroupes() != null) {
            return modèle.getCoursGroupes().size();
        }
        return 0;
    }

    @Override
    public void requeteVoirCoursGroupe(int position) {
        Intent intentVoirSéance = new Intent(activity, VoirUnCourGroupeActivity.class);
        intentVoirSéance.putExtra(EXTRA_CLÉ_CONNEXION, cléConnexion);
        intentVoirSéance.putExtra(EXTRA_POSITION_GROUPE, position);
        activity.startActivity(intentVoirSéance);
    }

    @Override
    public String getTitreCoursGroupe(int position) {
        return modèle.getCoursGroupes().get(position).toString();
    }
}
