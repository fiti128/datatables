package rw.props.domain;

import java.io.Serializable;
import java.util.List;

import flexjson.JSONSerializer;

public class DataTablesResponse<T extends Object> implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * This parameter is changed on the client side for evry ajax call.
	 * Cast to integer to avoid Xss attacks
	 */       
	private String sEcho;

	/*
	 * Total number of records
	 */
	private int iTotalRecords;
	
	/*
	 * Total number of records after filtering. We are not filtering but datatables expects this parameter 
	 */
	private int  iTotalDisplayRecords;
	
	/*
	 * Datasource.
	 * By default, DataTables will expect a 2D array for its data source. However, Using the 'aoColumns' parameter 
	 * in the datatables call we can map properties to columns
	 * Flexjson converts the list of returned objects into a json objects array.
	 */
	private List<T> aaData;
	
	/*
	 * Property used for sending messages (errors, info, etc) from server in the response.
	 */
//	private String serverMessage;
//	
	private String sColumns;

	
	public DataTablesResponse(String sEcho, int iTotalRecords, int iTotalDisplayRecords,
			List<T> aaData, String sColumns) {
		super();
		this.sEcho = sEcho;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.aaData = aaData;
//		this.serverMessage = serverMessage;
		this.sColumns = sColumns;
	}
	
	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<T> getAaData() {
		return aaData;
	}

	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}

//	public String getServerMessage() {
//		return serverMessage;
//	}
//
//	public void setServerMessage(String serverMessage) {
//		this.serverMessage = serverMessage;
//	}
//
	public String getsColumns() {
		return sColumns;
	}

	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	
	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
}
