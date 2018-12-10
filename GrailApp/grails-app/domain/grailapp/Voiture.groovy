package grailapp

import grails.rest.Resource

@Resource(uri='/voitures')
class Voiture {

    String nom

    static constraints = {
        nom blank:false
    }
}
