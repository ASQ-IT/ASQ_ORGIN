package asq.pos.zatca.invoice.generation.op;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asq.pos.zatca.invoice.models.InvoiceICV;


public class H2DataSourceConfig {

//	private Properties properties;
	final String JDBC_DRIVER;
	final String DB_URL;
	final String USER;
	final String PASS;
	final Logger logger = LogManager.getLogger(H2DataSourceConfig.class);
	
	public H2DataSourceConfig() {
//		properties = SmartHubUtil.getProperties();
		// JDBC driver name and database URL
		JDBC_DRIVER = System.getProperty("asq.pos.invoice.jdbcDriver");
		DB_URL = System.getProperty("asq.pos.invoice.jdbcURL");

		// Database credentials
		USER = System.getProperty("asq.pos.invoice.h2User");
		PASS = System.getProperty("asq.pos.invoice.h2Pwd");
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
		return conn;
	}

	public  InvoiceICV getInvoiceICV(Integer icv) {
		Base64 base64 = new Base64();
		InvoiceICV invoiceICV = new InvoiceICV();
	      Connection conn = null; 
	      PreparedStatement stmt = null; 
		try {
			conn = getConnection();
			String sql = "select " + "icv, " + "invoice_id, " + "hextoraw(hash) as hash, " + "invoice_date " + "from "
					+ "invoice_hash " + "where icv=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, icv);
			ResultSet resultSet = stmt.executeQuery();
			int count = 0;
			while (resultSet.next()) {
				count++;
				invoiceICV.setIcv(resultSet.getInt("icv"));
				invoiceICV.setInvoiceId(resultSet.getString("invoice_id"));
				invoiceICV.setHash(resultSet.getString("hash"));
				invoiceICV.setInvoiceDate(resultSet.getString("invoice_date"));
				logger.info(invoiceICV.toString());
			}
			
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return invoiceICV;
	}

	public  InvoiceICV getPreviousHash() {
		InvoiceICV invoiceICV = new InvoiceICV();
	      Connection conn = null; 
	      PreparedStatement stmt = null; 
		try {
			conn = getConnection();
			if("true".equalsIgnoreCase(System.getProperty("asq.pos.invoice.resetDB"))) {
				String drop="drop table invoice_hash";
				stmt = conn.prepareStatement(drop);
				stmt.execute();
				String create="CREATE TABLE invoice_hash ( \r\n"
						+ "   icv bigint auto_increment primary key,\r\n"
						+ "   invoice_id VARCHAR(100) NOT NULL, \r\n"
						+ "   hash BLOB NOT NULL, \r\n"
						+ "   invoice_date TIMESTAMP DEFAULT NOW()\r\n"
						+ ");";
				stmt = conn.prepareStatement(create);
				stmt.execute();
//				try {
//					FileOutputStream out = new FileOutputStream(
//							properties.getProperty("asq.pos.invoice.resourceDir") + "app.properties");
//					properties.setProperty("resetDB", String.valueOf(false));
//					properties.store(out, null);
//					out.close();
//				} catch (IOException ioe) {
//					logger.info("Property Updation Failed With IOException for resetDB property" + ioe);
//				}
			}
			String sql = "select icv, invoice_id, hextoraw(hash) as hash, invoice_date from invoice_hash where icv = (select max(icv) from invoice_hash)";
			stmt = conn.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();
			int count = 0;
			while (resultSet.next()) {
				count++;
				invoiceICV.setIcv(resultSet.getInt("icv"));
				invoiceICV.setInvoiceId(resultSet.getString("invoice_id"));
				invoiceICV.setHash(resultSet.getString("hash"));
				invoiceICV.setInvoiceDate(resultSet.getString("invoice_date"));
				logger.info(invoiceICV.toString());
			}
			//if its first transaction then setting default value
			if(count==0) {
				invoiceICV.setIcv(0);
				invoiceICV.setHash(System.getProperty("asq.pos.invoice.defaultHash"));
			}
			
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		Integer previousICV = invoiceICV.getIcv();
		if(previousICV <= 0) {
			previousICV = 0;
			return invoiceICV;
		}
		return getInvoiceICV(previousICV);
	}
	
	public  Integer insertInvoiceData(InvoiceICV in) {
		Integer icv = null;
		InvoiceICV invoiceICV = null;
	      Connection conn = null; 
	      Statement stmt = null; 
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String sql = "insert into invoice_hash(invoice_id, hash, invoice_date) values(?, RAWTOHEX(?), now())";
			PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, in.getInvoiceId());
			ps.setString(2, in.getHash());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			int count = 0;
			while (resultSet.next()) {
				count++;
				icv = (int) (long) resultSet.getLong(1);
			}
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			logger.info("Invoice "+ in.getInvoiceId()+" hash has not been inserted");
			logger.info(se.getErrorCode()+" : "+se.getMessage());
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			logger.info(e.getMessage());
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				logger.info(se.getErrorCode()+" : "+se.getMessage());
				se.printStackTrace();
			} // end finally try
		} // end try
		logger.info("Invoice "+ in.getInvoiceId()+" hash has been inserted successfully");
		return icv;
	}

	public  List<InvoiceICV> getInvoiceList() {
		  List<InvoiceICV> invoiceList = new ArrayList<InvoiceICV>();
	      Connection conn = null; 
	      PreparedStatement stmt = null; 
		try {
			conn = getConnection();
			String sql = "select " + "icv, " + "invoice_id, " + "hextoraw(hash) as hash, " + "invoice_date " + "from "
					+ "invoice_hash";
			stmt = conn.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();
			int count = 0;
			while (resultSet.next()) {
				count++;
				InvoiceICV invoiceICV = new InvoiceICV();
				invoiceICV.setIcv(resultSet.getInt("icv"));
				invoiceICV.setInvoiceId(resultSet.getString("invoice_id"));
				invoiceICV.setHash(resultSet.getString("hash"));
				invoiceICV.setInvoiceDate(resultSet.getString("invoice_date"));
				invoiceList.add(invoiceICV);
				logger.info(invoiceICV.toString());
			}
			
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return invoiceList;
	}
	
}
