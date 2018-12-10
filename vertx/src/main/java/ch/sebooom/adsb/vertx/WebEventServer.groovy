package ch.sebooom.adsb.vertx

import ch.sebooom.adsb.vertx.eventbus.SBS1MessageHandler;
import io.vertx.core.AbstractVerticle
import io.vertx.core.eventbus.EventBus
import io.vertx.ext.bridge.BridgeOptions
import io.vertx.ext.bridge.PermittedOptions
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions
import io.vertx.ext.web.handler.sockjs.SockJSSocket
import org.slf4j.LoggerFactory;

class WebEventServer extends AbstractVerticle{

    def logger = LoggerFactory.getLogger(WebEventServer.class.name)

   // EventBus eventBus = vertx.eventBus()


    @Override
    void start() throws Exception {
        logger.info "Starting SockJS Server"

        def eventBus = vertx.eventBus()
        def router = Router.router(vertx)

        SockJSHandlerOptions options = new SockJSHandlerOptions().setHeartbeatInterval(2000)

        SockJSHandler sockJSHandler = SockJSHandler.create(vertx)

        sockJSHandler.socketHandler({sockJSSocket ->

            // Just echo the data back
            sockJSSocket.handler({data ->

                startListenning(sockJSSocket, eventBus)
                logger.info "handler before write $data"
                sockJSSocket.write('{"msh":"ok"}')
            })

        })





        router.route("/eb/*").handler(sockJSHandler)
        vertx.createHttpServer()
                .requestHandler({h ->
            router.accept(h)}
        ).listen(9999)

    }

    void startListenning (SockJSSocket socket, EventBus eventBus) {

        eventBus.consumer("sbs1", {message ->

            def mb = message.body();

            logger.info mb

            socket.write(mb)

        });
    }


    SockJSHandler eventBusHandler() {
        BridgeOptions options = new BridgeOptions()
                .addOutboundPermitted(new PermittedOptions().setAddressRegex("out"))
                .addInboundPermitted(new PermittedOptions().setAddressRegex("in"));

        //SharedData data = vertx.sharedData();
        //CounterRepository repository = new CounterRepository(data);
        def eventBus = vertx.eventBus();
        SBS1MessageHandler sbs1MessageHandler = new SBS1MessageHandler(eventBus)

        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
        return sockJSHandler.bridge(options, sbs1MessageHandler);
    }

}
