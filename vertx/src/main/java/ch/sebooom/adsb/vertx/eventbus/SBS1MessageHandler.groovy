package ch.sebooom.adsb.vertx.eventbus

import io.vertx.core.Handler
import io.vertx.core.eventbus.EventBus
import io.vertx.ext.bridge.BridgeEventType
import io.vertx.ext.web.handler.sockjs.BridgeEvent
import org.slf4j.LoggerFactory

class SBS1MessageHandler implements Handler<BridgeEvent> {

    def logger = LoggerFactory.getLogger(SBS1MessageHandler.class);
    EventBus eventBus

    SBS1MessageHandler(EventBus eventBus) {
        this.eventBus = eventBus
    }

    @Override
    void handle(BridgeEvent event) {

        logger.info event.type().toString()

        if (event.type() == BridgeEventType.SOCKET_CREATED){
            logger.info("A socket was created")
        }

        if (event.type() == BridgeEventType.SEND){
            logger.info(event.rawMessage.toString())
            eventBus.publish("out","ok out2")
            eventBus.publish("out","ok out3")
            eventBus.publish("out","ok out4")
            eventBus.publish("out","ok out5")
        }


        if (event.type() == BridgeEventType.REGISTER){

            //§§eventBus.publish("out","ok out")
            logger.info "register"
        }


        //eventBus.publish("out","ok out")


        event.complete(true)
    }
}
