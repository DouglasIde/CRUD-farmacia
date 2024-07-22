package com.generation.farmacia.controller;

import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.repository.ICategoriaRepository;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.server.ResponseStatusException;


	@RestController
	@RequestMapping("/categorias")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public class CategoriaController {

		@Autowired
		private ICategoriaRepository repository;

		@GetMapping
		public ResponseEntity<List<Categoria>> getAll() {
			return ResponseEntity.ok(repository.findAll());

		}

		@GetMapping("/{id}")
		public ResponseEntity<Categoria> getById(@PathVariable Long id) {
			return repository.findById(id)
					.map(response -> ResponseEntity.ok(response))
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		}

		@GetMapping("/descricao/{descricao}")
		public ResponseEntity<List<Categoria>> getByDescription(@PathVariable String descricao) {
			return ResponseEntity.ok(repository.findAllByDescriptionContainingIgnoreCase(descricao));
		}

		@PostMapping
		public ResponseEntity<Categoria> post(@RequestBody Categoria categoria) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(repository.save(categoria));
		}

		@PutMapping
		public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria) {
			return repository.findById(categoria.getId())
					.map(response -> ResponseEntity.status(HttpStatus.CREATED)
							.body(repository.save(categoria)))
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		}

		@ResponseStatus(HttpStatus.NO_CONTENT)
		@DeleteMapping("/{id}")
		public void delete(@PathVariable Long id) {
			Optional<Categoria> categoria = repository.findById(id);

			if (categoria.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);

			}
			repository.deleteById(id);
		}
	}
