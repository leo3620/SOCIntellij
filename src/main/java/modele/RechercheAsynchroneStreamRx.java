package modele;

import infrastructure.jaxrs.HyperLien;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public class RechercheAsynchroneStreamRx extends RechercheAsynchroneAbstraite{
    public RechercheAsynchroneStreamRx(String nomAlgo) {
        super();
        this.nomAlgorithme = new ImplemNomAlgorithme(nomAlgo);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        return Observable
                .fromIterable(bibliotheques)
                .flatMap( h -> Observable.fromFuture(rechercheAsync(h,l,client)))
                .subscribeOn(Schedulers.io())
                .filter(Optional::isPresent)
                .blockingFirst();
    }

    @Override
    public NomAlgorithme nom() {
        return this.nomAlgorithme;
    }
}
