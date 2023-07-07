
package edu.connection.entities;



import java.time.LocalDate;

/**
 *
 * @author ASUS
 */
public class Offre {
    private int idOffre;
    private String jobHolder;
    private String lieuT;
    private String descriptif;
    private String skills;
    private String company;
    private LocalDate dateDebutOffre;
    private LocalDate dateFinOffre;
    private TypeEmploi typeEmploi;
    private TypeLieuTravail typeLieuTravail;
    private ReceptionOfApplication receptionOfApplication;

    
    public Offre() {
    }

    public Offre(int idOffre, String jobHolder, String lieuT, String descriptif, String skills, String company, LocalDate dateDebutOffre, LocalDate dateFinOffre, TypeEmploi typeEmploi, TypeLieuTravail typeLieuTravail, ReceptionOfApplication receptionOfApplication){//, int idUser, int poste) {
        this.idOffre = idOffre;
        this.jobHolder = jobHolder;
        this.lieuT = lieuT;
        this.descriptif = descriptif;
        this.skills = skills;
        this.company = company;
        this.dateDebutOffre = dateDebutOffre;
        this.dateFinOffre = dateFinOffre;
        this.typeEmploi = typeEmploi;
        this.typeLieuTravail = typeLieuTravail;
        this.receptionOfApplication = receptionOfApplication;
       // this.idUser = idUser;
        //this.poste = poste;
    }
    public Offre(String jobHolder, String lieuT, String descriptif, String skills, String company, LocalDate dateDebutOffre, LocalDate dateFinOffre, TypeEmploi typeEmploi, TypeLieuTravail typeLieuTravail, ReceptionOfApplication receptionOfApplication){//, int idUser, int poste) {
        this.jobHolder = jobHolder;
        this.lieuT = lieuT;
        this.descriptif = descriptif;
        this.skills = skills;
        this.company = company;
        this.dateDebutOffre = dateDebutOffre;
        this.dateFinOffre = dateFinOffre;
        this.typeEmploi = typeEmploi;
        this.typeLieuTravail = typeLieuTravail;
        this.receptionOfApplication = receptionOfApplication;
 
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.idOffre;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Offre other = (Offre) obj;
        if (this.idOffre != other.idOffre) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        return "Offre{" + "idOffre=" + idOffre + ", jobHolder=" + jobHolder + ", lieuT=" + lieuT + ", descriptif=" + descriptif + ", skills=" + skills + ", company=" + company + ", dateDebutOffre=" + dateDebutOffre + ", dateFinOffre=" + dateFinOffre + ", typeEmploi=" + typeEmploi + ", typeLieuTravail=" + typeLieuTravail + ", receptionOfApplication=" + receptionOfApplication +'}';
    }

    public String getJobHolder() {
        return jobHolder;
    }

    public void setJobHolder(String  jobHolder) {
        this.jobHolder = jobHolder;
    }
    
    

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }



    public String getLieuT() {
        return lieuT;
    }

    public void setLieuT(String lieuT) {
        this.lieuT = lieuT;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDate getDateDebutOffre() {
        return dateDebutOffre;
    }

    public void setDateDebutOffre(LocalDate dateDebutOffre) {
        this.dateDebutOffre = dateDebutOffre;
    }

    public LocalDate getDateFinOffre() {
        return dateFinOffre;
    }

    public void setDateFinOffre(LocalDate dateFinOffre) {
        this.dateFinOffre = dateFinOffre;
    }

    public TypeEmploi getTypeEmploi() {
        return typeEmploi;
    }

    public void setTypeEmploi(TypeEmploi typeEmploi) {
        this.typeEmploi = typeEmploi;
    }

    public TypeLieuTravail getTypeLieuTravail() {
        return typeLieuTravail;
    }

    public void setTypeLieuTravail(TypeLieuTravail typeLieuTravail) {
        this.typeLieuTravail = typeLieuTravail;
    }

    public ReceptionOfApplication getReceptionOfApplication() {
        return receptionOfApplication;
    }

    public void setReceptionOfApplication(ReceptionOfApplication receptionOfApplication) {
        this.receptionOfApplication = receptionOfApplication;
    }

    
    
    
    

}