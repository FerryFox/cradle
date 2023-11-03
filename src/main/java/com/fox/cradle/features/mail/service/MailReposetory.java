package com.fox.cradle.features.mail.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.mail.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailReposetory extends JpaRepository<Mail, String> {
}
