package rw.props.service;

import rw.props.domain.DataTablesResponse;
import rw.props.domain.MyEntry;



public interface PropsService<T> {
	public DataTablesResponse<T> getPropsListResponse(String json) throws Exception;
	public MyEntry getEntryByKey(String key) throws Exception;
	public void add(MyEntry myEntry) throws Exception;
	public void update(MyEntry myEntry) throws Exception;
	public void delete(MyEntry myEntry) throws Exception;

}
