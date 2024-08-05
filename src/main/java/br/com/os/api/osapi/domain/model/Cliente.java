package br.com.os.api.osapi.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Cliente {
		
	
	@Id
    @GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	//valida se o valor não é null, vazio, ou só espaço não deixa
	//@Email verifica se formato do email é valido
	//@Size(Max= 20) determina o tamando do campo
	
	//@Valid anotation para validação de campo
	@NotBlank
	@Column(name = "nome")
	private String nome;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
		
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
