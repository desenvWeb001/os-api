package br.com.os.api.osapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.os.api.osapi.domain.model.ClienteRepository;
import br.com.os.api.osapi.domain.model.Comentario;
import br.com.os.api.osapi.domain.model.ComentarioRepository;
import br.com.os.api.osapi.domain.model.OrdemServico;
import br.com.os.api.osapi.domain.model.OrdemServicoModel;
import br.com.os.api.osapi.domain.model.OrdemServicoRepository;
import br.com.os.api.osapi.domain.service.OsService;

@RestController
@RequestMapping("/service")
public class OrdemServicoController {
	
	@Autowired
    OrdemServicoRepository osRepository;
	
	@Autowired
	OsService osService;
	
	@Autowired
    ClienteRepository clienteRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ComentarioRepository comentarioRepository;
	
	/*
	 * se eu quiser tirar o Ordem de servico do metodo post basta criar outra classe de representação 
	 * e dentro dela colocar como atributo os dados de entrada e criar outr classe para referenciar o id
	 */
	
	
	@PostMapping("/criar")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<OrdemServicoModel> cadastro(@Valid @RequestBody OrdemServico os ) {
		OrdemServicoModel dados = toModel(osService.criarOs(os));
		return ResponseEntity.ok(dados);
	}
	
	@PostMapping("/criar/{id}/comentario")
	public ResponseEntity<Comentario> cadastro(@PathVariable Long id, @Valid @RequestBody Comentario comentario ) {
		Comentario dados = osService.criarComentario(id,comentario.getDescricao()	);
		return ResponseEntity.ok(dados);
	}
	
	@PutMapping("/{id}/finalizar")
	public void finalizar(@PathVariable Long id) {
		osService.finalizar(id);
	}
	
	
	@GetMapping("/os")
	public List<OrdemServicoModel> listar()  {
		//retorna uma lista de os
		return toCollectionModel(osRepository.findAll());
		//return clienteRepository.findByNome("Felipe");
	}
	
	@GetMapping("/os/{id}")
	public ResponseEntity <OrdemServicoModel> buscaId(@PathVariable Long id) {
		Optional<OrdemServico> ordemServico = osRepository.findById(id);
		
		// se tiver alguma coisa em cliente ele retorna um 200
		if(ordemServico.isPresent()) {
			
			//fazendo uma conversão para usar o padrão DTO classes de representação
			OrdemServicoModel model = toModel(ordemServico.get());
			return ResponseEntity.ok(model);
		}
		// se não tiver nada dentro de cliente retorna 404
		return ResponseEntity.notFound().build();
	}
	
	//metodo para converter a ordem de serviço na representação
	
	public OrdemServicoModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoModel.class);
	}
	
	//metodo para converter lista de os para passar a considerar a representation
	
	public List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordemServicos){
		return ordemServicos.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	
}
