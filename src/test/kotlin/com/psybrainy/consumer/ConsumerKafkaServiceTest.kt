package com.psybrainy.consumer

import com.psybrainy.consumer.service.ExternalService
import org.apache.kafka.clients.producer.ProducerRecord
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.timeout
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka


@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = ["test-topic"])
class ConsumerKafkaServiceTest {


    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @MockBean
    private lateinit var externalService: ExternalService


    @Test
    fun `test listen receives messages correctly`() = runTest {

        val expectedMessage = "Hello Kafka!"

        kafkaTemplate.send(ProducerRecord("test-topic", expectedMessage))

        verify(externalService, timeout(10000)).execute(expectedMessage)

    }
}