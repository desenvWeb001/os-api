package br.com.os.api.osapi.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.os.api.osapi.domain.model.Cliente;
import br.com.os.api.osapi.domain.model.ClienteRepository;
import br.com.os.api.osapi.domain.model.Comentario;
import br.com.os.api.osapi.domain.model.ComentarioRepository;
import br.com.os.api.osapi.domain.model.OrdemServico;
import br.com.os.api.osapi.domain.model.OrdemServicoRepository;
import br.com.os.api.osapi.domain.model.StatusOrdemServico;
import br.com.os.api.osapi.exception.NegocioException;

@Service
public class OsService {
	
	@Autowired
     OrdemServicoRepository osrepository;
	
	@Autowired
	ClienteRepository repoCliente;
	
	@Autowired
	ComentarioRepository comentarioRepository;
	
	
	public OrdemServico criarOs(OrdemServico os ) {
		
		
		Cliente cliente = repoCliente.findById(os.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Código não existe"));
		
		// setando status inicial
		os.setCliente(cliente);
		os.setStatus(StatusOrdemServico.ABERTA);
		os.setDataAbertura(OffsetDateTime.now());
		return osrepository.save(os);
	}
	
	public Comentario criarComentario(Long idOdermServico, String comentario ) {
		
		//fazendo uma vusca nas ordens de servico
		OrdemServico os = osrepository.findById(idOdermServico)
				.orElseThrow(() -> new NegocioException("Os não encontrada"));
				
		Comentario dadosComentario = new Comentario();
		dadosComentario.setDataAbertura(OffsetDateTime.now());
		dadosComentario.setDescricao(comentario);
		dadosComentario.setOrdemServico(os);
		
		return comentarioRepository.save(dadosComentario);
	}
	
	public void finalizar(Long idOdermServico) {
			
			//fazendo uma vusca nas ordens de servico
			OrdemServico os = osrepository.findById(idOdermServico)
					.orElseThrow(() -> new NegocioException("Os não encontrada"));
			
			//metodo implementado dentro da classe OrdemServico	
			os.finalizar();
			
			osrepository.save(os);
		}
	
	}
