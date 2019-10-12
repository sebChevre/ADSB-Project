<!doctype html>
<html>
<head>
    <title>Welcome to Grails</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <script src="//cdn.jsdelivr.net/sockjs/0.3.4/sockjs.min.js"></script>

    <asset:stylesheet src="bootstrap.css"/>

</head>
<body>

<div class="container">

    <!-- jumboton start -->
    <div class="jumbotron">
        <h1 class="display-4">Grails Lab SSE example</h1>
        <p class="lead">Projet web basé sur le framework grails. Exemple de messages SSE</p>
        <hr class="my-4">

        <p>Composant de l'architecture du projet adsb. C'est le client final</p>

        <hr class="my-4">
        <!-- liste des composants -->
        <div class="row">

            <div id="sse-events" class="col-md-12">
                <h2>Events SSE from : <small id="sse-uri">[yep]</small></h2>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Server Sent Events</h3>
                    </div>
                    <div class="panel-body">
                        <button id="sse-start" type="button" class="btn btn-primary" style="margin: 20px">Start SSE</button>
                        <button id="sse-stop" type="button" class="btn btn-danger" style="margin: 20px">Stop SSE</button>


                        <ul id="eventList" class="list-group sselist">
                        </ul>

                    </div>

                </div>
            </div>


        </div><!-- /liste des composants -->
    </div>

</div>


</div><!-- /jumboton start -->


<asset:javascript src="application.js"/>
<asset:javascript src="public/event.js"/>

</body>
</html>


