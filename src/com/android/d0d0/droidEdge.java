package com.android.d0d0;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;



import net.jxta.access.AccessService;
import net.jxta.content.Content;
import net.jxta.content.ContentID;
import net.jxta.content.ContentProviderEvent;
import net.jxta.content.ContentProviderListener;
import net.jxta.content.ContentService;
import net.jxta.content.ContentShare;
import net.jxta.content.ContentShareEvent;
import net.jxta.content.ContentShareListener;
import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.FileDocument;
import net.jxta.document.MimeMediaType;
import net.jxta.endpoint.EndpointService;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.IDFactory;
import net.jxta.logging.Logging;
import net.jxta.membership.MembershipService;
import net.jxta.peer.PeerID;
import net.jxta.peer.PeerInfoService;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.PipeService;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.jxta.protocol.ContentAdvertisement;
import net.jxta.protocol.ContentShareAdvertisement;
import net.jxta.protocol.DiscoveryResponseMsg;
import net.jxta.rendezvous.RendezVousService;
import net.jxta.resolver.ResolverService;

import android.content.Context;

import com.android.d0d0.Global.ApplicationClass;


/**
 * This is the static class that access JXTA services
 */
public final class droidEdge {

	public static Context context = ApplicationClass
			.getApplicationClassContext();
	public static File file = new File(context.getFilesDir(),
			"Android_Client.cache");
    public static MimeMediaType MimeType_3gp = new MimeMediaType("application/3gp").intern();

	public static ContentService TNPGContentService;
	public static AccessService TNPGAccessService;
	public static DiscoveryService TNPGDiscoveryService;
	public static EndpointService TNPGEndpointService;
	public static MembershipService TNPGMembershipService;
	public static PeerInfoService TNPGPeerInfoService;
	public static PipeService TNPGPipeService;
	public static RendezVousService TNPGRendezVousService;
	public static ResolverService TNPGResolverService;
	public static PeerGroup TheNetPeerGroup;
    public static NetworkManager edgeManager;
	final static String d0d0EDGEdroid = "d0d0EDGE";
	final static PeerID d0d0EDGEdroidPID = IDFactory.newPeerID(
			PeerGroupID.defaultNetPeerGroupID, d0d0EDGEdroid.getBytes());
	final static int TcpPort_EDGE_A = 4;
	boolean dodoCalled = false;
	private final static Logger LOG = Logger.getLogger(droidEdge.class
			.getName());
    private  ContentID contentID;

    /**
	 * Main method
	 * 
	 * @param args
	 *            Command line arguments. none defined
	 */

	public static void startJXTA() {
		try {
			// Set the main thread name for debugging.

//			Thread.currentThread().setName(droidEdge.class.getSimpleName());

			// String fileName =
			// Uri.parse("Android_Client").getLastPathSegment();
			createConfigFile(file);

			edgeManager = configureEdge(d0d0EDGEdroid,
					d0d0EDGEdroidPID, TcpPort_EDGE_A);

			TheNetPeerGroup = startEdge(d0d0EDGEdroid, edgeManager);

			waitForRDV(edgeManager);

			Logging.logCheckedInfo(LOG, "retrieving Services");
			TNPGAccessService = TheNetPeerGroup.getAccessService();
			TNPGContentService = TheNetPeerGroup.getContentService();
			TNPGDiscoveryService = TheNetPeerGroup.getDiscoveryService();
			TNPGEndpointService = TheNetPeerGroup.getEndpointService();
			TNPGMembershipService = TheNetPeerGroup.getMembershipService();
			TNPGPeerInfoService = TheNetPeerGroup.getPeerInfoService();
			TNPGPipeService = TheNetPeerGroup.getPipeService();
			TNPGRendezVousService = TheNetPeerGroup.getRendezVousService();
			TNPGResolverService = TheNetPeerGroup.getResolverService();

			TNPGDiscoveryService.addDiscoveryListener(dscListener);
			TNPGDiscoveryService.getRemoteAdvertisements(null,
					DiscoveryService.PEER, null, null, 5, null);

			/*
			 * // Stop JXTA Logging.logCheckedInfo(LOG,"Stopping JXTA");
			 * manager.stopNetwork();
			 * 
			 * Logging.logCheckedInfo(LOG,"JXTA stopped");
			 */
		} catch (Throwable e) {
			// Some type of error occurred. Print stack trace and quit.
			System.err.println("Fatal error -- Quitting");
			e.printStackTrace(System.err);
			System.exit(-1);
		}
	}

    public static void shareContent(String contentFileName){
        File contentFile = new File(contentFileName);
        FileDocument contentFileDoc = new FileDocument(contentFile, new MimeMediaType("application/3gp").intern());
        ContentID contentID = IDFactory.newContentID(TheNetPeerGroup.getPeerGroupID(), false);
        Content content = new Content(contentID, null, contentFileDoc );
        ContentAdvertisement contentAdv = (ContentAdvertisement) AdvertisementFactory.newAdvertisement(ContentAdvertisement.getAdvertisementType());
        contentAdv.setContentID(contentID);
        contentAdv.setMimeType(MimeType_3gp);

        try {
            TNPGDiscoveryService.publish(contentAdv);
        } catch (IOException e) {
            Logging.logCheckedInfo(LOG, "unable to publish content Advertisement");
        }



    }


    /**
     * Content provider listener used to be notified of any activity being
     * performed by the content provider.
     */
    private ContentProviderListener provListener =
            new ContentProviderListener() {
                public void contentShared(ContentProviderEvent event) {
                    Logging.logCheckedInfo(LOG, "Content shared:", event);
                }

                public void contentUnshared(ContentProviderEvent event) {
                    Logging.logCheckedInfo(LOG, "Content unshared: ", event);
                }

                public boolean contentSharesFound(ContentProviderEvent event) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };



    /**
     * Content share listener used to be notified of any activity during
     * a content transfer.
     */
    private static ContentShareListener shareListener = new ContentShareListener() {
        public void shareSessionOpened(ContentShareEvent event) {
            Logging.logCheckedInfo(LOG, "Session opened: ", event);
        }

        public void shareSessionClosed(ContentShareEvent event) {
            Logging.logCheckedInfo(LOG, "Session closed", event);
        }

        public void shareAccessed(ContentShareEvent event) {
            Logging.logCheckedInfo(LOG, "Share access", event);
        }
    };



    /**
	 * 
	 */


	protected static void createConfigFile(File file) {

		if (file.exists()) {
			Logging.logCheckedInfo(LOG,
					file.getName() + " exists: " + file.exists());
			file.deleteOnExit();
		}
	}

	/**
	 * @param edgeManager
	 */
	protected static void waitForRDV(NetworkManager edgeManager) {
		Logging.logCheckedInfo(LOG, "Waiting for a rendezvous connection");
		boolean connected = edgeManager.waitForRendezvousConnection(20 * 1000);
		Logging.logCheckedInfo(LOG,
				MessageFormat.format("Edge Connected :{0}", connected));
	}

	/**
	 * @param d0d0EDGEdroid
	 * @param edgeManager
	 * @return
	 * @throws PeerGroupException
	 * @throws IOException
	 */
	protected static PeerGroup startEdge(final String d0d0EDGEdroid,
			NetworkManager edgeManager) throws PeerGroupException, IOException {
		Logging.logCheckedInfo(LOG,
				"Configuring Complete. Starting the edge named: "
						+ d0d0EDGEdroid);

		PeerGroup TheNetPeerGroup = edgeManager.startNetwork();
		boolean started = edgeManager.isStarted();
		Logging.logCheckedInfo(LOG,
				MessageFormat.format("Edge started :{0}", started));
		return TheNetPeerGroup;
	}

	/**
	 * @param d0d0EDGEdroid
	 * @param d0d0EDGEdroidPID
	 * @param TcpPort_EDGE_A
	 * @return
	 * @throws IOException
	 */
	protected static NetworkManager configureEdge(final String d0d0EDGEdroid,
			final PeerID d0d0EDGEdroidPID, final int TcpPort_EDGE_A)
			throws IOException {

		Logging.logCheckedInfo(LOG, "Configuring d0d0 Edge");
		NetworkManager edgeManager = new NetworkManager(
				NetworkManager.ConfigMode.EDGE, d0d0EDGEdroid, file.toURI());

		// Retrieving the network configurator
		NetworkConfigurator MyNetworkConfigurator = edgeManager
				.getConfigurator();

		// Setting Configuration
		MyNetworkConfigurator.setTcpPort(TcpPort_EDGE_A);
		MyNetworkConfigurator.setTcpEnabled(true);
		MyNetworkConfigurator.setTcpIncoming(true);
		MyNetworkConfigurator.setTcpOutgoing(true);
		MyNetworkConfigurator.setUseMulticast(true);

		// Setting the Peer ID
		MyNetworkConfigurator.setPeerID(d0d0EDGEdroidPID);

		// Adding RDV a as a seed
		MyNetworkConfigurator.clearRendezvousSeeds();
		MyNetworkConfigurator.clearRelaySeeds();

        String TCPSeed = "TCP://192.168.1.79:9702";
        String HTTPSeed = "http://192.168.1.79:9704";

        URI LocalRendezVousHTTPSeedURI = URI.create(HTTPSeed);
        URI LocalRendezVousTCPSeedURI = URI.create(TCPSeed);

        // MyNetworkConfigurator.addSeedRendezvous(LocalRendezVousHTTPSeedURI);
        MyNetworkConfigurator.addSeedRendezvous(LocalRendezVousTCPSeedURI);

        return edgeManager;
	}

	/**
	 * obtains all the necessary services from the network Peer group
	 * 
	 * @param TheNetPeerGroup
	 */
	protected static void getServices(PeerGroup TheNetPeerGroup) {

		Logging.logCheckedInfo(LOG, "retrieving Services");
		ContentService TNPGContentService = TheNetPeerGroup.getContentService();
		AccessService TNPGAccessService = TheNetPeerGroup.getAccessService();
		DiscoveryService TNPGDiscoveryService = TheNetPeerGroup
				.getDiscoveryService();
		EndpointService TNPGEndpointService = TheNetPeerGroup
				.getEndpointService();
		MembershipService TNPGMembershipService = TheNetPeerGroup
				.getMembershipService();
		PeerInfoService TNPGPeerInfoService = TheNetPeerGroup
				.getPeerInfoService();
		PipeService TNPGPipeService = TheNetPeerGroup.getPipeService();
		RendezVousService TNPGRendezVousService = TheNetPeerGroup
				.getRendezVousService();
		ResolverService TNPGResolverService = TheNetPeerGroup
				.getResolverService();
	}

	/**
	 * @param TheNetPeerGroup
	 */
	public static ContentService getContentService() {

		return TNPGContentService;
	}

	public static DiscoveryService getDiscoveryService() {

		return TNPGDiscoveryService;
	}

	/**
	 * This method is called whenever a discovery response is received, which
	 * are either in response to a query we sent, or a remote publish by another
	 * node
	 * 
	 * @param ev
	 *            the discovery event
	 */
	static DiscoveryListener dscListener = new DiscoveryListener() {
		public void discoveryEvent(DiscoveryEvent ev) {

			DiscoveryResponseMsg res = ev.getResponse();

			// let's get the responding peer's advertisement
			Logging.logCheckedInfo(LOG,
					" [  Got a Discovery Response [" + res.getResponseCount()
							+ " elements]  from peer : " + ev.getSource()
							+ "  ]");

			Advertisement adv;
			Enumeration<Advertisement> PeerAdvs = res.getAdvertisements();

			if (PeerAdvs != null) {
				while (PeerAdvs.hasMoreElements()) {
					adv = (Advertisement) PeerAdvs.nextElement();
					Logging.logCheckedInfo(LOG, adv);
				}
			}
		}
	};

       public static void stopEdge(){

			 // Stop JXTA
			 Logging.logCheckedInfo(LOG,"Stopping JXTA");
			 edgeManager.stopNetwork();

			 Logging.logCheckedInfo(LOG,"JXTA stopped");


       }
}
