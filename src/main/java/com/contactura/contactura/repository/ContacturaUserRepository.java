package com.contactura.contactura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactura.contactura.model.ContacturaUser;

public interface ContacturaUserRepository extends JpaRepository<ContacturaUser, Long> {

}
