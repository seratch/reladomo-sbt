package example.util

import java.sql.Connection
import java.util.TimeZone

import com.gs.fw.common.mithra.bulkloader.BulkLoaderException
import com.gs.fw.common.mithra.connectionmanager.{SourcelessConnectionManager, XAConnectionManager}
import com.gs.fw.common.mithra.databasetype.{DatabaseType, MariaDatabaseType}

class MariaDbConnectionManager private() extends SourcelessConnectionManager {
  private val xaConnectionManager: XAConnectionManager = new XAConnectionManager()
  private val MAX_POOL_SIZE_KEY = "maxPoolSize"
  private val DEFAULT_MAX_WAIT = 500
  private val DEFAULT_POOL_SIZE = 10
  private val TOKYO_TIMEZONE = TimeZone.getTimeZone("Asia/Tokyo")
  private val driverClassname = "org.mariadb.jdbc.Driver"
  private val connectionString = "jdbc:mariadb://127.0.0.1:3306/"
  private val schemaName = "testdb"
  private val userName = "root"
  private val password = "mariadb"

  createConnectionManager()

  private def createConnectionManager(): XAConnectionManager = {
    xaConnectionManager.setDriverClassName(driverClassname)
    xaConnectionManager.setMaxWait(DEFAULT_MAX_WAIT)
    xaConnectionManager.setJdbcConnectionString(connectionString + schemaName)
    xaConnectionManager.setJdbcUser(userName)
    xaConnectionManager.setJdbcPassword(password)
    xaConnectionManager.setPoolName("myproj connection pool")
    xaConnectionManager.setInitialSize(1)
    xaConnectionManager.setPoolSize(DEFAULT_POOL_SIZE)
    xaConnectionManager.initialisePool()
    xaConnectionManager
  }

  def getConnection: Connection = xaConnectionManager.getConnection

  def getDatabaseType: DatabaseType = MariaDatabaseType.getInstance

  def getDatabaseTimeZone = TOKYO_TIMEZONE

  @throws[BulkLoaderException]
  def createBulkLoader = throw new RuntimeException("BulkLoader is not supported")

  def getDatabaseIdentifier: String = schemaName

}

object MariaDbConnectionManager {
  private val _instance = new MariaDbConnectionManager()

  def getInstance(): MariaDbConnectionManager = _instance
}
