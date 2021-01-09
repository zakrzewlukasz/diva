package org.springframework.diva.app.model;


import org.springframework.core.style.ToStringCreator;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;




@Entity
@Table(name = "objects_features")
public class ObjectFeatures extends BaseEntity {

	@Column(name = "name_obj")
	@NotEmpty
	private  String nameObj;

	@Column(name = "category_obj")
	@NotEmpty
	private  String categoryObj;

	@Column(name = "comment_obj")
	@NotEmpty
	private  String commentObj;

	@Column(name = "source_disk_obj")
	@NotEmpty
	private  String sourceDiskObj;

	@Column(name = "directory_obj")
	@NotEmpty
	private  String directoryObj;

	@Column(name = "destination_disk_obj")
	@NotEmpty
	private  String destinationDiskObj;

	@Column(name = "options_obj")
	@NotEmpty
	private  String optionsObj;

	@Column(name = "file_name_list")
	@NotEmpty
	private ArrayList<String> fileNameList;

	@Column(name = "operation_priority")
	@NotEmpty
	@Digits(fraction = 0, integer = 2)
	private  String operationPriority;





	public String getNameObj() {
		return nameObj;
	}

	public void setNameObj(String nameObj) {
		this.nameObj = nameObj;
	}

	public String getCategoryObj() {
		return categoryObj;
	}

	public void setCategoryObj(String categoryObj) {
		this.categoryObj = categoryObj;
	}

	public String getCommentObj() {
		return commentObj;
	}

	public void setCommentObj(String commentObj) {
		this.commentObj = commentObj;
	}

	public String getSourceDiskObj() {
		return sourceDiskObj;
	}

	public void setSourceDiskObj(String sourceDiskObj) {
		this.sourceDiskObj = sourceDiskObj;
	}

	public String getDirectoryObj() {
		return directoryObj;
	}

	public void setDirectoryObj(String directoryObj) {
		this.directoryObj = directoryObj;
	}

	public String getDestinationDiskObj() {
		return destinationDiskObj;
	}

	public void setDestinationDiskObj(String destinationDiskObj) {
		this.destinationDiskObj = destinationDiskObj;
	}

	public String getOptionsObj() {
		return optionsObj;
	}

	public void setOptionsObj(String optionsObj) {
		this.optionsObj = optionsObj;
	}

	public ArrayList<String> getFileNameList() {
		return fileNameList;
	}

	public void setFileNameList(ArrayList<String> fileNameList) {
		this.fileNameList = fileNameList;
	}

	public String getOperationPriority() {
		return operationPriority;
	}

	public void setOperationPriority( String operationPriority) {
		this.operationPriority = operationPriority;
	}

}
