package com.example.weblab2.repositories;

import com.example.weblab2.entities.TelegramLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramLogsRepository extends JpaRepository<TelegramLogs, Long> {
}
