package org.bwbc.repositories;

import java.util.List;

import org.bwbc.beans.Acteur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActeurRepository extends CrudRepository<Acteur, Long> {

	/** Recherche tous les acteurs triés par identité
	 * @return List
	 */
	@Query("From Acteur a ORDER BY identite ASC")
	List<Acteur> findActeursTriesParIdentite();
	
	/** Recherche tous les acteurs dont l'identité (ou partie) est passée en paramètre
	 * @param identite identité
	 * @return List
	 */
	@Query("From Acteur a WHERE a.identite LIKE %:identite%")
	List<Acteur> findActeursParIdentite(String identite);
	
	/** Recherche tous les acteurs nés l'année passée en paramètre. Les acteurs sont triés par date de naissance ascendante.
	 * @param annee année
	 * @return List
	 */
	@Query("From Acteur a WHERE YEAR(a.anniversaire)=:annee ORDER BY YEAR(a.anniversaire)")
	List<Acteur> findActeursParAnneeNaissance(int annee);


	/** Recherche les acteurs dont le nom de personnage est passé en paramètre
	 * @param roleName nom du personnage de fiction
	 * @return List
	 */
	@Query("From Acteur a JOIN a.roles r WHERE r.nom=:roleName")
	List<Acteur> findActeursParRole(String roleName);
		
	/** Recherche tous les acteurs ayant joué dans un film paru l'année passée en paramètre.
	 * @param annee année
	 * @return List
	 */
	@Query("From Acteur a JOIN a.roles r JOIN r.film f WHERE f.annee=:annee")
	List<Acteur> findActeursParFilmParuAnnee(@Param("annee") int annee);

	/** Recherche tous les acteurs ayant joué dans un film produit dans le pays dont le nom est passé en paramètre
	 * @param nom nom du pays
	 * @return List
	 */
	@Query("SELECT distinct a From Acteur a JOIN a.roles r JOIN r.film f JOIN f.pays p WHERE p.nom=:nom")
	List<Acteur> findActeursParPays(String nom);
	
	/** Recherche tous les acteurs ayant joué dans un film paru l'année passée en paramètre et produit dans un des pays passés en paramètre
	 * @param noms noms des pays de production des films
	 * @param annee année de parution
	 * @return List
	 */
	@Query("SELECT distinct a From Acteur a JOIN a.roles r JOIN r.film f JOIN f.pays p WHERE p.nom in (:noms) and f.annee=:annee")
	List<Acteur> findActeursParListePaysEtAnnee(List<String> noms, int annee);
	
	/** Recherche tous les acteurs ayant joué dans un film réalisé par la personne dont l'identité est passée en paramètre. Seuls les films dont l'année
	 * de paution est comprise entre anneeMin et anneeMax sont concernés.
	 * @param identite identité du réalisateur
	 * @param anneeMin année min de recherche
	 * @param anneeMax année max de recherche
	 * @return List
	 */
	@Query("From Acteur")
	List<Acteur> findActeursParRealisateurEntreAnnees(String identite, int anneeMin, int anneeMax);
	
}