package org.bwbc.repositories;

import java.util.List;

import org.bwbc.beans.Film;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends CrudRepository<Film, Long> {

	/** Recherche tous les films triés par ordre alphabétique
	 * @return List
	 */
	@Query("From Film f ORDER BY f.nom ASC")
	List<Film> findFilmsTriesParNom();

	/** Recherche tous les films triés par année ascendante
	 * @return
	 */
	@Query("From Film f ORDER BY f.annee ASC")
	List<Film> findFilmsTriesParAnnee();

	/** Recherche un film à partir d'une partie de son nom
	 * @param nom nom du film
	 * @return List
	 */
	@Query("From Film f WHERE f.nom LIKE %:nom%")
	List<Film> findFilmsParNom(String nom);

	/** Retourne tous les films parus l'année passée en paramètre et triés par nom
	 * @param annee année
	 * @return List
	 */
	@Query("From Film f WHERE f.annee=:annee ORDER BY f.nom")
	List<Film> findFilmsParAnnee(int annee);

	/** Retourne tous les films pour un genre donné
	 * @param genre genre recherché
	 * @return List
	 */
	@Query("From Film a JOIN a.genres g WHERE g.nom=:genre")
	List<Film> findFilmsParGenre(String genre);
	
	/** Retourne tous les films réalisés par un réalisateur donné entre 2 dates, triés par année
	 * @param identite identité du réalisateur
	 * @param minAnnee année min
	 * @param maxAnnee année max
	 * @return List
	 */
	
	@Query("From Film f JOIN f.realisateurs r WHERE r.identite=:identite and f.annee between :minAnnee and :maxAnnee ORDER BY f.annee")
	List<Film> findFilmsParRealisateurEtAnneesTriesParAnnee(String identite, int minAnnee, int maxAnnee);

	/** Recherche les films ayant un certain nombre de genres.
	 * @param nbGenres nombre de genres
	 * @return List
	 */
	@Query("From Film f WHERE SIZE(f.genres)=:nbGenres ")
	List<Film> findFilmsParNbGenres(int nbGenres);
	
	/** Recherche les films dans lesquels les 2 personnes passées en paramètre ont joué.
	 * @param identite1 identité de la personne 1
	 * @param identite2 identité de la personne 2
	 * @return List
	 */
	@Query("From Film f")
	List<Film> findFilmsAvecPlusieursActeurs(String identite1, String identite2);

}