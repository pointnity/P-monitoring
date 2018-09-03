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
return new ByteBuffer(0,littleEndian,noAssert);var bb=new ByteBuffer(capacity,littleEndian,noAssert),bi;i=0;while(i<k){bi=buffers[i++];length=bi.limit-bi.offset;if(length<=0)continue;bb.view.set(bi.view.subarray(bi.offset,bi.limit),bb.offset);bb.offset+=length;}
bb.limit=bb.offset;bb.offset=0;return bb;};ByteBuffer.isByteBuffer=function(bb){return(bb&&bb["__isByteBuffer__"])===true;};ByteBuffer.type=function(){return ArrayBuffer;};ByteBuffer.wrap=function(buffer,encoding,littleEndian,noAssert){if(typeof encoding!=='string'){noAssert=littleEndian;littleEndian=encoding;encoding=undefined;}
if(typeof buffer==='string'){if(typeof encoding==='undefined')
encoding="utf8";switch(encoding){case"base64":return ByteBuffer.fromBase64(buffer,littleEndian);case"hex":return ByteBuffer.fromHex(buffer,littleEndian);case"binary":return ByteBuffer.fromBinary(buffer,littleEndian);case"utf8":return ByteBuffer.fromUTF8(buffer,littleEndian);case"debug":return ByteBuffer.fromDebug(buffer,littleEndian);default:throw Error("Unsupported encoding: "+encoding);}}
if(buffer===null||typeof buffer!=='object')
throw TypeError("Illegal buffer");var bb;if(ByteBuffer.isByteBuffer(buffer)){bb=ByteBufferPrototype.clone.call(buffer);bb.markedOffset=-1;return bb;}
if(buffer instanceof Uint8Array){bb=new ByteBuffer(0,littleEndian,noAssert);if(buffer.length>0){bb.buffer=buffer.buffer;bb.offset=buffer.byteOffset;bb.limit=buffer.byteOffset+buffer.byteLength;bb.view=new Uint8Array(buffer.buffer);}}else if(buffer instanceof ArrayBuffer){bb=new ByteBuffer(0,littleEndian,noAssert);if(buffer.byteLength>0){bb.buffer=buffer;bb.offset=0;bb.limit=buffer.byteLength;bb.view=buffer.byteLength>0?new Uint8Array(buffer):null;}}else if(Object.prototype.toString.call(buffer)==="[object Array]"){bb=new ByteBuffer(buffer.length,littleEndian,noAssert);bb.limit=buffer.length;for(var i=0;i<buffer.length;++i)
bb.view[i]=buffer[i];}else
throw TypeError("Illegal buffer");return bb;};ByteBufferPrototype.writeBitSet=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(!(value instanceof Array))
throw TypeError("Illegal BitSet: Not an array");if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
var start=offset,bits=value.length,bytes=(bits>>3),bit=0,k;offset+=this.writeVarint32(bits,offset);while(bytes--){k=(!!value[bit++]&1)|((!!value[bit++]&1)<<1)|((!!value[bit++]&1)<<2)|((!!value[bit++]&1)<<3)|((!!value[bit++]&1)<<4)|((!!value[bit++]&1)<<5)|((!!value[bit++]&1)<<6)|((!!value[bit++]&1)<<7);this.writeByte(k,offset++);}
if(bit<bits){var m=0;k=0;while(bit<bits)k=k|((!!value[bit++]&1)<<(m++));this.writeByte(k,offset++);}
if(relative){this.offset=offset;return this;}
return offset-start;}
ByteBufferPrototype.readBitSet=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;var ret=this.readVarint32(offset),bits=ret.value,bytes=(bits>>3),bit=0,value=[],k;offset+=ret.length;while(bytes--){k=this.readByte(offset++);value[bit++]=!!(k&0x01);value[bit++]=!!(k&0x02);value[bit++]=!!(k&0x04);value[bit++]=!!(k&0x08);value[bit++]=!!(k&0x10);value[bit++]=!!(k&0x20);value[bit++]=!!(k&0x40);value[bit++]=!!(k&0x80);}
if(bit<bits){var m=0;k=this.readByte(offset++);while(bit<bits)value[bit++]=!!((k>>(m++))&1);}
if(relative){this.offset=offset;}
return value;}
ByteBufferPrototype.readBytes=function(length,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+length>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+length+") <= "+this.buffer.byteLength);}
var slice=this.slice(offset,offset+length);if(relative)this.offset+=length;return slice;};ByteBufferPrototype.writeBytes=ByteBufferPrototype.append;ByteBufferPrototype.writeInt8=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof value!=='number'||value%1!==0)
throw TypeError("Illegal value: "+value+" (not an integer)");value|=0;if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
offset+=1;var capacity0=this.buffer.byteLength;if(offset>capacity0)
this.resize((capacity0*=2)>offset?capacity0:offset);offset-=1;this.view[offset]=value;if(relative)this.offset+=1;return this;};ByteBufferPrototype.writeByte=ByteBufferPrototype.writeInt8;ByteBufferPrototype.readInt8=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+1>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+1+") <= "+this.buffer.byteLength);}
var value=this.view[offset];if((value&0x80)===0x80)value=-(0xFF-value+1);if(relative)this.offset+=1;return value;};ByteBufferPrototype.readByte=ByteBufferPrototype.readInt8;ByteBufferPrototype.writeUint8=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof value!=='number'||value%1!==0)
throw TypeError("Illegal value: "+value+" (not an integer)");value>>>=0;if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
offset+=1;var capacity1=this.buffer.byteLength;if(offset>capacity1)
this.resize((capacity1*=2)>offset?capacity1:offset);offset-=1;this.view[offset]=value;if(relative)this.offset+=1;return this;};ByteBufferPrototype.writeUInt8=ByteBufferPrototype.writeUint8;ByteBufferPrototype.readUint8=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+1>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+1+") <= "+this.buffer.byteLength);}
var value=this.view[offset];if(relative)this.offset+=1;return value;};ByteBufferPrototype.readUInt8=ByteBufferPrototype.readUint8;ByteBufferPrototype.writeInt16=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof value!=='number'||value%1!==0)
throw TypeError("Illegal value: "+value+" (not an integer)");value|=0;if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
offset+=2;var capacity2=this.buffer.byteLength;if(offset>capacity2)
this.resize((capacity2*=2)>offset?capacity2:offset);offset-=2;if(this.littleEndian){this.view[offset+1]=(value&0xFF00)>>>8;this.view[offset]=value&0x00FF;}else{this.view[offset]=(value&0xFF00)>>>8;this.view[offset+1]=value&0x00FF;}
if(relative)this.offset+=2;return this;};ByteBufferPrototype.writeShort=ByteBufferPrototype.writeInt16;ByteBufferPrototype.readInt16=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+2>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+2+") <= "+this.buffer.byteLength);}
var value=0;if(this.littleEndian){value=this.view[offset];value|=this.view[offset+1]<<8;}else{value=this.view[offset]<<8;value|=this.view[offset+1];}
if((value&0x8000)===0x8000)value=-(0xFFFF-value+1);if(relative)this.offset+=2;return value;};ByteBufferPrototype.readShort=ByteBufferPrototype.readInt16;ByteBufferPrototype.writeUint16=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof value!=='number'||value%1!==0)
throw TypeError("Illegal value: "+value+" (not an integer)");value>>>=0;if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
offset+=2;var capacity3=this.buffer.byteLength;if(offset>capacity3)
this.resize((capacity3*=2)>offset?capacity3:offset);offset-=2;if(this.littleEndian){this.view[offset+1]=(value&0xFF00)>>>8;this.view[offset]=value&0x00FF;}else{this.view[offset]=(value&0xFF00)>>>8;this.view[offset+1]=value&0x00FF;}
if(relative)this.offset+=2;return this;};ByteBufferPrototype.writeUInt16=ByteBufferPrototype.writeUint16;ByteBufferPrototype.readUint16=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+2>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+2+") <= "+this.buffer.byteLength);}
var value=0;if(this.littleEndian){value=this.view[offset];value|=this.view[offset+1]<<8;}else{value=this.view[offset]<<8;value|=this.view[offset+1];}
if(relative)this.offset+=2;return value;};ByteBufferPrototype.readUInt16=ByteBufferPrototype.readUint16;ByteBufferPrototype.writeInt32=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof value!=='number'||value%1!==0)
throw TypeError("Illegal value: "+value+" (not an integer)");value|=0;if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
offset+=4;var capacity4=this.buffer.byteLength;if(offset>capacity4)
this.resize((capacity4*=2)>offset?capacity4:offset);offset-=4;if(this.littleEndian){this.view[offset+3]=(value>>>24)&0xFF;this.view[offset+2]=(value>>>16)&0xFF;this.view[offset+1]=(value>>>8)&0xFF;this.view[offset]=value&0xFF;}else{this.view[offset]=(value>>>24)&0xFF;this.view[offset+1]=(value>>>16)&0xFF;this.view[offset+2]=(value>>>8)&0xFF;this.view[offset+3]=value&0xFF;}
if(relative)this.offset+=4;return this;};ByteBufferPrototype.writeInt=ByteBufferPrototype.writeInt32;ByteBufferPrototype.readInt32=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+4>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+4+") <= "+this.buffer.byteLength);}
var value=0;if(this.littleEndian){value=this.view[offset+2]<<16;value|=this.view[offset+1]<<8;value|=this.view[offset];value+=this.view[offset+3]<<24>>>0;}else{value=this.view[offset+1]<<16;value|=this.view[offset+2]<<8;value|=this.view[offset+3];value+=this.view[offset]<<24>>>0;}
value|=0;if(relative)this.offset+=4;return value;};ByteBufferPrototype.readInt=ByteBufferPrototype.readInt32;ByteBufferPrototype.writeUint32=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof value!=='number'||value%1!==0)
throw TypeError("Illegal value: "+value+" (not an integer)");value>>>=0;if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
offset+=4;var capacity5=this.buffer.byteLength;if(offset>capacity5)
this.resize((capacity5*=2)>offset?capacity5:offset);offset-=4;if(this.littleEndian){this.view[offset+3]=(value>>>24)&0xFF;this.view[offset+2]=(value>>>16)&0xFF;this.view[offset+1]=(value>>>8)&0xFF;this.view[offset]=value&0xFF;}else{this.view[offset]=(value>>>24)&0xFF;this.view[offset+1]=(value>>>16)&0xFF;this.view[offset+2]=(value>>>8)&0xFF;this.view[offset+3]=value&0xFF;}
if(relative)this.offset+=4;return this;};ByteBufferPrototype.writeUInt32=ByteBufferPrototype.writeUint32;ByteBufferPrototype.readUint32=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+4>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+4+") <= "+this.buffer.byteLength);}
var value=0;if(this.littleEndian){value=this.view[offset+2]<<16;value|=this.view[offset+1]<<8;value|=this.view[offset];value+=this.view[offset+3]<<24>>>0;}else{value=this.view[offset+1]<<16;value|=this.view[offset+2]<<8;value|=this.view[offset+3];value+=this.view[offset]<<24>>>0;}
if(relative)this.offset+=4;return value;};ByteBufferPrototype.readUInt32=ByteBufferPrototype.readUint32;if(Long){ByteBufferPrototype.writeInt64=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof value==='number')
value=Long.fromNumber(value);else if(typeof value==='string')
value=Long.fromString(value);else if(!(value&&value instanceof Long))
throw TypeError("Illegal value: "+value+" (not an integer or Long)");if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
if(typeof value==='number')
value=Long.fromNumber(value);else if(typeof value==='string')
value=Long.fromString(value);offset+=8;var capacity6=this.buffer.byteLength;if(offset>capacity6)
