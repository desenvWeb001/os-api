package br.com.os.api.osapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.os.api.osapi.domain.model.Cliente;
import br.com.os.api.osapi.domain.model.ClienteRepository;
import br.com.os.api.osapi.domain.service.CadastroService;

@RestController
@RequestMapping("/api")
public class ClienteController {
	

	
	@Autowired
    ClienteRepository clienteRepository;
	
	@Autowired
	CadastroService serviceCadastro;
	
	@GetMapping("/clientes")
	public List<Cliente> listar()  {
		//retorna uma lista de clientes
		return clienteRepository.findAll();
		//return clienteRepository.findByNome("Felipe");
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity <Cliente> buscaId(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		// se tiver alguma coisa em cliente ele retorna um 200
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		// se não tiver nada dentro de cliente retorna 404
		return ResponseEntity.notFound().build();
	}
	
	/* a chamada de uma exception deve ser feita no metodo do service e no endpoint do controller throws NomeClasseExcepiton
	 * 
	 * no metodo do service deve conter a chamada da exception
	 */
	
	
	/*
	@GetMapping("/clientes/{id}")
	public ResponseEntity <Cliente> buscaId(@PathVariable Long id) throws NomeClasseExcepiton {
		Optional<Cliente> cliente = clienteRepository.findById(id).orElseThrow(() -> new NomeClasseExcepiton(id));
		
		// se tiver alguma coisa em cliente ele retorna um 200
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		// se não tiver nada dentro de cliente retorna 404
		return ResponseEntity.notFound().build();
	}*/
	
	
	// retornando um 201 para criação inves de 200
	@PostMapping("/cadastrar")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cliente> cadastro(@Valid @RequestBody Cliente cliente ) {
		Cliente dados = serviceCadastro.cadastro(cliente);
		return ResponseEntity.ok(dados);
	}
	
	/*
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cliente> cadastro(@Valid @RequestBody Cliente cliente ) {
		Cliente dados = clienteRepository.save(cliente);
		return ResponseEntity.ok(dados);
	}*/
	
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity <Cliente> atualiza(@Valid @PathVariable Long id, @RequestBody Cliente cliente  ) {
		
		// verifica se o id passado existe
		if(!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		//pegando a instancia do cliente selecionado para atualização do cadastro e atualizando
		cliente.setId(id);
		cliente = clienteRepository.save(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity <Void> remove(@PathVariable Long id) {
		
		// verifica se o id passado existe
		if(!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		serviceCadastro.deletar(id);
		return ResponseEntity.noContent().build();
	
	}
	
	/*@DeleteMapping("/clientes/{id}")
	public ResponseEntity <Void> remove(@PathVariable Long id) {
		
		// verifica se o id passado existe
		if(!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		clienteRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	
	}*/
	
}