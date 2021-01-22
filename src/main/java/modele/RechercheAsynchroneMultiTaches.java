package modele;

import infrastructure.jaxrs.HyperLien;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.InvocationCallback;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class RechercheAsynchroneMultiTaches extends RechercheAsynchroneAbstraite{
    public RechercheAsynchroneMultiTaches(String nomAlgo) {
        this.nomAlgorithme = new ImplemNomAlgorithme(nomAlgo);
    }

    public void liberer(CountDownLatch countDownLatch){
        for (int i = 0; i <  countDownLatch.getCount(); i++) {
            countDownLatch.countDown();
        }
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        try {
            ExecutorService threadPool = Executors.newCachedThreadPool();
            CountDownLatch countDownLatch = new CountDownLatch(bibliotheques.size());
            AtomicReference<Optional<HyperLien<Livre>>> atomicLivreReference = new AtomicReference<>(Optional.empty());

            for (HyperLien<Bibliotheque> biblioteque : bibliotheques) {
                threadPool.submit(() -> {

                    InvocationCallback<Optional<HyperLien<Livre>>> retour = new InvocationCallback<Optional<HyperLien<Livre>>>() {
                        @Override
                        public void completed(Optional<HyperLien<Livre>> livreHyperLien) {
                            countDownLatch.countDown();
                            if (livreHyperLien.isPresent()) {
                                atomicLivreReference.set(livreHyperLien);
                                liberer(countDownLatch);
                            }
                        }

                        @Override
                        public void failed(Throwable throwable) {
                            countDownLatch.countDown();
                            throw new RuntimeException("CallBack failed");
                        }
                    };
                    rechercheAsyncAvecRappel(biblioteque, l, client, retour);

                });
            }
            countDownLatch.await();
            return atomicLivreReference.get();
        } catch (InterruptedException exception) {
            throw new RuntimeException("Probleme de sequentialisation");
        }
    }

    @Override
    public NomAlgorithme nom() {
        return this.nomAlgorithme;
    }
}
