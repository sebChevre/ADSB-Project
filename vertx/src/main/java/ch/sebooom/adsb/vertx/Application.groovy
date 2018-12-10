package ch.sebooom.adsb.vertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import org.slf4j.Logger
import org.slf4j.LoggerFactory;

class Application extends AbstractVerticle{

    final static Logger logger = LoggerFactory.getLogger(Application.class.name)

    static void main(String[] args) {

        def vertx = Vertx.vertx();

        vertx.deployVerticle("ch.sebooom.adsb.vertx.adsb.TCPClient", {handler ->

            logger.info "TCP Client started"
            logger.info handler.result()

        })

      /**  vertx.deployVerticle("ch.sebooom.adsb.vertx.WebEventServer", {handler ->

            logger.info "SockJs Server started"

        })*/

        vertx.deployVerticle("ch.sebooom.adsb.vertx.SockJsServer", {handler ->

            logger.info "SockJs Server started"
            logger.info handler.result()

        })
    }
}
