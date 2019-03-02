import org.junit.Assert;
import org.junit.Test;
import gym.*;

import java.util.ArrayList;

public class ManageServiceTest {

    @Test
    public void addService() {

        // set up
        DataCenter.database = new Database();

        // Nombre de service par defaut
        Assert.assertEquals(DataCenter.database.getServiceToSessions().size(), 6);

        // Ajouter un service
        Service service = new Service("Soccer");
        DataCenter.database.getServiceToSessions().put(service, new ArrayList());

        //Service ajouté
        Assert.assertEquals(DataCenter.database.getServiceToSessions().size(), 7);
    }

    @Test
    public void updateService() {

        // set up
        DataCenter.database = new Database();

        Service service = new Service("Soccer");
        DataCenter.database.getServiceToSessions().put(service, new ArrayList());

        Assert.assertEquals(DataCenter.database.getServiceFromId(service.getId()).getTitle(), "Soccer");

        //Modifier le titre d'un service
        DataCenter.database.getServiceFromId(service.getId()).setTitle("Football");
        Assert.assertEquals(DataCenter.database.getServiceFromId(service.getId()).getTitle(), "Football");

    }

    @Test
    public void removeService() {

        // set up
        DataCenter.database = new Database();

        Service service = new Service("Soccer");
        DataCenter.database.getServiceToSessions().put(service, new ArrayList());

        // Nombre de service actuel
        Assert.assertEquals(DataCenter.database.getServiceToSessions().size(), 7);


        // supprimer le service et tester
        DataCenter.database.removeRegistrationsFromServiceId(service.getId());
        DataCenter.database.getServiceToSessions().remove(service);
        Assert.assertFalse(DataCenter.database.getServices().contains(service));


    }

}
