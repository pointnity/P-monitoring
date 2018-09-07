var ws;
function initWs() {
    ws = new WebSocket("ws://localhost:9321?name=Kobe Bryant &name= Kobe &name= in the library");
    ws.onmessage = function (event) {
        document.getElementById("contentId").value += (event.data + "\r\n");
    };
    ws.onclose = function (event) {
