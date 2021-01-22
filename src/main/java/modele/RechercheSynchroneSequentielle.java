package modele;

import infrastructure.jaxrs.HyperLien;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public class RechercheSynchroneSequentielle extends RechercheSynchroneAbstraite{

    public RechercheSynchroneSequentielle(String nomAlgo) {
        this.nomAlgorithme = new ImplemNomAlgorithme(nomAlgo);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        for (HyperLien<Bibliotheque> b: bibliotheques) {
            Optional<HyperLien<Livre>> livre = this.rechercheSync(b, l, client);
             if(livre.isPresent()) {
                 return livre;
             };
        }
        return Optional.empty();
    }

    @Override
    public NomAlgorithme nom() {
        return this.nomAlgorithme;
    }
}
