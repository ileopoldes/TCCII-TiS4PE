package br.unisinos.tcc.tis4pe.wcf.exceptions;

public class DateFormatException extends RuntimeException {

	private static final long serialVersionUID = -4521401217798104659L;
	private RuntimeException originalException;
	
	public DateFormatException(String message){
		super(message);
	}
	
	public DateFormatException(RuntimeException originalException, String message){
		super(message);
		this.originalException = originalException;
	}
	
	public DateFormatException(){
		super("Erro ao tentar converter string em data (string mal formado ou pattern desconhecido)");
	}

	public Exception getOriginalException() {
		return originalException;
	}
}
