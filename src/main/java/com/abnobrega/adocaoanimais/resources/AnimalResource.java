package com.abnobrega.adocaoanimais.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
/*
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
*/
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abnobrega.adocaoanimais.domain.Animal;
import com.abnobrega.adocaoanimais.dtos.AnimalDTO;
//import com.abnobrega.adocaoanimais.domain.Animal;
import com.abnobrega.adocaoanimais.services.AnimalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController 							//Controlador Rest
@RequestMapping(value = "/api/animais") 	//Para dicionar o endpoint inicial para os serviços(endpoints) dos animai
@Api
public class AnimalResource {
	// Exemplo de Requisição Http para acessar um recurso de animal: localhost:8080/api/animais/1
	
	
    //*****************************************
    //**************  ATRIBUTOS  **************
    //*****************************************
	@Autowired
	private AnimalService animalService;	
	
    //*************************************************
    //************** MÉTODOS / ENDPOINTS **************
    //*************************************************
	
    //*********************************
    //******* C O N S U L T A R *******
    //*********************************	
	// Endpoint para consultar um Animal por Id		
	@GetMapping(value = "/{id}")	
	@ApiOperation("Consultar Animal por Id")
	public ResponseEntity<Animal> findById(@PathVariable @ApiParam("Id do animal") Integer id) {
		Animal obj = animalService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	// Endpoint para listar todos os animais	
	@GetMapping
	@ApiOperation("Listar Animais")	
	public ResponseEntity<List<AnimalDTO>> findAll() {
		List<Animal> lista = animalService.listarAnimais();
		List<AnimalDTO> listaDTO = lista.stream().map(obj -> new AnimalDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}	
	
    //*****************************
    //******* E X C L U I R *******
    //*****************************
	// Endpoint para excluir um Animal	
	@DeleteMapping(value = "/{id}") // Endpoint que recebe o id na URL
	@ApiOperation("Excluir Animal")	
	public ResponseEntity<AnimalDTO> excluirCliente(@PathVariable @ApiParam("Id do animal") Integer id) {
		animalService.excluirAnimal(id);
		return ResponseEntity.noContent().build();
	}	
	
    //*****************************
    //******* I N C L U I R *******
    //*****************************	
	// Endpoint para incluir um novo Animal
	@PostMapping
	@ApiOperation("Incluir Animal")	
	public ResponseEntity<AnimalDTO> incluirAnimal(@RequestBody AnimalDTO objDTO) {
		Animal newObj = animalService.incluirAnimal(objDTO);
		// Quando criamos um novo objeto no BD, é uma boa prátoca retornar a URI desse novo objeto.
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();		
	}
	
    //*********************************
    //******* A T U A L I Z A R *******
    //*********************************	
	// Endpoint para atualizar um Animal		
	@PutMapping(value = "/{id}")	
	@ApiOperation("Atualizar Animal")	
	public ResponseEntity<AnimalDTO> atualizarAnimal(@PathVariable @ApiParam("Id do animal") Integer id, 
													 @RequestBody AnimalDTO objDTO) {
		Animal newObj = animalService.atualizarAnimal(id, objDTO);
		
		return ResponseEntity.ok().body(new AnimalDTO(newObj));
	}

}
