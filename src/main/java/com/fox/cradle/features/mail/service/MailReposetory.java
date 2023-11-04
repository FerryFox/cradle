package com.fox.cradle.features.mail.service;

import com.fox.cradle.features.mail.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MailReposetory extends JpaRepository<Mail, String> {
}
