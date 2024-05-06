package com.psybrainy.consumer.service

import com.psybrainy.config.CompanionLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class ConsumerKafkaService(
    val externalService: ExternalService
) {

    @KafkaListener( topics = ["test-topic"], groupId = "consumer_group_id" )
    fun listen(message: String) = CoroutineScope(Dispatchers.IO).launch {
        log.info("Aca tenes el mensaje: {}", message)
        externalService.execute(message)
    }

    companion object : CompanionLogger()
}



