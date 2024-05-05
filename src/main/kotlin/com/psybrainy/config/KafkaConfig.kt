package com.psybrainy.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer

@Configuration
class KafkaConfig {

    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String? = null

    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> {
        return ConcurrentKafkaListenerContainerFactory<String, String>().also { it.consumerFactory = consumerFactory() }
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> {
        val consumerProps = mapOf(
            ConsumerConfig.GROUP_ID_CONFIG to "consumer_group_id",
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,

            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",

            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
        )
        return DefaultKafkaConsumerFactory(consumerProps)
    }
}