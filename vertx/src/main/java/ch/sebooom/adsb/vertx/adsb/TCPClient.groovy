package ch.sebooom.adsb.vertx.adsb


import groovy.json.JsonBuilder
import io.vertx.core.AbstractVerticle
import io.vertx.core.net.NetClientOptions
import io.vertx.core.net.NetSocket
import org.slf4j.LoggerFactory

class TCPClient extends AbstractVerticle {

    def logger = LoggerFactory.getLogger(TCPClient.class.name)


    @Override
    void start() throws Exception {

        def eventBus = vertx.eventBus()

        logger.info "TCPClientGroovy started"

        def options = new NetClientOptions().setConnectTimeout(1000);
        def client = vertx.createNetClient(options)


        client.connect(30003, "raspberrypi",{ res ->

            if (res.succeeded()) {
                logger.info "Connected!";
                NetSocket socket = res.result()

                socket.handler({ buffer ->

                    def lignes = buffer.toString().split("\n")

                    lignes.each {l ->

                        def ligne = "$l"

                        logger.debug "Ligne: $ligne"
                        def tokens = ligne.split(",")
                        def nbreToken = tokens.size()
                        logger.debug "spliting in $nbreToken"


                        def json = new JsonBuilder(new SBS1Message(address: "sbs1", type: "out",typeMsg: "MSG", typeNo: "asdas")).toPrettyString()

                        logger.debug "Json object before sending to evenbus: $json"
                        //eventBus.publish("sbs1",json )

                    }
                })

            } else {
               logger.error "Failed to connect: " + res.cause().getMessage()
            }
        })
    }
}
