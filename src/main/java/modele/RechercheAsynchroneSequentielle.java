package modele;

import infrastructure.jaxrs.HyperLien;

import javax.ws.rs.client.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RechercheAsynchroneSequentielle extends RechercheAsynchroneAbstraite {
    public RechercheAsynchroneSequentielle(String nomAlgo) {
        super();
        this.nomAlgorithme = new ImplemNomAlgorithme(nomAlgo);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        List<Future<Optional<HyperLien<Livre>>>> promesses = new ArrayList<>();
        for (HyperLien<Bibliotheque> biblio :
                bibliotheques) {
            promesses.add(rechercheAsync(biblio, l, client));
        }
        try {
            for (Future<Optional<HyperLien<Livre>>> promesse :
                    promesses) {
                Optional<HyperLien<Livre>> resultat = promesse.get();

                if (resultat.isPresent()) {
                    return resultat;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public NomAlgorithme nom() {
        return this.nomAlgorithme;
    }
}
