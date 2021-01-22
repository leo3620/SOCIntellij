package modele;

import infrastructure.jaxrs.HyperLien;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public class RechercheSynchroneStreamParallele extends RechercheSynchroneAbstraite {
    public RechercheSynchroneStreamParallele(String nomAlgo) {
        super();
        this.nomAlgorithme = new ImplemNomAlgorithme(nomAlgo);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
         return bibliotheques
                .parallelStream()
                .map(biblio -> this.rechercheSync(biblio, l, client))
                .filter(Optional::isPresent)
                .findAny()
                .orElse(Optional.empty());
    }

    @Override
    public NomAlgorithme nom() {
        return this.nomAlgorithme;
    }
}
