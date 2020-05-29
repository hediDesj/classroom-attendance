package dti.g25.projet_s.dao;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import dti.g25.projet_s.domaine.entité.CoursGroupe;
import dti.g25.projet_s.domaine.entité.Horaire;
import dti.g25.projet_s.domaine.entité.LibelleCours;
import dti.g25.projet_s.domaine.entité.Seance;
import dti.g25.projet_s.domaine.entité.Utilisateur;
import dti.g25.projet_s.domaine.interacteurs.GestionCoursGroupe;
import dti.g25.projet_s.présentation.modèle.dao.DAO;
import dti.g25.projet_s.présentation.modèle.dao.DAOFactoryV1;

import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.ErrorListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class DAOFactoryRESTAPI extends DAOFactoryV1 {
    private static final String TOKEN = "";
    private static final String NOM_UTILISATEUR = "username";
    private static final String MOT_PASSE = "password";
    private static final String URL = "https://projet-s.dti.crosemont.quebec/api/v0/utilisateurs";
    private static final String URL_CONNECT = "https://projet-s.dti.crosemont.quebec/";
    private static final String POINT_ENTREE_UTILISATEURS = "utilisateurs";
    private static final String TAG = "DAOFactoryRESTAPI" ;
    private final static String seanceparGroupeGet = "https://projet-s.dti.crosemont.quebec/api/v1/groupe/";
    private static final String CNX_GET_POINT_ENTREE = "https://projet-s.dti.crosemont.quebec/api/v1/" ;
    private int idUserConnect=0;
    private  Context context;
    private  String cle;
    private Response.Listener<JSONObject> response;
    private static List<DAO<CoursGroupe>> coursGroupes;


    public DAOFactoryRESTAPI(Context context) {
        this.context = context;

    }

    @Override
    public List<DAO<CoursGroupe>> chargerListeCoursGroupeParUtilisateur(final DAO<Utilisateur> utilisateurDAO) {
        coursGroupes= new ArrayList<>();
        if(!(utilisateurDAO instanceof DAOUtilisateurRESTAPI)){return null;}
        System.out.println("Chargement des cours groupes....");
        final DAOUtilisateurRESTAPI utilisateurREST= (DAOUtilisateurRESTAPI)  utilisateurDAO;
        RequestQueue queue= Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CNX_GET_POINT_ENTREE+"utilisateur/"+utilisateurREST.getId()+"/groupes", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Iterator<String> iterator = response.keys();
                    while (iterator.hasNext()){
                        JSONObject jsonObject =response.getJSONObject(iterator.next());
                        int idCg = jsonObject.getInt("id");
                        String titre = jsonObject.getString("titre");
                        String sigle = jsonObject.getString("sigle");
                        System.out.println(titre);
                        int num = jsonObject.getInt("num\u00e9ro");
                        CoursGroupe cG = new GestionCoursGroupe().creerCoursGroupe( new LibelleCours(titre,sigle), num);
                        coursGroupes.add(new DAOCoursGroupeRESTAPI(idCg,utilisateurREST.getCle(),cG, context));
                        Log.i(TAG, titre);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+utilisateurREST.getCle());
                return params;
            }
        };

        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        return coursGroupes;
    }

    @Override
    public List<DAO<Utilisateur>> chargerListeUtilisateursParCoursGroupe(DAO<CoursGroupe> coursGroupeDAO) {
        return null;
    }

    @Override
    public  List<DAO<Seance>> chargerListeSeanceParCoursGroupe(DAO<CoursGroupe> coursGroupeDAO) {
        return null;
    }

    @Override
    public DAO<CoursGroupe> chargerCoursGroupeParSeance(DAO<Seance> seanceDAO) {
        return null;
    }

    @Override
    public List<DAO<Seance>> chargerListeSeanceParUtilisateur(DAO<Utilisateur> utilisateurDAO) {
        return null;
    }

    @Override
    public List<DAO<Utilisateur>> chargerListeUtilisateurParCoursGroupe(DAO<CoursGroupe> coursGroupeDAO) {
        return null;
    }

    @Override
    public List<DAO<Utilisateur>> chargerListeUtilisateurParSeance(DAO<Seance> seanceDAO) {
        return null;
    }

    @Override
    public List<DAO<Horaire>> chargerHoraireParCoursGroupe(DAO<CoursGroupe> coursGroupeDAO) {
        return null;
    }

    @Override
    public List<DAO<CoursGroupe>> chargerCoursGroupeParHoaire(DAO<Horaire> horaireDAO) {
        return null;
    }

    @Override
    public String tenterConnection(String nomUtilisateur, String motDePasse) {
        return null;
    }


    @Override
    public String tenterConnection(final String nomUtilisateur, final String motDePasse, Response.ErrorListener errorResponse) {
        final String CNX_GET="https://projet-s.dti.crosemont.quebec/api/v1/auth_token";
        ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CNX_GET, null, response, errorResponse) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", nomUtilisateur, motDePasse).getBytes(), Base64.DEFAULT)));
                return params;
            }
        };

        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        return cle;
    }

    @Override
    public void getSeancesParCourGroupe(Response.Listener<JSONObject> response, CoursGroupe courGroupe){
        List<Seance> listSeance = new ArrayList<>();
        String url = seanceparGroupeGet + courGroupe.getId() + "?embed=true";

        JSONObject jsonn = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url , jsonn, response
            , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Bearer "+cle);
                return headers;
            }
        };
        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void getListeÉlèvesCour(Response.Listener<JSONObject> response, CoursGroupe courGroupe){
        String url = "https://projet-s.dti.crosemont.quebec/api/v1/groupe/1?embed=true";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url , null, response
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Bearer "+cle);
                return headers;
            }
        };
    }

    @Override
    public void prendrePrésence() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,"https://projet-s.dti.crosemont.quebec/api/v1/groupe/1?embed=true" , null, response
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Authorization:", "Bearer "+cle);
                return headers;
            }
        };
        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void obtenirPrésence() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,"https://projet-s.dti.crosemont.quebec/api/v1/groupe/1?embed=true" , null, response
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }


    @Override
    public String getCle() {
        return cle;
    }



    public void setResponse(Response.Listener<JSONObject> response) {
        this.response = response;
    }
}
