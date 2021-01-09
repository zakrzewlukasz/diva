package org.springframework.diva.app.model;


import org.springframework.core.style.ToStringCreator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.diva.app.model.ObjectFeatures;

@Entity
@Table(name = "objects_rq")
public class ObjectRq extends ObjectFeatures {

	@Column(name = "operation_date")
	@NotEmpty
	private  String operationDate;

	@Column(name = "operation_time")
	@NotEmpty
	private  String operationTime;

	@Column(name = "operation_status")
	@NotEmpty
	private  String operationStatus;

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}


	@Override
	public String toString() {
		return new ToStringCreator(this)

			.append("id", this.getId()).append("new", this.isNew()).append("nameObj", this.getNameObj())
			.append("categoryObj", this.getCategoryObj()).append("commentObj", this.getCommentObj()).append("sourceDiskObj", this.getSourceDiskObj())
			.append("directoryObj", this.getDirectoryObj()).append("destinationDiskObj", this.getDestinationDiskObj())
			.append("optionsObj", this.getOptionsObj()).append("fileNameList", this.getFileNameList())
			.append("operationPriority", this.getOperationPriority()).append("operationDate", this.operationDate)
			.append("operationTime", this.operationTime).append("operationStatus", this.operationStatus).toString();
	}

}
