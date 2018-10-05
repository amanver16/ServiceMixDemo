package com.stpl.gtn.company.domain.event;

public enum CompanyMsEventType {
	CompanyAdded("CompanyAdded"), CompanyAdditionFailed("CompanyAdditionFailed"), CompanyValidated(
			"CompanyValidated"), CompanyUpdated("CompanyUpdated"), CompanyUpdationFailed(
					"CompanyUpdationFailed"), CompanyDeletionFailed("CompanyDeletionFailed"), CompanyDeleted(
							"CompanyDeleted"), CompanyQualifierAttached(
									"CompanyQualifierAttached"), CompanyQualifierNotAttached(
											"CompanyQualifierNotAttached"), CompanyTradeClassAttached(
													"CompanyTradeClassAttached"), CompanyTradeClassNotAttached(
															"CompanyTradeClassNotAttached"), ParentCompanyAttached(
																	"ParentCompanyAttached"), ParentCompanyNotAttached(
																			"ParentCompanyNotAttached"), NotesNotAttached(
																					"NotesNotAttached"), NotesAttached(
																							"NotesAttached"), CompanyQualifierNotDetached(
																									"CompanyQualifierNotDetached"), CompanyQualifierDetached(
																											"CompanyQualifierDetached"), CompanyTradeClassNotDetached(
																													"CompanyTradeClassNotDetached"), CompanyTradeClassDetached(
																															"CompanyTradeClassDetached"), ParentCompanyNotDetached(
																																	"ParentCompanyNotDetached"), ParentCompanyDetached(
																																			"ParentCompanyDetached"), NotesNotDetached(
																																					"NotesNotDetached"), NotesDetached(
																																							"NotesDetached");
	private String eventName;

	private CompanyMsEventType(String eventName) {
		this.eventName = eventName;
	}

	public String getEventName() {
		return eventName;
	}

}
