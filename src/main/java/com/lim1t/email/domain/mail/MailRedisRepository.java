package com.lim1t.email.domain.mail;

import org.springframework.data.repository.CrudRepository;

public interface MailRedisRepository extends CrudRepository<Mail, String> {
}
