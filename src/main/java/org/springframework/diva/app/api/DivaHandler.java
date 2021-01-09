package org.springframework.diva.app.api;

import com.fpdigital.diva.api.*;
import com.fpdigital.diva.api.exceptions.DivaException;
import com.fpdigital.diva.api.exceptions.CommandFailureException;
import com.fpdigital.diva.api.requests.ArchiveRequest;
import com.fpdigital.diva.api.exceptions.DivaInternalError;
import com.fpdigital.diva.api.requests.*;
import com.fpdigital.diva.api.resources.*;
import com.fpdigital.diva.api.DivaObjectInfo.*;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;


public class DivaHandler {
	private Session session;
	//private String managerHost;
	private String userName;
	private String userPassword;
	private String userInfo;
	private String siteName;
	private String appName;
	private String appInfo;
	private int managerPortNumber;
	private int responseTimeOut;
	private int MaxSimultaneousRequests;

	// konstruktory
//	public DivaHandler() {
//		initConnection();
//	}

	public DivaHandler() {//String managerHost) {
		initConnection();
		//this.managerHost = managerHost;
	}

	// meody prywatne
	private void initConnection() {
		managerPortNumber = 9000;
		responseTimeOut = 100000;
		MaxSimultaneousRequests = 200;
		appName = "DIVA_API_GUI";
		appInfo = "DIVA_API_GUI_APP";
		userName = "DIVA";
		userPassword = "";
		userInfo = "";
		siteName = "local";
	}

	private ArchiveRequest getArchiveRequest(String objectName, String category, String transferServerName,
											 String sourceDestPathName, String mediaName, ArrayList<String> fileNameList, String comment, String options,
											 int priority) {
		QOS qos = QOS.Default;
		Vector<String> fileNameListV = new Vector<String>();
		for (String s : fileNameList) {
			fileNameListV.add(s);
		}
		ArchiveRequest request = new ArchiveRequest(objectName, category, transferServerName, mediaName,
			sourceDestPathName, fileNameListV, qos, priority, comment, options);

		return request;
	}

	private RequestStatus submitRequest(Request request) throws Exception {
		RequestStatus rs = RequestStatus.Unknown;
		try {
			if (session == null)
				throw new Exception("No connection");

			session.submit(request);
			rs = request.getStatus();
			if (rs != RequestStatus.Running)
				throw new DivaException("Request did not start");
		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(DivaHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
		return rs;
	}

	// metody publiczne
	public boolean Connect(String managerHost) {
		boolean ret = false;
		try {
			DivaInstance diva = DivaApi.getDivaInstance(managerHost, managerPortNumber);
			diva.getDefaultPolicy().setResponseTimeoutMs(responseTimeOut);
			diva.getDefaultPolicy().setMaxSimultaneousRequests(MaxSimultaneousRequests);
			SessionParameters sp = new SessionParameters(managerHost, managerPortNumber, userName, userPassword,
				appName, appInfo, userInfo, siteName, "log");
			session = diva.createSession(sp, null);
			ret = true;
		} catch (DivaException e) {
			System.out.println(e.getMessage());
		}
		return ret;
	}

	public boolean Disonnect() {
		boolean ret = false;
		try {
			if (session.isClosed())
				ret = true;
			else {
				session.close();
				ret = true;
			}
		} catch (Exception ex) {
			System.out.println("DIVA API session disconection error!");
			System.out.println(ex.getMessage());
			ret = false;
		}
		return ret;
	}

	public boolean isRequestRunning(Request request) {
		RequestStatus status = request.getStatus();
		boolean isRunning = false;
		if (status == RequestStatus.Running || status == RequestStatus.Unknown || status == RequestStatus.Submitted) {
			isRunning = true;
		}
		if (status == RequestStatus.Cancelled || status == RequestStatus.Aborted || status == RequestStatus.Rejected
			|| status == RequestStatus.NotSubmitted || status == RequestStatus.Completed
			|| status == RequestStatus.PartiallyAborted) {
			isRunning = false;
		}
		return isRunning;
	}

	public double getRequestProgress(Request request) {
		return request.getInfo().getProgress();
	}

	public ArchiveRequest archiveObject(String objectName, String category, String transferServerName,
										String sourceDestPathName, String mediaName, ArrayList<String> fileNameList, String comment, String options,
										int priority) {
		ArchiveRequest ar = getArchiveRequest(objectName, category, transferServerName, sourceDestPathName, mediaName,
			fileNameList, comment, options, priority);
		try {
			submitRequest(ar);
		} catch (Exception e) {
			java.util.logging.Logger.getLogger(DivaHandler.class.getName()).log(Level.WARNING, null, e);
		}
		return ar;
	}

	//	public DivaObjectInfo getObjectInfo(String objectName, String objectCat) {
//
//		DivaObject o = new DivaObject(objectName, objectCat);
//
//		DivaObjectInfo doi = null;
//		try {
//			doi = session.getObjectInfo(o);
//		} catch(Exception die) {
//			//throw new Exception(die.getMessage());
//
//			System.out.println(die.getMessage());
//		}
//
//		return doi;
//
//	}
	public String getObjectListByFileName(String fileName, String objectCat) {
		System.out.print("*** getObjectListByFileName ***\n");

		// fileName = promptForInput("File name", fileName);
		// objectCat = promptForInput("Category Mask", objectCat);

		ObjectListByFileNameInfo info;
		try {
			info = session.getObjectListByFileName(fileName, objectCat);
		} catch (DivaException e) {
			// throw new DivaException(e.getMessage(), e);// pritn error
			return "";
		}

		String text = "\n---------- Response ----------\n";
//		if(info == null) {
//		2k po rekonstrukcji, po korekcji koloru 16bit RGB P3
//			return text += "Error processing the command";
//		}
//
//		if(info.getStatus() == 0) {
//			text += "Success";
//		} else {
//			text += ApiMessagingConstants.apiStatusNames[info.getStatus()];
//		}
//		text += "\n\n";

		String namesList[] = info.getObjectNamesList();
		String catsList[] = info.getObjectCatsList();

		text += "Objects found which contain the file, and match the category mask :\n";
		for (int i = 0; (i < namesList.length) && (i < catsList.length); i++) {
			text += ("\tobject (name/category) [" + i + "] : " + namesList[i] + "/" + catsList[i] + "\n");
			System.out.print("\tobject (name/category) [" + i + "] : " + namesList[i] + "/" + catsList[i] + "\n");
		}

		if (namesList.length == 0) {
			text += "      none\n";
		}
		String s = "false";
		if (info.doMoreObjectsExist()) {
			s = "true";
		}
		text += "\nState of more objects exist flag : " + s + "\n";

		return text;
	}

	//	public ArrayList<DivaObjectInfo> SearchByObjectNamePart(String objectNamePart, String objectCatPart) {
//		// String objectNameAndCat = "";
//
//		// int previousListType = -1;
//		int listType = ApiMessagingConstants.LIST_OBJECTS;
//		//int objectListType = ApiMessagingConstants.CREATED_SINCE;
//		int objectListType = ApiMessagingConstants.CREATED_SINCE;
//		int objectLevelOfDetail = ApiMessagingConstants.OBJECTNAME_AND_CATEGORY_DETAIL;
//		int startDate = -1;
//		// String objectNameParts = "*";
//		// String objectCat = "*";
//		String mediaName = "*";
//		int listSize = 1500; //
//
//		ObjectDetailsListFilter filter = new ObjectDetailsListFilter();
//		filter.setListType(listType);
//		filter.setObjectListType(objectListType); //musi byc
//		filter.setObjectName(objectNamePart);
//		filter.setCategory(objectCatPart);
//		filter.setGroup(mediaName);
//		filter.setInitialTime(startDate);
//		filter.setMaxFetch(listSize);
//		filter.setLevelOfDetail(objectLevelOfDetail); //nie musi
//
//		ObjectDetailsList list = null;
//		try {
//			list = session.getObjectDetailsList(filter);
//		} catch (DivaException e) {
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//			System.out.println(e.getMessage());
//			//System.out.println("guu");
//			//return null;
//
//		}
//
////		do {
////			ObjectDetailsList list = session.getObjectDetailsList(filter);
////			filter = (ObjectDetailsListFilter) (list.getFilter());
////			ArrayList<DivaObjectInfo> objectList = list.getList();
////
////
////		}while (!filter.equals(null));
//		filter = (ObjectDetailsListFilter) (list.getFilter());
//		ArrayList<DivaObjectInfo> objectList = list.getList();
//
////		String textOut= null;
////		int size = objectList.size();
////		for(int i=0; i<size;i++) {
////			if(objectList.get(i) instanceof DivaObjectInfo) {
////				DivaObjectInfo doi = (DivaObjectInfo) (objectList.get(i));
////				textOut += doi.getObjectName();
////			}
////		}
////		System.out.println(textOut);
//		return objectList;
//	}
	public ObjectDetailsList SearchByObjectNamePart(String objectNamePart, String objectCatPart)  {
		//public ArrayList<String> SearchByObjectNamePart(String objectNamePart, String objectCatPart)  {
		//public void  SearchByObjectNamePart(String objectNamePart, String objectCatPart) throws DivaException {
		ObjectDetailsListFilter filter = new ObjectDetailsListFilter();
		filter.setFistTime(true);
		filter.setListPosition(new ArrayList<>());
		filter.setListPositionInitialTime(0);
		filter.setInitialTime(0);
		filter.setCategory(objectCatPart);
		filter.setMaxFetch(1500);
		// if (cmd.getOptionValue("s", "").equals("")) {
		// throw new Exception("Wyrażenie wyszukiwania nie może być puste");
		//}
		filter.setObjectName(objectNamePart);
		filter.setListType(ApiMessagingConstants.LIST_OBJECTS);
		filter.setObjectListType(ApiMessagingConstants.CREATED_SINCE);
		ObjectDetailsList list = null;
		try {
			list = session.getObjectDetailsList(filter);
		} catch (DivaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (list.getList() == null) {
			System.out.println("Brak wyników wyszukiwania!");
		} else {
			//list.getList().stream().forEach(o -> System.out.println(((DivaObjectInfo) o).getObject().getName()));
		}


		return list;
	}

	public RestoreRequest RestoreObject(String objectID, String objectCategory, String destination,
										String filesPathRoot, int priority) {
		//if(!ConnectionStatus()) {

		//}
		QOS qos = QOS.DirectOnly;
		int additionalServises = 0; //default value
		String archiveOptions = "";

		RestoreRequest request = new RestoreRequest(objectID, objectCategory, destination, filesPathRoot, qos,
			additionalServises, archiveOptions, priority);

		try {
			submitRequest(request);
		} catch (Exception e) {
			java.util.logging.Logger.getLogger(DivaHandler.class.getName()).log(Level.WARNING, null, e);
		}
//		if(request.getRequestId() == -1) {
//			try {
//				for(int id : session.getObjectInfo(new DivaObject(objectID,objectCategory)).relatedActiveRequests) {
//					RestoreRequest req = (RestoreRequest) session.findRequest(id);
//					if(!req.isFinished() && req.getFilesPathRoot().equals(filesPathRoot)) {
//						return req;
//					}
//				}
//			}catch (DivaException e) {
//			}
//		}
//		try {
//			submitRequest(request);
//		} catch (Exception e) {
//			java.util.logging.Logger.getLogger(DivaHandler.class.getName()).log(Level.WARNING, null, e);
//		}
//		return request;

		return request;
	}

//	private boolean isResponseYes(String promptText) throws Exception{
//		return isResponseYes(promptText, "Y");
//	}
//
//	private boolean isResponseYes(String promptText, String dflt) throws Exception {
//		String s = promptForInput(promptText, dflt);
//		s = s.toUpperCase();
//		if((s.compareTo("Y") != 0) && (s.compareTo("YES") != 0))
//			return false;
//
//		return true;
//	}

	public String getDiskArrayList() {//throws Exception {
		//System.out.print("*** getDiskArrayList ***\n");

		//otherOptions = promptForInput("options", otherOptions);

		//System.out.println("\n--- Get Disk Array List ---");
		//if(!isResponseYes("\nContinue?"))
		//throw new Exception("Cancelled by user");
		Array[] arrayList = null;
		try {
			arrayList = session.getArrayList(null);
		} catch (DivaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//CommandFailureException.getError();
		}

		String s = "\n---------- Response ----------\n";
		s += "Success \n\n";

		if(arrayList == null)
			s = "  no arrays are connected / feature not supported by Manager";
		else {
			for(Array anArrayList : arrayList) {
				s += "\n  Array name: " + anArrayList.getName() + "\n\n";

				Disk[] diskList = anArrayList.getDisks();
				if(diskList == null)
					s += "    No disks are connected" + "\n";
				else
					for(Disk aDiskList : diskList) {
						s += "    Name: " + aDiskList.getName() + "\n";
						s += "    Status: " + aDiskList.getStatus() + "\n";
						s += "    Total size: " + aDiskList.getTotalSize() + "\n";
						s += "    Origional available size: " + aDiskList.getOriginalAvailableSize() + "\n";
						s += "    Current remaining size: " + aDiskList.getCurrentRemainingSize() + "\n";
						s += "    Used size for storage: " + aDiskList.getUsedSizeForStorage() + "\n";
						s += "    Used size for cache: " + aDiskList.getUsedSizeForCache() + "\n";
						s += "    Min free space: " + aDiskList.getMinFreeSpace() + "\n";
						s += "    Is writeable: " + aDiskList.isWritable() + "\n";
						s += "    Max thrughput: " + aDiskList.getMaxThroughput() + "\n";
						s += "    Current remaining size: " + aDiskList.getCurrentRemainingSize() + "\n";
						s += "\n";
					}
			}
		}
		System.out.println(s);
		return s;
	}


}

