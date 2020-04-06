package com.example.seller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class Service(
        val kafkaTemplate: KafkaTemplate<String, Advertisement>,
        @Value("\${spring.application.name}") val applicationName: String
) {

    private final val log = LoggerFactory.getLogger(this::class.java)

    private final val counter = AtomicInteger(1)

    @Scheduled(initialDelay = 1000, fixedDelay = 3000)
    fun producer() {
        val advertisement = Advertisement(id = counter.getAndIncrement().toString(), name = applicationName)

        log.info("Publishing Message=[$advertisement] to Topic=[${kafkaTemplate.defaultTopic}]")
        kafkaTemplate.sendDefault(applicationName, advertisement)
    }

}