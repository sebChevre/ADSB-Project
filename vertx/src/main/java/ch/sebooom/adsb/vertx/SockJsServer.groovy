package ch.sebooom.adsb.vertx

import ch.sebooom.adsb.vertx.eventbus.SBS1MessageHandler
import io.vertx.core.AbstractVerticle
import io.vertx.core.eventbus.EventBus

import io.vertx.ext.bridge.PermittedOptions
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.handler.sockjs.BridgeOptions
import io.vertx.ext.web.handler.sockjs.SockJSHandler

import java.util.function.Function

class SockJsServer extends AbstractVerticle {

    @Override
    void start() {
        Router router = Router.router(vertx);

        router.route("/eb/*").handler(eventBusHandler())
        router.route().handler(staticHandler())

        vertx.createHttpServer()
                .requestHandler({handler -> router.accept(handler)})
                .listen(9999)
    }

    private SockJSHandler eventBusHandler() {
        BridgeOptions options = new BridgeOptions()
                .addOutboundPermitted(new PermittedOptions().setAddressRegex("out"))
                .addInboundPermitted(new PermittedOptions().setAddressRegex("in"))


        EventBus eventBus = vertx.eventBus()
        SBS1MessageHandler counterHandler = new SBS1MessageHandler(eventBus)

        SockJSHandler sockJSHandler = SockJSHandler.create(vertx)
        return sockJSHandler.bridge(options, counterHandler)
    }

    private StaticHandler staticHandler() {
        return StaticHandler.create()
                .setCachingEnabled(false);
    }
}
