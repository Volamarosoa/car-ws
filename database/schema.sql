create table categorie(
	id_categorie serial primary key,
	nom_categorie varchar(50) not null,
	deleted boolean default false
);

create table carburant(
	id_carburant serial primary key,
	nom_carburant varchar(50) not null,
	deleted boolean default false
);

create table modele(
	id_modele serial primary key,
	nom_modele varchar(50) not null,
	deleted boolean default false
);

create table transmission(
	id_transmission serial primary key,
	nom_transmission varchar(50) not null,
	deleted boolean default false
);

create table marque(
	id_marque serial primary key,
	nom_marque varchar(50) not null,
	deleted boolean default false
);

create table users(
	id_user text not null primary key,
	nom_user varchar(50) not null,
	prenom varchar(50) ,
	mail varchar(60) not null,
	contact varchar(50),
	password varchar(100),
	date_naissance date
);

create table annonce(
	id_annonce serial primary key,
	date_heure_publication timestamp,
	id_user text not null,
	prix double precision,
	foreign key(id_user) REFERENCES users( id_user )
);

create table validate_annonce(
	id_annonce_valide serial primary key,
	id_annonce integer not null,
	date_time_validation timestamp,
	foreign key(id_annonce) REFERENCES annonce( id_annonce )
);


create table voiture_annonce(
	id_voiture_annonce serial primary key,
	id_marque integer not null,
	id_modele integer not null,
	id_carburant integer not null,
	id_categorie integer not null,
	id_transmission integer not null,
	foreign key( id_marque ) REFERENCES marque(id_marque),
	foreign key( id_modele ) REFERENCES modele(id_modele),
	foreign key( id_carburant ) REFERENCES carburant(id_carburant),
	foreign key( id_categorie ) REFERENCES categorie(id_categorie),
	foreign key( id_transmission ) REFERENCES transmission(id_transmission)
);

create table detail_annonce(

	id_detail_annonce serial primary key,
	id_annonce integer not null,
	description text,
	marque integer not null,
	modele integer not null,
	id_voiture_annonce integer not null,
	kilometrage double precision,
	foreign key(id_voiture_annonce) REFERENCES voiture_annonce(id_voiture_annonce),
	foreign key(id_annonce) REFERENCES annonce(id_annonce)
);

-- Okey zavatra hafa indray zao


create table annonce_vendus(
	id_annonce_vendus serial primary key,
	date_vendus date,
	id_annonce integer not null,
	foreign key(id_annonce) REFERENCES annonce( id_annonce )
);

create table annonce_photo(
	id_photo serial primary key,
	id_annonce integer not null,
	photo text,
	foreign key(id_annonce) REFERENCES annonce (id_annonce)
);

create table favoris(
	id_favoris serial primary key,
	id_annonce integer not null,
	id_user text not null,
	date_ajout date,
	foreign key( id_annonce ) REFERENCES annonce(id_annonce),
	foreign key( id_user ) REFERENCES users(id_user)
);

create table discussion(
	id_discussion serial primary key,
	id_envoyeur text not null,
	id_receveur text not null,
	foreign key(id_envoyeur) REFERENCES users( id_user ),
	foreign key(id_receveur) REFERENCES users( id_user )
);

create table message(
	id_messsage serial primary key,
	id_discussion integer not null,
	date_heure_envoie timestamp,
	message text,
	foreign key( id_discussion ) REFERENCES discussion(id_discussion)
);

create table mois(
	id varchar(4) not null primary key,
	nom varchar(50) unique not null,
	reference integer not null
);

drop view if exists one_annonce_to_month;
create or replace view one_annonce_to_month
	as		
		select
			*
		from mois as m , annonce as a;

drop view if exists v_stats cascade;
create or replace view v_stats
	as

		select
			o_a_m.nom,
			o_a_m.reference,
			count( distinct(a.id_annonce) ) as nbr_annonce,
			extract( YEAR from a.date_heure_publication ) as year
		from one_annonce_to_month as o_a_m
		left join annonce as a
		on extract ( month from a.date_heure_publication ) = o_a_m.reference
		group by o_a_m.nom, o_a_m.reference, extract( YEAR from a.date_heure_publication )
		order by o_a_m.reference;

drop view if exists v_stats_month;
create or replace view v_stats_month
	as
		select
			v_s.nom, SUM(v_s.nbr_annonce) as total
		from v_stats as v_s
		group by v_s.nom, v_s.reference
		order by v_s.reference
;

select id_annonce from v_stats_month group by id_annonce ;


-- Donc mila miketrika kely fotsiny
-- Ny affichage farany zany tokony hoe
-- Janvier : count
-- Fevrier : count
-- Ahoana no anaovana izany
-- Andao ketrehana kely