package rw.props.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;

/**
 * {
 *  "sEcho":1,
 *  "iColumns":7,
 *  "sColumns":"",
 *  "iDisplayStart":0,
 *  "iDisplayLength":10,
 *  "amDataProp":[0,1,2,3,4,5,6],
 *  "sSearch":"",
 *  "bRegex":false,
 *  "asSearch":["","","","","","",""],
 *  "abRegex":[false,false,false,false,false,false,false],
 *  "abSearchable":[true,true,true,true,true,true,true],
 *  "iSortingCols":1,
 *  "aiSortCol":[0],
 *  "asSortDir":["asc"],
 *  "abSortable":[true,true,true,true,true,true,true]
 * }
 *
 *{"sEcho":1,"iColumns":4,"sColumns":"","iDisplayStart":0,"iDisplayLength":10,"amDataProp":[0,1,2,3],"mDataProp_0":0,"mDataProp_1":1,"mDataProp_2":2,"mDataProp_3":3,"sSearch":"","bRegex":false,"asSearch":["","","",""],"sSearch_0":"","abRegex":[false,false,false,false],"bRegex_0":false,"abSearchable":[true,true,true,true],"bSearchable_0":true,"sSearch_1":"","bRegex_1":false,"bSearchable_1":true,"sSearch_2":"","bRegex_2":false,"bSearchable_2":true,"sSearch_3":"","bRegex_3":false,"bSearchable_3":true,"aiSortCol":[0],"iSortCol_0":0,"asSortDir":["asc"],"sSortDir_0":"asc","iSortingCols":1,"abSortable":[true,true,true,true],"bSortable_0":true,"bSortable_1":true,"bSortable_2":true,"bSortable_3":true}
 *
 * 
 */
public class DataTablesRequest<T> implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int sEcho;
    public int iColumns;
    public String sColumns;
    public int iDisplayStart;
    public int iDisplayLength;
    public List<String> amDataProp;
    public String sSearch;
    public List<String> asSearch;
    public boolean bRegex;
    public List<Boolean> abRegex;
    public List<Boolean> abSearchable;
    public int iSortingCols;
    public List<Integer> aiSortCol;
    public List<String> asSortDir;
    public List<Boolean> abSortable;
    public String sRangeSeparator;
    public T searchObj;
    public Map<String,Interval> msIntervals; /* Map<propertyName,Interval> */
    public Object extraData;

    @Override
    public String toString() {
        return "DataTablesRequest{" +
                "sEcho=" + sEcho +
                ", iColumns=" + iColumns +
                ", sColumns='" + sColumns + '\'' +
                ", iDisplayStart=" + iDisplayStart +
                ", iDisplayLength=" + iDisplayLength +
                ", amDataProp=" + amDataProp +
                ", sSearch='" + sSearch + '\'' +
                ", asSearch=" + asSearch +
                ", bRegex=" + bRegex +
                ", abRegex=" + abRegex +
                ", abSearchable=" + abSearchable +
                ", iSortingCols=" + iSortingCols +
                ", aiSortCol=" + aiSortCol +
                ", asSortDir=" + asSortDir +
                ", abSortable=" + abSortable +
                ", sRangeSeparator='" + sRangeSeparator + '\'' +
                ", searchObj=" + searchObj +
                ", msIntervals=" + msIntervals +
                ", extraData=" + extraData +
                '}';
    }
}
