package com.example.seller

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OpportunityConsumer(val advertisementProducer: AdvertisementProducer) {

    private final val log = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = ["\${opportunity-topic}"])
    fun consumer(record: ConsumerRecord<String, Opportunity>) {
        log.info("Consuming Message=[${record.value()}] Key=[${record.key()}]")
        advertisementProducer.producer() // FIXME
    }

}