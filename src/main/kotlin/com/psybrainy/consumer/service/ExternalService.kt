package com.psybrainy.consumer.service

import org.springframework.stereotype.Component

@Component
class ExternalService {
    suspend fun execute(message: String): String {
        return message
    }
}