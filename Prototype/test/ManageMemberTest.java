import org.junit.Assert;
import org.junit.Test;
import gym.*;

import java.util.Date;

public class ManageMemberTest {

    @Test
    public void addMember() {

        // set up
        DataCenter.database = new Database();

        // aucun membre
        Assert.assertEquals(DataCenter.database.getMembers().size(), 0);

        Date date = SetUpUtil.parseDate("15-01-1996", "dd-MM-yyyy");
        Member member = new Member("Alex", "Eid", date, "M", "5141111111", "b@gmail.com", "", "", "", "");
        DataCenter.database.getMembers().add(member);

        // membre ajoute
        Assert.assertEquals(DataCenter.database.getMembers().size(), 1);

    }

    @Test
    public void updateMember() {
        // set up
        DataCenter.database = new Database();

        Date date = SetUpUtil.parseDate("15-01-1996", "dd-MM-yyyy");
        Member member = new Member("Alex", "Eid", date, "M", "5141111111", "b@gmail.com", "", "", "", "");
        DataCenter.database.getMembers().add(member);

        Assert.assertEquals(DataCenter.database.getMemberFromId(member.getId()).getName(), "Alex Eid");

        // modifier le prenom du membre et tester
        DataCenter.database.getMemberFromId(member.getId()).setFirstName("Alexandre");
        Assert.assertEquals(DataCenter.database.getMemberFromId(member.getId()).getName(), "Alexandre Eid");

    }

    @Test
    public void removeMember() {
        // set up
        DataCenter.database = new Database();

        Date date = SetUpUtil.parseDate("15-01-1996", "dd-MM-yyyy");
        Member member = new Member("Alex", "Eid", date, "M", "5141111111", "b@gmail.com", "", "", "", "");
        DataCenter.database.getMembers().add(member);

        Assert.assertEquals(DataCenter.database.getMembers().size(), 1);

        // supprimer le membre et tester
        DataCenter.database.getMembers().remove(member);
        Assert.assertFalse(DataCenter.database.getMembers().contains(member));
    }

}
