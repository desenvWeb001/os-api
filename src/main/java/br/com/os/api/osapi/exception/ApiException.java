package br.com.os.api.osapi.exception;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//indica que é um componente que vai tratar excepition de todas as classes

@ControllerAdvice
public class ApiException extends ResponseEntityExceptionHandler{
	
	
	// anotação indicando que quando a exceção for lançada caia aqui
	// a classe no parametro a classe da exceção 
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		// tipo do erro da requisição
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problema problema = new Problema();
		//valor do erro ex:400
		problema.setStatus(status.value());
		// mensagem passada para classe da EXCEPTION
		problema.setTitulo(ex.getMessage());
		problema.setTime(LocalDateTime.now());
		
		// PARAM ( EXCPTION, CORPO DA MENSAGEM, CABEÇALHO VAZIO, STATUS ERRO 405, REQUISIÇÃO
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
		
	}
	
	// tratando exeção de campos não validos CONFORME CONFIGURADO PELO BEAN VALIDATION no model para o consumidor da api
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Problema problema = new Problema();
		
		problema.setStatus(status.value());
		problema.setTitulo("um ou mais campos vazios");
		problema.setTime(LocalDateTime.now());
		
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	// METODO PARA PERSONALIZAR ERROS NOT FOUND falta cuztomizar
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		Problema problema = new Problema();
		
		problema.setStatus(status.value());
		problema.setTitulo("recurso não encontrado");
		problema.setTime(LocalDateTime.now());
		
		return super.handleExceptionInternal(ex, problema, headers, status, request);
		//return super.handleNoHandlerFoundException(ex,headers, status, request);
	}
	
	// metodo para tratar internal server erro falta cuztomizar
	
	/*
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		Problema problema = new Problema();
		
		problema.setStatus(status.value());
		problema.setTitulo("dado enviado não valido");
		problema.setTime(LocalDateTime.now());
		
		return super.handleExceptionInternal(ex, problema, headers, status, request);
		//return super.handleExceptionInternal(ex, body, headers, status, request);
	}*/
	
	
	

	
	// para localizar um metodo para sobrescrita pasta iniciar digitando
	/*
	 * quando a exeption acontecer o spring irá executar o metótodo correspondente 
	 * 
	 * execeptionpodem ser costumizadas
	 * 
	 */
	//handleMet
}
