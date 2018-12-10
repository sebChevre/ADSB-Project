package grailapp

import rx.Observer
import grails.rx.web.RxController
import groovy.transform.CompileStatic

@CompileStatic
class SSEController implements RxController{

    def index() {

        rx.stream { Observer observer ->

            def date = new Date()

            for(i in (0..5)) {
                if(i % 2 == 0) {



                    observer.onNext(
                            rx.event("message, le $date",id: i, event: 'tick', comment: 'tick')
                    )
                }
                else {
                    observer.onNext(
                            rx.event("ok boy",id: i, event: 'tick', comment: 'tick')
                    )

                }
                sleep 1000
            }


            observer.onCompleted()
        }
    }
}
