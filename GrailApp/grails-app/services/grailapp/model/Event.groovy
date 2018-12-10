package grailapp.model

import groovy.transform.ToString

@ToString(includeNames=true)
class Event {

    String msg
    Date date

    static constraints = {
    }
}
