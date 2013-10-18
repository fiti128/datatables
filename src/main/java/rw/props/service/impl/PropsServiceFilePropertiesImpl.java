package rw.props.service.impl;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import rw.props.domain.DataTablesRequest;
import rw.props.domain.DataTablesResponse;
import rw.props.domain.MyEntry;
import rw.props.service.PropsService;
import flexjson.JSONDeserializer;

@Service("propsService")
public class PropsServiceFilePropertiesImpl implements PropsService<MyEntry> {
    public static final String UTF_8 = "UTF-8";
    public static final String TEMP = "_new";
    Logger logger = Logger.getLogger(this.getClass());


    public static final String PARSER_PROPERTIES = "parser.properties";
    private URL url = Thread.currentThread().getContextClassLoader().getResource(PARSER_PROPERTIES);

    enum EntryProcess {
        UPDATE,
        DELETE
    }


    @Override
    public DataTablesResponse<MyEntry> getPropsListResponse(String json)
            throws Exception {

        DataTablesRequest<MyEntry> dataTablesRequest = new JSONDeserializer<DataTablesRequest<MyEntry>>()
                .use("searchObj", MyEntry.class)
                .use(null, DataTablesRequest.class)
                .deserialize(json);


        List<MyEntry> entryList = new ArrayList<MyEntry>();

        int entriesCount = 0;
        int startIndex = dataTablesRequest.iDisplayStart;
        int lastIndex = dataTablesRequest.iDisplayLength + startIndex;
        int cursor = 0;
        String keyFilter = dataTablesRequest.asSearch.get(0);
        String valueFilter = dataTablesRequest.asSearch.get(1);
        System.out.println(keyFilter + " " + valueFilter);

        InputStream is = url.openStream();
        String buffer;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, PropsServiceFilePropertiesImpl.UTF_8));
        while ((buffer = reader.readLine()) != null) {
            if (buffer.contains("=")) {
                boolean accept = true;
                MyEntry entry = MyEntry.parse(buffer);

                if (keyFilter.length() > 0) {
                    if (!entry.getKey().contains(keyFilter)) {
                        accept = false;
                    }
                }
                if (valueFilter.length() > 0) {
                    if (!entry.getValue().contains(valueFilter)) {
                        accept = false;
                    }
                }
                if (accept) {
                    entriesCount++;
                    cursor++;
                    if (cursor >= startIndex && cursor <= lastIndex) {
                        entryList.add(entry);
                    }

                }
            }
        }


        return new DataTablesResponse<MyEntry>("" + dataTablesRequest.sEcho,
                entriesCount,
                entriesCount,
                entryList,
                dataTablesRequest.sColumns);

    }


    @Override
    public MyEntry getEntryByKey(String key) throws Exception {

        MyEntry myEntry = null;
        InputStream is = url.openStream();
        String buffer;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, UTF_8));
        while ((buffer = reader.readLine()) != null) {
            if (buffer.contains("=")) {

                myEntry = MyEntry.parse(buffer);
                if (myEntry.getKey().equals(key)) {
                    break;
                }
            }
        }
        return myEntry;

    }

    @Override
    public void add(MyEntry myEntry) throws Exception {
        String key = myEntry.getKey();
        URL url = Thread.currentThread().getContextClassLoader().getResource(PARSER_PROPERTIES);
        InputStream is = url.openStream();
        String buffer = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, PropsServiceFilePropertiesImpl.UTF_8));
        MyEntry fileEntry;
        while ((buffer = reader.readLine()) != null) {
            if (buffer.contains("=")) {

                fileEntry = MyEntry.parse(buffer);
                if (fileEntry.getKey().equals(key)) {
                    return;
                }
            }
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(url.getFile(), true), PropsServiceFilePropertiesImpl.UTF_8));
            writer.write(myEntry.toString());
            writer.newLine();
        } finally {
            if(writer!=null){
                writer.close();
            }
        }
        logger.info(String.format("Property %s successfuly added",myEntry.toString()));
    }

    @Override
    public void update(MyEntry myEntry) throws Exception {
           process(myEntry,EntryProcess.UPDATE);
           logger.info(String.format("Property %s successfuly updated",myEntry.toString()));

    }

    void copyFile(File targetFile, BufferedReader reader, BufferedWriter writer, File sourceFile) throws IOException {
        String buffer;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), PropsServiceFilePropertiesImpl.UTF_8));

            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile, false), PropsServiceFilePropertiesImpl.UTF_8));
            while ((buffer = reader.readLine()) != null) {
                writer.write(buffer);
                writer.newLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

	@Override
	public void delete(MyEntry myEntry) throws Exception {
        process(myEntry,EntryProcess.DELETE);
        logger.info(String.format("Property %s successfuly deleted",myEntry.toString()));
		
	}

    void process(MyEntry myEntry,EntryProcess entryProcess) throws IOException {
        String key = myEntry.getKey();
        url = Thread.currentThread().getContextClassLoader().getResource(PARSER_PROPERTIES);
        File file = new File(url.getFile());
        InputStream is = url.openStream();
        BufferedReader reader = null;
        BufferedWriter writer = null;
        String tempFilePath = url.getFile() + TEMP;
        File tempFile = new File(tempFilePath);
        String buffer;
        try {
            reader = new BufferedReader(new InputStreamReader(is, UTF_8));
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile, false), PropsServiceFilePropertiesImpl.UTF_8));
            MyEntry fileEntry;
            while ((buffer = reader.readLine()) != null) {
                if (buffer.contains("=")) {

                    fileEntry = MyEntry.parse(buffer);
                    if (fileEntry.getKey().equals(key)) {
                        if(entryProcess == EntryProcess.UPDATE) {
                            writer.write(myEntry.toString());
                            writer.newLine();
                        }
                        continue;
                    }
                    writer.write(fileEntry.toString());
                    writer.newLine();

                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        copyFile(file, reader, writer, tempFile);
    }


}


		
	


