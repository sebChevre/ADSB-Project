<!doctype html>
<html>
<head>
    <title>Welcome to Grails</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <asset:stylesheet src="bootstrap.css"/>
</head>
<body>

<div class="container">

    <!-- jumboton start -->
    <div class="jumbotron">
        <h1 class="display-4">Grails Lab sample project!</h1>
        <p class="lead">Projet web basé sur le framework grails.</p>
        <hr class="my-4">

        <p>Composant de l'architecture du projet adsb. C'est le client final</p>

    <hr class="my-4">
        <!-- liste des composants -->
        <div class="row">

            <div id="controllers" class="col-md-4">
                <h2>Controlleurs:</h2>
                <ul>
                    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                        <li class="controller">
                            <g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link>
                        </li>
                    </g:each>
                </ul>
            </div>

            <div id="domains" class="col-md-4">
                <h2>Ressources de domaines</h2>
                <ul>
                    <g:each var="c" in="${grailsApplication.domainClasses.sort { it.fullName } }">
                        <li class="domain">
                            <g:link domain="${c.logicalPropertyName}">${c.fullName}</g:link>
                        </li>
                    </g:each>
                </ul>
            </div>

            <div id="services" class="col-md-4">
                <h2>Services</h2>
                <ul>
                    <g:each var="c" in="${grailsApplication.serviceClasses.sort { it.fullName } }">
                        <li class="domain">
                            <g:link service="${c.logicalPropertyName}">${c.fullName}</g:link>
                        </li>
                    </g:each>
                </ul>
            </div>

        </div><!-- /liste des composants -->
    </div>

</div>


</div><!-- /jumboton start -->


<asset:javascript src="application.js"/>

</body>
</html>
