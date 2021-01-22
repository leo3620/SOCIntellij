package modele;

import infrastructure.jaxrs.HyperLien;
import io.reactivex.rxjava3.core.Observable;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public class RechercheSynchroneStreamRx extends RechercheSynchroneAbstraite {
    public RechercheSynchroneStreamRx(String nomAlgo) {
        super();
        this.nomAlgorithme = new ImplemNomAlgorithme(nomAlgo);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
       // Observable<HyperLien<Bibliotheque>> observaleBiblio =  Observable.fromIterable(bibliotheques);
        return null;
    }

    @Override
    public NomAlgorithme nom() {
        return this.nomAlgorithme;
    }
}
