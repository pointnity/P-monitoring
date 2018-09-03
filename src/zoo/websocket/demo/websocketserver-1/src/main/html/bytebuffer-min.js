(function(global,factory){if(typeof define==='function'&&define["amd"])
define(["long"],factory);else if(typeof require==='function'&&typeof module==="object"&&module&&module["exports"])
module['exports']=(function(){var Long;try{Long=require("long");}catch(e){}
return factory(Long);})();else
(global["dcodeIO"]=global["dcodeIO"]||{})["ByteBuffer"]=factory(global["dcodeIO"]["Long"]);})(this,function(Long){"use strict";var ByteBuffer=function(capacity,littleEndian,noAssert){if(typeof capacity==='undefined')
capacity=ByteBuffer.DEFAULT_CAPACITY;if(typeof littleEndian==='undefined')
littleEndian=ByteBuffer.DEFAULT_ENDIAN;if(typeof noAssert==='undefined')
