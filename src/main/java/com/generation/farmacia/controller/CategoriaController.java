package com.generation.farmacia.controller;

import com.generation.farmacia.model.Categoria;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


	@RestController
	@RequestMapping("/categorias")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public class CategoriaController {

		@Autowired
		private CategoryRepository repository;

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
			return repository.findById(category.getId())
					.map(response -> ResponseEntity.status(HttpStatus.CREATED)
							.body(repository.save(category)))
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
}