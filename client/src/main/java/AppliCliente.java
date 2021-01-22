import infrastructure.jaxrs.AdapterClientReponsesPUT404EnOption;
import infrastructure.jaxrs.AdapterClientReponsesPUTEnOption;
import infrastructure.jaxrs.HyperLien;
import modele.*;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

public class AppliCliente {
    public static Client clientJAXRS() {
        ClientConfig config = new ClientConfig();
        config.register(new AdapterClientReponsesPUT404EnOption());
        config.register(AdapterClientReponsesPUTEnOption.class);
        return ClientBuilder.newClient(config);
    }

    public static void main(String[] args) {
        WebTarget cible = clientJAXRS().target("http://localhost:8081/PortailServeur2/portail/").property(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML);


        AdminAlgo adminAlgo = WebResourceFactory.newResource(AdminAlgo.class, cible);
        Repertoire repertoire = WebResourceFactory.newResource(Repertoire.class, cible);

        System.out.println("*************");
        System.out.println("recherche sync seq");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync seq"));
        doTest(repertoire);

        System.out.println("*************");
        System.out.println("recherche sync multi");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync multi"));
        doTest(repertoire);

        System.out.println("*************");
        System.out.println("recherche sync stream 8");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync stream 8"));
        doTest(repertoire);

        System.out.println("*************");
        System.out.println("recherche sync stream rx");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync stream rx"));
        doTest(repertoire);

        System.out.println("*************");
        System.out.println("recherche async seq");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync seq"));
        doTest(repertoire);

        System.out.println("*************");
        System.out.println("recherche async multi");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync multi"));
        doTest(repertoire);

        System.out.println("*************");
        System.out.println("recherche async stream 8");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync stream 8"));
        doTest(repertoire);

        System.out.println("*************");
        System.out.println("recherche async stream rx");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync stream rx"));
        doTest(repertoire);
    }

    private static void doTest(Repertoire repertoire) {
        long temps = System.nanoTime();
        Optional<HyperLien<Livre>> lien = repertoire.chercher(new ImplemLivre("Services5.6"));
        temps = System.nanoTime() - temps;
        if(lien.isPresent()) {
            System.out.print("TEST SUCCESS ! Book in " + lien.get().getUri());
        } else {
            System.out.print("TEST FAILED");
        }
        System.out.println(" Réalisé en : " + (temps / 1000000));
    }
}
