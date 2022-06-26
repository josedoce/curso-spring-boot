package github.josedoce.cursosb.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> list = new ArrayList<>();
	public ValidationError() {
		
	}
	public ValidationError(Integer status, String msg, Long timstamp) {
		super(status, msg, timstamp);
	}

	public List<FieldMessage> getErrors() {
		return list;
	}

	public void addError(String fieldName, String message) {
		this.list.add(new FieldMessage(fieldName, message));
	}
	
	
}
