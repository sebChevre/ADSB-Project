package grailapp

import org.grails.web.json.JSONObject
import rx.Observer
import grails.rx.web.RxController
import groovy.transform.CompileStatic

import java.text.SimpleDateFormat

@CompileStatic
class SSEController implements RxController{

    def sse() {

        rx.stream { Observer observer ->



            def msg = new JSONObject().put("date",new Date().toInstant()).toString()
            def msgCpt = 0

            try{
                while(true) {


                    observer.onNext(
                            rx.event("msg: $msg", id: "$msgCpt", event: "sse-test")
                    )

                    sleep 1000
                    msgCpt ++

                }

                observer.onCompleted()
            }catch(Exception e){
                observer.onError(e)
                log.warn("Client SSE connection error : " + e)
            }





        }
    }
}
