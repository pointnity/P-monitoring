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

			var str = byteBuffer.readUTF8String(byteBuffer.limit);
			document.getElementById("contentId").value += (str + "\r\n");
			
		} else {
			document.getElementById("contentId").value += (event.data + "\r\n");
		}
    };
    ws.onclose = function (event) {

    };
    ws.onopen = function (event) {
        // var binary = new Uint8Array(6);
		// binary[0] = '1';
		// binary[1] = '2';
		// binary[2] = '3';
		// binary[3] = '4';
		// binary[4] = '5';
		// binary[5] = '6';
		// console.log(binary.buffer);
		// ws.send(binary.buffer);
		
		
		
		var bb = new ByteBuffer()
