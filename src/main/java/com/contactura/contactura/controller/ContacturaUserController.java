package com.contactura.contactura.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.contactura.contactura.model.Contactura;
import com.contactura.contactura.model.ContacturaUser;
import com.contactura.contactura.repository.ContacturaUserRepository;

@RestController
@RequestMapping({ "/contacturauser" })
public class ContacturaUserController {

	@Autowired
	private ContacturaUserRepository repository; // http://localhost:8090/contacturauser

	// List All - http://localhost:8090/contacturauser
	@GetMapping
	public List<ContacturaUser> findAll() {
		return repository.findAll();
	}

	// Find By Id - http://localhost:8090/contacturauser/{id}
	@GetMapping(value = "{id}")
	public ResponseEntity<ContacturaUser> findById(@PathVariable Long id) {
		Optional<ContacturaUser> contacturaUser = repository.findById(id);
		if (contacturaUser.isPresent()) {
			return ResponseEntity.ok(contacturaUser.get());
		}
		return ResponseEntity.notFound().build();
	}

	// Create - http://localhost:8090/contacturauser
	@PostMapping
	public ResponseEntity<ContacturaUser> create(@RequestBody ContacturaUser contacturaUser,
			UriComponentsBuilder uriBuilder) {
		repository.save(contacturaUser);

		URI uri = uriBuilder.path("/contacturauser/{id}").buildAndExpand(contacturaUser.getId()).toUri();
		return ResponseEntity.created(uri).body(contacturaUser);
	}

	// Update - http://localhost:8090/contactura/{id}
	@PutMapping(value = "{id}")
	public ResponseEntity<ContacturaUser> update(@PathVariable Long id, @RequestBody ContacturaUser contacturaUser) {

		Optional<ContacturaUser> optional = repository.findById(id);
		if (optional.isPresent()) {
			ContacturaUser update = optional.get();
			update.setName(contacturaUser.getName());
			update.setAdmin(contacturaUser.getAdmin());
			update.setPassword(contacturaUser.getPassword());
			update.setUserName(contacturaUser.getUserName());
			repository.save(update);
			return ResponseEntity.ok().body(update);
		}
		return ResponseEntity.notFound().build();
	}

	// Delete - http://localhost:8090/contacturauser/{id}

	@DeleteMapping(path = { "/id" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Optional<ContacturaUser> optional = repository.findById(id);
		if (optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();

	}
}
