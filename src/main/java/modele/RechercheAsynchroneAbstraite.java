package modele;

import java.util.Optional;
import java.util.concurrent.Future;

import configuration.JAXRS;
import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.LienVersRessource;
import infrastructure.jaxrs.Outils;
import infrastructure.langage.Types;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

public abstract class RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	protected ImplemNomAlgorithme nomAlgorithme;

	protected Future<Optional<HyperLien<Livre>>> rechercheAsync(HyperLien<Bibliotheque> h, Livre l, Client client){
		WebTarget wt = client.target(h.getUri());
		return wt.path(JAXRS.SOUSCHEMIN_ASYNC)
				.request()
				.accept(JAXRS.TYPE_MEDIATYPE)
				.async()
				.put(Entity.entity(l, JAXRS.TYPE_MEDIATYPE), Types.typeRetourChercherAsync());

	}

	protected Future<Optional<HyperLien<Livre>>> rechercheAsyncAvecRappel(
			HyperLien<Bibliotheque> h, Livre l, Client client,  
			InvocationCallback<Optional<HyperLien<Livre>>> retour){
		WebTarget wt = client.target(h.getUri());
		return wt.path(JAXRS.SOUSCHEMIN_ASYNC)
				.request()
				.accept(JAXRS.TYPE_MEDIATYPE)
				.async()
				.put(Entity.entity(l, JAXRS.TYPE_MEDIATYPE), retour);
	}
}
