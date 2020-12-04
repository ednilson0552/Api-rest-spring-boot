package com.soft.cadastro.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.soft.cadastro.model.Cliente;
import com.soft.cadastro.repository.ClienteRepository;
import com.soft.cadastro.servico.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/clientes")
@Api(value="API REST Clientes")
@CrossOrigin(value = "*")
public class Controller {

	@Autowired	
	private ClienteRepository clienteRepository;

	
	@Autowired 
	private ClienteService clienteService;

	@GetMapping("/login/{login}") 
	@ResponseStatus(HttpStatus.ACCEPTED)  
	@ApiOperation(value = "Verifica Login Basic", hidden = true)
	public String buscaLogin(@PathVariable String login,
//							 @PathVariable String senha, 
		@AuthenticationPrincipal UserDetails userDetails )  {
		return login;
	}
	
	
	@GetMapping  
	@ApiOperation(value = "Retorna Lista de Clientes")
	public List<Cliente> listar()  {
		return clienteRepository.findAll();
	}


	@GetMapping("/{clienteId}") 
	@ApiOperation(value = "Retorna Um unico Cliente cadastrado")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId  )  {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId); 

		if (!cliente.isPresent()) {
			return ResponseEntity.notFound().build(); // retorna 404 not found
		}
		// cria resposta (200 ok) para regtorno.
		return ResponseEntity.ok(cliente.get());
	}

	@GetMapping("/cpf/{cpf}") 
	@ApiOperation(value = "Verifica se CPF já esta cadastrado")
	public ResponseEntity<Cliente> buscarCpf(@PathVariable String cpf)  {
		Cliente cliente = clienteRepository.findByCpf(cpf); 

		if (cliente == null) {
			return ResponseEntity.notFound().build(); // retorna 404 not found
		}
		return ResponseEntity.ok(cliente);
	}
	
	@GetMapping("/email/{email}") 
	@ApiOperation(value = "Verifica se Email já esta cadastrado")
	public ResponseEntity<Cliente> buscarEmail(@PathVariable String email)  {
		Cliente cliente = clienteRepository.findByEmail(email); 

		if (cliente == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cliente);
	}
	
	@PostMapping 
	@ResponseStatus(HttpStatus.CREATED)  
	@ApiOperation(value = "Cadastra Novo Cliente")
	public Cliente Adicionar(@Valid @RequestBody Cliente cliente) { 
		return clienteService.save(cliente);
	}
	
	@PutMapping("/{clienteId}") 
	@ApiOperation(value = "Altera Cliente cadastrado")
	public ResponseEntity <Cliente> Atualizar(@Valid
											  @PathVariable Long clienteId, 
											  @RequestBody Cliente cliente)  {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		 
		cliente.setId(clienteId);
		cliente  = clienteService.update(cliente);

		return ResponseEntity.ok(cliente);
	}	

	
	@DeleteMapping("/{clienteId}") 
	@ApiOperation(value = "Exclui Cliente")
	public ResponseEntity <Void> Deletar(@PathVariable Long clienteId)  { 
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		clienteService.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}
	
}
