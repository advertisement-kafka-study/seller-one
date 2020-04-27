package com.example.seller

import io.cloudevents.v03.CloudEventBuilder
import io.cloudevents.v03.CloudEventImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.net.URI
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

@Service
class AdvertisementProducer(
        val kafkaTemplate: KafkaTemplate<String, CloudEventImpl<Advertisement>>,
        @Value("\${spring.application.name}") val applicationName: String
) {

    private final val log = LoggerFactory.getLogger(this::class.java)

    fun producer(opportunity: Opportunity) {
        val advertisement = Advertisement(id = opportunity.id, name = applicationName)
        val cloudEvent: CloudEventImpl<Advertisement> = CloudEventBuilder.builder<Advertisement>()
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("/advertisements/${advertisement.id}"))
                .withType("Advertisement")
                .withTime(ZonedDateTime.now(ZoneOffset.UTC))
                .withData(advertisement)
                .build()

        log.info("Publishing Message=[$cloudEvent] to Topic=[${kafkaTemplate.defaultTopic}]")
        kafkaTemplate.sendDefault(applicationName, cloudEvent)
    }

}