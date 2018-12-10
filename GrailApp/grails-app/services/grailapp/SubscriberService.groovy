package grailapp

import grailapp.model.Event
import grails.events.annotation.Publisher
import grails.events.annotation.Subscriber
import grails.gorm.transactions.Transactional

@Transactional
class SubscriberService {

    @Subscriber
    def onSendEvent(Event event) {
       log.info "event receiveing, message : {$event}"
    }
}
