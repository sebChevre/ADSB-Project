package grailapp

import grailapp.model.Event

class EventController {

    def eventService;

    def index() {

        render (view: "index")
    }

    def sendEvent(String message) {

        def event = new Event(msg: message, date: new Date())

        log.info "sendEvent, with message: ${event}"

        eventService.sendEvent(event)

        render "event send :${event} "
    }
}
