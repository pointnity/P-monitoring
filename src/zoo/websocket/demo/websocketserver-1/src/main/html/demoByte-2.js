var ByteBuffer = window.dcodeIO.ByteBuffer;

var ws;
function initWs() {
    ws = new WebSocket("ws://localhost:9321?name=Kobe Bryant &name= Kobe Bryant Name= Kobe Bryant");
    ws.binaryType = 'arraybuffer';
    ws.onmessage = function (event) {
        if(event.data instanceof ArrayBuffer){
			var arrayBuffer = event.data;
			var byteBuffer = ByteBuffer.wrap(arrayBuffer);
			console.log(byteBuffer);
			//byteBuffer.flip();
			//console.log(byteBuffer);
			document.getElementById("contentId").value += (byteBuffer + "\r\n");
