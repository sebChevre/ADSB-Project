//= require jquery-2.2.0.min
//= require bootstrap
//= require vertx-event-bus
//= require_self

let eventBus;
let evtSource;
let $sseStartBtn
let $sseStopBtn
let $eventList
let $sseUriLabel
const SSE_URI = "/SSE/sse"

$(function () {

    registerHandler();

    $eventList = $('#eventList');
    $sseStartBtn = $('#sse-start');
    $sseStopBtn = $('#sse-stop').hide();
    $sseUriLabel = $('#sse-uri').hide();


    $sseStopBtn.on('click', function () {
        evtSource.close();
        $sseStopBtn.hide();
        $sseUriLabel.hide();

    });

    //Start sse streming listenning
    $sseStartBtn.on('click', function () {

        evtSource = new EventSource(SSE_URI);

        $sseStopBtn.show();
        $eventList.children().remove()

        evtSource.onopen = function (e) {
            console.log('***** Event Source connection Open on url: ' + e.target.url + ' *****')
            $sseUriLabel.html(e.target.url).show()
        }

        evtSource.addEventListener("sse-test", function (e) {
            console.log(e)

            var datStr = "data: " + e.data + ", id: " + e.lastEventId + ", event: " + e.type;

            var elem = $('<li class="list-group-item">' + datStr + '</li>')

            console.log(e)

            addEventToList(elem)

            console.log($eventList.children().length)
        })



    })

    $('#ssebutton2').on('click', function () {


        console.log("incre")
        eventBus.send('in')

    })

    $( window ).unload(function() {
        evtSource.close()
    });
})

function addEventToList (element) {

    if($eventList.children().length === 10){
        $eventList.find('li:last-child').remove()
    }
    $eventList.prepend(element);

}


function increment() {
    console.log("incre")
    eventBus.send('in')
}

function registerHandler() {
    eventBus = new EventBus('http://localhost:9999/eb');

    eventBus.onopen = function (e) {

        console.log("Vertx EventBus connection open with SockJS")
        console.log(e)

        eventBus.registerHandler('out', function (error, message) {
            console.log(error)
            console.log(message)

            //eventBus.send("in","hello")
            //$('#current_value').html(message)

        });
    }

}