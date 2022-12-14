package dti.g25.projet_s.domaine.entité;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import dti.g25.projet_s.domaine.entité.Horaire;

public class Seance {
    private CoursGroupe _coursGroupe;
    private List<Absence> _listeAbsence;
    private EtatSeance _etat;
    private Horaire _horaires;
    private int id;

    public Seance(CoursGroupe coursGroupe, Horaire horaires){
        _coursGroupe=coursGroupe;
        _listeAbsence = new ArrayList<Absence>();
        _horaires = horaires;

        if(coursGroupe.getParticipants()!=null){
            for (Utilisateur user : _coursGroupe.getParticipants()) {
                if(user.getRôle().equals(Role.ÉLÈVE))
                    _listeAbsence.add(new Absence(user, false));
            }
        }
        _etat=EtatSeance.PREVUE;
    }

    public Seance(CoursGroupe coursGroupe, Horaire horaires, int id) {
        _coursGroupe=coursGroupe;
        _listeAbsence = new ArrayList<Absence>();
        _horaires = horaires;
        this.id = id;

        if(coursGroupe.getParticipants()!=null){
            for (Utilisateur user : _coursGroupe.getParticipants()) {
                if(user.getRôle().equals(Role.ÉLÈVE))
                    _listeAbsence.add(new Absence(user, false));
            }
        }
        _etat=EtatSeance.PREVUE;
    }

    public void set_listeAbsence(List<Absence> _listeAbsence) {
        this._listeAbsence = _listeAbsence;
    }

    public Horaire get_horaires() {
        return _horaires;
    }

    public void set_horaires(Horaire _horaires) {
        this._horaires = _horaires;
    }

    public void set_coursGroupe(CoursGroupe _coursGroupe) {
        this._coursGroupe = _coursGroupe;
    }

    public CoursGroupe get_coursGroupe() {
        return _coursGroupe;
    }

    public EtatSeance get_etat() {
        return _etat;
    }

    public void set_etat(EtatSeance _etat) {
        this._etat = _etat;
    }

    public List<Absence> getListeAbsence() {
        return _listeAbsence;
    }

    public void setListeAbsence(List<Absence> listeAbsence) {
        this._listeAbsence = listeAbsence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seance seance = (Seance) o;

        if (_coursGroupe != null ? !_coursGroupe.equals(seance._coursGroupe) : seance._coursGroupe != null)
            return false;
        if (_etat != seance._etat)
            return false;

        return _horaires != null ? _horaires.equals(seance._horaires) : seance._horaires == null;
    }

}
