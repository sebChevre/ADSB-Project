package grailapp

import grailapp.model.Event
import grails.events.annotation.Publisher
import grails.gorm.transactions.Transactional

@Transactional
class EventService {

    @Publisher
    def sendEvent(Event event) {
        log.info "message sending : ${event}"
        event
    }
}
