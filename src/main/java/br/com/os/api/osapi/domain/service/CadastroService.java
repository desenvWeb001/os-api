package br.com.os.api.osapi.domain.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.os.api.osapi.domain.model.Cliente;
import br.com.os.api.osapi.domain.model.ClienteRepository;

@Service
public class CadastroService {
	
	@Autowired
    ClienteRepository clienteRepository;
	
	
	public Cliente cadastro(Cliente cliente ) {
		
		//implmentar regras de negocio
		return clienteRepository.save(cliente);
	}
	
	public void deletar(Long id) {
		clienteRepository.deleteById(id);
	}

}
