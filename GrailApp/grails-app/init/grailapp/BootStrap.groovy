package grailapp


class BootStrap {

    def init = { servletContext ->
        new Voiture(nom:"VW").save()
        new Voiture(nom:"Golg").save()
    }
    def destroy = {
    }
}
