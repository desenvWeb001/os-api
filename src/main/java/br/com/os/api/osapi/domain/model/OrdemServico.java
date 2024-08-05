package br.com.os.api.osapi.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import br.com.os.api.osapi.exception.NegocioException;

@Entity
public class OrdemServico {
	
	@Id
    @GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	
	/*anotação para quanto colocar a validação no cliente ele não cascatear a validação para os outros campos
	@ConvertGroup(from = Default.class, to = VlidationGroups.class)
	*/
	
	// verificar essa anotation não lembro
	//@NotBlank
	@ManyToOne	
	private Cliente cliente;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private StatusOrdemServico status;
	
	
	//@JosonProperty(access = Access.READ_ONLY
	// DEFINI O CAMPO SÓ PARA LEITURA INIBINDO O USUÁRIO DE ENVIAR VALORES PARA O CAMPO
	
	// a classe ModelMapper criar representações das classes de modelo para usar no controller isso gera mais segurança para api
	
	
	@Column(name = "data")
	//private LocalDateTime dataAbertura;
	private OffsetDateTime dataAbertura;
	
	/*
	@OneToMany(mappedBy = "ordemServico")
	private List<Comentario> comentarios = new ArrayList<>();
	*/
	public Long getId() {
		return id;
	}
	/*
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}*/
	public void setId(Long id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public StatusOrdemServico getStatus() {
		return status;
	}
	public void setStatus(StatusOrdemServico status) {
		this.status = status;
	}
	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	
	public void finalizar() {
		if(!StatusOrdemServico.ABERTA.equals(getStatus())) {
			throw new NegocioException("Os não pode está finalizafa");
		}
		setStatus(StatusOrdemServico.FINALIZADA);
		//criar atributo data de finalização só tem a abertura
	}

}
