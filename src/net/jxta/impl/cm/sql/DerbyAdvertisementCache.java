/*

    @d0d0
    I am getting an error saying that javax.naming.referenceable is not found.
    So I looked into the error, sort of decided that it is the direct connection
    to the Derby database through a JDBC thats causing all this issue.
    Then sort of decided that if I provide JNDI jar file containing javax.naming.referenceable,
    then all my problems would be over. the problem is that the library contains some classes that
    gradle does not like.

    Ok. I needed help and I posted my question in StackOverflow:
    http://stackoverflow.com/questions/27225187/android-studio-gradle-error-message-cannot-access-referenceable-in-javax-namin

    Also, I was getting some dex errors. this shows that the library files are not android compatible.

    So I am going to comment out the derby database related code and
    remove the derby database library files.

package net.jxta.impl.cm.sql;



import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import net.jxta.impl.util.threads.TaskManager;

import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;


public class DerbyAdvertisementCache extends JdbcAdvertisementCache {

public DerbyAdvertisementCache(URI storeRoot, String areaName, TaskManager taskManager) throws IOException {
		super(storeRoot, areaName, taskManager);
	}
	
	public DerbyAdvertisementCache(URI storeRoot, String areaName, TaskManager taskManager, long gcinterval, boolean trackDeltas) throws IOException {
		super(storeRoot, areaName, taskManager, gcinterval, trackDeltas);
	}
	
	@Override
	protected EmbeddedConnectionPoolDataSource createDataSource() {
		if(!loadDbDriver( "org.apache.derby.jdbc.EmbeddedDriver")) {
			throw new RuntimeException("Unable to loadDB driver:  org.apache.derby.jdbc.EmbeddedDriver");
		}
		EmbeddedConnectionPoolDataSource dataSource = new EmbeddedConnectionPoolDataSource();
		dataSource.setDatabaseName(dbDir.getAbsolutePath());
		dataSource.setCreateDatabase("create");
		System.err.println("Created derby cache");
		return dataSource;
	}
	
	@Override
	protected void shutdownDb() throws SQLException {
		// annoyingly, shutting down a derby instance involves catching an exception
		// and checking error codes to make sure it shut down "normally"
		
		try {
			EmbeddedDataSource dataSource = new EmbeddedDataSource();
			dataSource.setDatabaseName(dbDir.getAbsolutePath());
			dataSource.setShutdownDatabase("shutdown");
			dataSource.getConnection();
		} catch(SQLException e) {
			// make sure we get the correct error codes 
			if(e.getErrorCode() != 45000 || !"08006".equals(e.getSQLState())) {
				throw e;
			}
		}
	}

}
*/
