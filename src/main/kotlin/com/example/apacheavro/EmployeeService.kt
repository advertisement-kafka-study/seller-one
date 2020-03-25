package com.example.apacheavro

import com.example.apacheavro.domain.Employee
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmployeeService(val kafkaTemplate: KafkaTemplate<String, Employee>) {

    private final val log = LoggerFactory.getLogger(this::class.java)

    @Scheduled(initialDelay = 1000, fixedDelay = 3000)
    fun producer() {
        val employee = Employee()
        employee.setId(Random().nextInt())
        employee.setFirstName(UUID.randomUUID().toString());
        employee.setLastName(UUID.randomUUID().toString())

        log.info("Creating $employee")
        kafkaTemplate.sendDefault(employee)
    }

}