# Tp3_Service-oriented-computing

#On supposera que la bibliothèque est déployée à l'adresse BIBLIO et le portail à l'adresse PORTAIL.

- Repertoire {

	@PUT
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	@ReponsesPUTOption
	// Requête (méthode http + url) : (put) http://BIBLIO/biblioteque ou (put) http://PORTAIL/portail
	// Corps : Livre
	// Réponses (à spécifier par code) : 
	// - 404 : Pas de resultats
	// - 200 : TYPE_MEDIA contenant un HyperLien<Livre>
	Optional<HyperLien<Livre>> chercher(Livre l);
  

	@PUT
	@ReponsesPUTOption
	@Path(JAXRS.SOUSCHEMIN_ASYNC)
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : (put) http://BIBLIO/biblioteque/async ou (put) http://PORTAIL/portail/async
	// Corps : Livre 
	// Réponses (à spécifier par code) :
	// - 404 : Pas de resultats
	// - 200 : TYPE_MEDIA contenant un HyperLien<Livre>
	Future<Optional<HyperLien<Livre>>> chercherAsynchrone(Livre l, @Suspended final AsyncResponse ar);

	@GET
	@Path(SOUSCHEMIN_CATALOGUE)
	@Produces(TYPE_MEDIA)
	// Requête (méthode http + url) : (GET) http://BIBLIO/biblioteque/catalogue ou (GET) http://PORTAIL/portail/catalogue
	// Corps : VIDE
	// Réponses (à spécifier par code) :
	// - 200 : List<HyperLien<Livre>>
	HyperLiens<Livre> repertorier();

- Archive 
	@Path("{id}")
	@ReponsesGETNullEn404
	// Adresse de la sous-ressource : biblioteque
	// Requête sur la sous-ressource (méthode http + url) : 
	// Corps : 
	// Réponses (à spécifier par code) :
	// - 200 : IdentifiantLivre
	Livre sousRessource(@PathParam("id") IdentifiantLivre id) ;

	@Path("{id}")
	@GET 
	@Produces(JAXRS.TYPE_MEDIA)
	@ReponsesGETNullEn404
	// Requête (méthode http + url) : (GET) http://BIBLIO/biblioteque/{id}
	// Corps : id du livre
	// Réponses (à spécifier par code) :
	// - 200 : IdentifiantLivre
    // - 404 : si null
	Livre getRepresentation(@PathParam("id") IdentifiantLivre id);

	@POST
	@ReponsesPOSTEnCreated
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : (POST) http://BIBLIO/biblioteque/
	// Corps : Livre
	// Réponses (à spécifier par code) :
	// - 201 : Hyperlien<Livre>
	HyperLien<Livre> ajouter(Livre l);
}

- AdminAlgo
	@PUT
	@Path(JAXRS.SOUSCHEMIN_ALGO_RECHERCHE)
	@Consumes(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : http://PORTAIL/admin/recherche
	// Corps : NomAlgo
	// Réponses (à spécifier par code) :
	// - VIDE 
	void changerAlgorithmeRecherche(NomAlgorithme algo);


- Exemple de livre
<livre>
  <titre></titre>
</livre>
  
- Exemple de nom d'algo

<algo nom="">
</algo>


-- Analyse des résultats : 

*************
recherche sync seq
Réalisé en : 7703ms
*************
recherche sync multi
Réalisé en : 1543ms
*************
recherche sync stream 8
Réalisé en : 1532ms
*************
recherche sync stream rx
Réalisé en : 7602ms
*************
recherche async seq
Réalisé en : 7540ms
*************
recherche async multi
Réalisé en : 1519ms
*************
recherche async stream 8
Réalisé en : 1524ms
*************
recherche async stream rx
Réalisé en : 7534ms

Bilan : Les streams semblement etre vraiment plus performants.
(Par ailleurs, le fait d'utiliser .stream au lieu de .parallalelStream semble également plus interessant)