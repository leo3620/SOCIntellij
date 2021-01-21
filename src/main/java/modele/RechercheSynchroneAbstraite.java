package modele;

import java.util.Optional;

import javax.ws.rs.client.Client;

import com.sun.istack.Pool;
import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.LienVersRessource;
import infrastructure.jaxrs.Outils;

public abstract class RechercheSynchroneAbstraite implements AlgorithmeRecherche {

    protected NomAlgorithme nomAlgorithme;

    protected Optional<HyperLien<Livre>> rechercheSync(HyperLien<Bibliotheque> h, Livre l, Client client){
		Outils.afficherInfoTache("recherche synchrone");
		Bibliotheque bibli = LienVersRessource.proxy(client, h, Bibliotheque.class);
		return bibli.chercher(l);
	}

}
