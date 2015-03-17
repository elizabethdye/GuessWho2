package Network;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class NetworkTests extends TestCase {

    NetworkManager manager = NetworkManager.getInstance();

    @Test
    public void testGetInstance() throws Exception {
        assertTrue(manager != null);
    }

    @Test
    public void testStartServer() throws Exception {
        manager.startServer();
        assertTrue(manager.serverThread.isAlive());
    }

    @Test
    public void testOpenConnection() throws Exception {
        ArrayList<String> neighbors = manager.potentialNetworkPartners();
        for (String ip : neighbors){
            manager.openConnection(ip, 8888);
            assertEquals(manager.IPAddress, ip);
        }
    }

    @Test
    public void testPotentialNetworkPartners() throws Exception {

    }

    @Test
    public void testChannelSetup() throws Exception {

    }

    @Test
    public void testGetLatest() throws Exception {

    }

    @Test
    public void testAddComm() throws Exception {

    }

    @Test
    public void testGetLocalIP() throws Exception {

    }

    @Test
    public void testGetLocalHostname() throws Exception {

    }

    @Test
    public void testReportError() throws Exception {

    }

    @Test
    public void testNumItems() throws Exception {

    }

    @Test
    public void testAddServer() throws Exception {

    }

    @Test
    public void testGetKnownServers() throws Exception {

    }
}