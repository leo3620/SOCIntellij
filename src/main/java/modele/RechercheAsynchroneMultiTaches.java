package modele;

import infrastructure.jaxrs.HyperLien;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

public class RechercheAsynchroneMultiTaches extends RechercheAsynchroneAbstraite{
    public RechercheAsynchroneMultiTaches(String nomAlgo) {
        this.nomAlgorithme = new ImplemNomAlgorithme(nomAlgo);
    }

    public void liberer(CountDownLatch countDownLatch){
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        return Optional.empty();
    }

    @Override
    public NomAlgorithme nom() {
        return this.nomAlgorithme;
    }
}
