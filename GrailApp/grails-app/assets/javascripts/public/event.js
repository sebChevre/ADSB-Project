//= require jquery-2.2.0.min
//= require bootstrap
//= require vertx-event-bus
//= require_self

let eventBus;
let evtSource;
let $sseStartBtn
let $sseStopBtn
let $eventList
$(function () {

    registerHandler();

    $eventList = $('#eventList');
    $sseStartBtn = $('#sse-start');
    $sseStopBtn = $('#sse-stop').hide();

    $sseStopBtn.on('click', function () {
        evtSource.close();
        $sseStopBtn.hide();
    });

    $sseStartBtn.on('click', function () {

        $sseStopBtn.show();


        evtSource = new EventSource("/SSE/index");

        evtSource.addEventListener("tick", function (e) {
            console.log(e)

            var datStr = "data: " + e.data + ", id: " + e.lastEventId;

            var elem = $('<li class="list-group-item">' + datStr + '</li>')

           // var newElement =  $('<li>', {
              //  "class": 'list-group-item',
              //  html: "data: " + e.data + ", id: " + e.lastEventId
           // })
           // var newElement = document.createElement("li");
            //newElement.

            console.log(e)

           $eventList.prepend(elem);
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




function increment() {
    console.log("incre")
    eventBus.send('in')
}

function registerHandler() {
    eventBus = new EventBus('http://localhost:9999/eb');

    eventBus.onopen = function () {

        console.log("open")

        eventBus.registerHandler('out', function (error, message) {
            console.log(error)
            console.log(message)

            //eventBus.send("in","hello")
            //$('#current_value').html(message)

        });
    }

}