package com.psybrainy.consumer.service

import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.jupiter.api.Test
import org.mockito.Mockito.timeout
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka


@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = ["hola"])
class ConsumerKafkaServiceTest {


    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @MockBean
    @Autowired
    private lateinit var externalService: ExternalService


    @Test
    fun `test listen receives messages correctly`() {
        val expectedMessage = "Hello Kafka!"
        kafkaTemplate.send(ProducerRecord("hola", expectedMessage))
        verify(externalService, timeout(10000)).execute(expectedMessage)

    }
}