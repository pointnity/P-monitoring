(function(global,factory){if(typeof define==='function'&&define["amd"])
define(["long"],factory);else if(typeof require==='function'&&typeof module==="object"&&module&&module["exports"])
module['exports']=(function(){var Long;try{Long=require("long");}catch(e){}
return factory(Long);})();else
(global["dcodeIO"]=global["dcodeIO"]||{})["ByteBuffer"]=factory(global["dcodeIO"]["Long"]);})(this,function(Long){"use strict";var ByteBuffer=function(capacity,littleEndian,noAssert){if(typeof capacity==='undefined')
capacity=ByteBuffer.DEFAULT_CAPACITY;if(typeof littleEndian==='undefined')
littleEndian=ByteBuffer.DEFAULT_ENDIAN;if(typeof noAssert==='undefined')
noAssert=ByteBuffer.DEFAULT_NOASSERT;if(!noAssert){capacity=capacity|0;if(capacity<0)
throw RangeError("Illegal capacity");littleEndian=!!littleEndian;noAssert=!!noAssert;}
this.buffer=capacity===0?EMPTY_BUFFER:new ArrayBuffer(capacity);this.view=capacity===0?null:new Uint8Array(this.buffer);this.offset=0;this.markedOffset=-1;this.limit=capacity;this.littleEndian=littleEndian;this.noAssert=noAssert;};ByteBuffer.VERSION="5.0.1";ByteBuffer.LITTLE_ENDIAN=true;ByteBuffer.BIG_ENDIAN=false;ByteBuffer.DEFAULT_CAPACITY=16;ByteBuffer.DEFAULT_ENDIAN=ByteBuffer.BIG_ENDIAN;ByteBuffer.DEFAULT_NOASSERT=false;ByteBuffer.Long=Long||null;var ByteBufferPrototype=ByteBuffer.prototype;ByteBufferPrototype.__isByteBuffer__;Object.defineProperty(ByteBufferPrototype,"__isByteBuffer__",{value:true,enumerable:false,configurable:false});var EMPTY_BUFFER=new ArrayBuffer(0);var stringFromCharCode=String.fromCharCode;function stringSource(s){var i=0;return function(){return i<s.length?s.charCodeAt(i++):null;};}
function stringDestination(){var cs=[],ps=[];return function(){if(arguments.length===0)
return ps.join('')+stringFromCharCode.apply(String,cs);if(cs.length+arguments.length>1024)
ps.push(stringFromCharCode.apply(String,cs)),cs.length=0;Array.prototype.push.apply(cs,arguments);};}
ByteBuffer.accessor=function(){return Uint8Array;};ByteBuffer.allocate=function(capacity,littleEndian,noAssert){return new ByteBuffer(capacity,littleEndian,noAssert);};ByteBuffer.concat=function(buffers,encoding,littleEndian,noAssert){if(typeof encoding==='boolean'||typeof encoding!=='string'){noAssert=littleEndian;littleEndian=encoding;encoding=undefined;}
var capacity=0;for(var i=0,k=buffers.length,length;i<k;++i){if(!ByteBuffer.isByteBuffer(buffers[i]))
buffers[i]=ByteBuffer.wrap(buffers[i],encoding);length=buffers[i].limit-buffers[i].offset;if(length>0)capacity+=length;}
if(capacity===0)
