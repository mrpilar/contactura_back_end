package com.contactura.contactura.controller;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contactura.contactura.dto.MensagemSucessoDto;
import com.contactura.contactura.model.ContacturaUser;
import com.contactura.contactura.repository.ContacturaUserRepository;

@CrossOrigin()
@RestController
@RequestMapping({ "/user" })
public class ContacturaUserController {

	@Autowired
	private ContacturaUserRepository repository;

	@RequestMapping("/login")
	@GetMapping
	public List<String> login(HttpServletRequest request) {

		String authorization = request.getHeader("Authorization").substring("Basic".length()).trim();
		byte[] baseCred = Base64.getDecoder().decode(authorization);
		String credentialsParsed = new String(baseCred, StandardCharsets.UTF_8);
		String[] values = credentialsParsed.split(":", 2);
		ContacturaUser user = repository.findByUsername(values[0]);

		String token = request.getHeader("Authorization").substring("Basic".length()).trim();
		// ArrayList<String> lista = new ArrayList<>();
		// lista.add(Boolean.toString(user.isAdmin()));
		// lista.add(token);
		// return lista;

		return Arrays.asList(Boolean.toString(user.isAdmin()), token);
	}

	// List All - http://localhost:8090/user
	@GetMapping
	public List<ContacturaUser> findAll() {
		return repository.findAll();
	}

	/*
	 * // List All com JPQL - http://localhost:8090/user/find
	 * 
	 * @GetMapping(value = "/find") public List<ContacturaUser> findAllAtivas()
	 * { return repository.findAllAtivas(); }
	 */

	// Find By Id - http://localhost:8090/user/{id}
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// Create - http://localhost:8090/user
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ContacturaUser create(@RequestBody ContacturaUser user) {
		user.setPassword(encryptPassword(user.getPassword()));
		return repository.save(user);
	}

	// Update - http://localhost:8090/user/{id}
	@PutMapping(value = "{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody ContacturaUser user) {
		return repository.findById(id).map(record -> {
			record.setName(user.getName());
			record.setPassword(encryptPassword(user.getPassword()));
			record.setUsername(user.getUsername());
			record.setAdmin(user.isAdmin());
			ContacturaUser update = repository.save(record);
			return ResponseEntity.ok().body(update);
		}).orElse(ResponseEntity.notFound().build());
	}

	// Delete - http://localhost:8090/user/{id}

	@DeleteMapping(path = "{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable long id) {
		return repository.findById(id).map(record -> {
			repository.deleteById(id);
			MensagemSucessoDto mensagemSucessoDto = new MensagemSucessoDto("Deletado com sucesso!");
			return ResponseEntity.ok().body(mensagemSucessoDto);
		}).orElse(ResponseEntity.notFound().build());
	}

	private String encryptPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordCrypt = passwordEncoder.encode(password);

		return passwordCrypt;
	}
}
