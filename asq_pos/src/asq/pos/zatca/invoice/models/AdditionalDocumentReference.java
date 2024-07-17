package asq.pos.zatca.invoice.models;

import java.util.UUID;

public class AdditionalDocumentReference {
	private String id;
	private String UUID;
	private UUID UUIDDigit;
	

	private String embeddedDocumentBinaryObject;
	
	public AdditionalDocumentReference(String id, String uUID, String embeddedDocumentBinaryObject) {
		super();
		this.id = id;
		UUID = uUID;
		this.embeddedDocumentBinaryObject = embeddedDocumentBinaryObject;
	}
	public AdditionalDocumentReference(String id, UUID uUIDDigit, String embeddedDocumentBinaryObject) {
		super();
		this.id = id;
		UUIDDigit = uUIDDigit;
		this.embeddedDocumentBinaryObject = embeddedDocumentBinaryObject;
	}
	public UUID getUUIDDigit() {
		return UUIDDigit;
	}
	public void setUUIDDigit(UUID uUIDDigit) {
		UUIDDigit = uUIDDigit;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String getEmbeddedDocumentBinaryObject() {
		return embeddedDocumentBinaryObject;
	}
	public void setEmbeddedDocumentBinaryObject(String embeddedDocumentBinaryObject) {
		this.embeddedDocumentBinaryObject = embeddedDocumentBinaryObject;
	}
	
	public AdditionalDocumentReference() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
}
