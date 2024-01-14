package com.example.carws.model.statistics;

import jakarta.persistence.*;

@Entity
@Table( name = "v_stats" )
public class Statistique {
	
	@Id
	@Column( name = "nom" )
	String label;
	@Column( name = "total" )
	Integer nombreAnnonce;
	@Column( name = "annee" )
	Integer annee;

	public void setLabel(String label){
		this.label = label;
	}

	public String getLabel(){
		return this.label;
	}

	public void setNombreAnnonce( Integer nombre ){
		this.nombreAnnonce = nombre;
	}

	public Integer getNombreAnnonce(){
		return this.nombreAnnonce;
	}

	public void setAnnee( Integer an ){
		this.annee = an;
	}

	public Integer getAnnee(){
		return this.annee;
	}


	// Okey eto de azoko daholo ny statistique rehetra ilaiko
	// Inona no ilaiko manaraka
	// Andao ange jerena eh
	// Manao service afahana milalao azy tsara
	// Let's go ary eh


}