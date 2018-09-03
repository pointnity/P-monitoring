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
this.resize((capacity6*=2)>offset?capacity6:offset);offset-=8;var lo=value.low,hi=value.high;if(this.littleEndian){this.view[offset+3]=(lo>>>24)&0xFF;this.view[offset+2]=(lo>>>16)&0xFF;this.view[offset+1]=(lo>>>8)&0xFF;this.view[offset]=lo&0xFF;offset+=4;this.view[offset+3]=(hi>>>24)&0xFF;this.view[offset+2]=(hi>>>16)&0xFF;this.view[offset+1]=(hi>>>8)&0xFF;this.view[offset]=hi&0xFF;}else{this.view[offset]=(hi>>>24)&0xFF;this.view[offset+1]=(hi>>>16)&0xFF;this.view[offset+2]=(hi>>>8)&0xFF;this.view[offset+3]=hi&0xFF;offset+=4;this.view[offset]=(lo>>>24)&0xFF;this.view[offset+1]=(lo>>>16)&0xFF;this.view[offset+2]=(lo>>>8)&0xFF;this.view[offset+3]=lo&0xFF;}
if(relative)this.offset+=8;return this;};ByteBufferPrototype.writeLong=ByteBufferPrototype.writeInt64;ByteBufferPrototype.readInt64=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+8>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+8+") <= "+this.buffer.byteLength);}
var lo=0,hi=0;if(this.littleEndian){lo=this.view[offset+2]<<16;lo|=this.view[offset+1]<<8;lo|=this.view[offset];lo+=this.view[offset+3]<<24>>>0;offset+=4;hi=this.view[offset+2]<<16;hi|=this.view[offset+1]<<8;hi|=this.view[offset];hi+=this.view[offset+3]<<24>>>0;}else{hi=this.view[offset+1]<<16;hi|=this.view[offset+2]<<8;hi|=this.view[offset+3];hi+=this.view[offset]<<24>>>0;offset+=4;lo=this.view[offset+1]<<16;lo|=this.view[offset+2]<<8;lo|=this.view[offset+3];lo+=this.view[offset]<<24>>>0;}
var value=new Long(lo,hi,false);if(relative)this.offset+=8;return value;};ByteBufferPrototype.readLong=ByteBufferPrototype.readInt64;ByteBufferPrototype.writeUint64=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof value==='number')
value=Long.fromNumber(value);else if(typeof value==='string')
value=Long.fromString(value);else if(!(value&&value instanceof Long))
throw TypeError("Illegal value: "+value+" (not an integer or Long)");if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
if(typeof value==='number')
value=Long.fromNumber(value);else if(typeof value==='string')
value=Long.fromString(value);offset+=8;var capacity7=this.buffer.byteLength;if(offset>capacity7)
this.resize((capacity7*=2)>offset?capacity7:offset);offset-=8;var lo=value.low,hi=value.high;if(this.littleEndian){this.view[offset+3]=(lo>>>24)&0xFF;this.view[offset+2]=(lo>>>16)&0xFF;this.view[offset+1]=(lo>>>8)&0xFF;this.view[offset]=lo&0xFF;offset+=4;this.view[offset+3]=(hi>>>24)&0xFF;this.view[offset+2]=(hi>>>16)&0xFF;this.view[offset+1]=(hi>>>8)&0xFF;this.view[offset]=hi&0xFF;}else{this.view[offset]=(hi>>>24)&0xFF;this.view[offset+1]=(hi>>>16)&0xFF;this.view[offset+2]=(hi>>>8)&0xFF;this.view[offset+3]=hi&0xFF;offset+=4;this.view[offset]=(lo>>>24)&0xFF;this.view[offset+1]=(lo>>>16)&0xFF;this.view[offset+2]=(lo>>>8)&0xFF;this.view[offset+3]=lo&0xFF;}
if(relative)this.offset+=8;return this;};ByteBufferPrototype.writeUInt64=ByteBufferPrototype.writeUint64;ByteBufferPrototype.readUint64=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+8>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+8+") <= "+this.buffer.byteLength);}
var lo=0,hi=0;if(this.littleEndian){lo=this.view[offset+2]<<16;lo|=this.view[offset+1]<<8;lo|=this.view[offset];lo+=this.view[offset+3]<<24>>>0;offset+=4;hi=this.view[offset+2]<<16;hi|=this.view[offset+1]<<8;hi|=this.view[offset];hi+=this.view[offset+3]<<24>>>0;}else{hi=this.view[offset+1]<<16;hi|=this.view[offset+2]<<8;hi|=this.view[offset+3];hi+=this.view[offset]<<24>>>0;offset+=4;lo=this.view[offset+1]<<16;lo|=this.view[offset+2]<<8;lo|=this.view[offset+3];lo+=this.view[offset]<<24>>>0;}
var value=new Long(lo,hi,true);if(relative)this.offset+=8;return value;};ByteBufferPrototype.readUInt64=ByteBufferPrototype.readUint64;}
function ieee754_read(buffer,offset,isLE,mLen,nBytes){var e,m,eLen=nBytes*8-mLen-1,eMax=(1<<eLen)-1,eBias=eMax>>1,nBits=-7,i=isLE?(nBytes-1):0,d=isLE?-1:1,s=buffer[offset+i];i+=d;e=s&((1<<(-nBits))-1);s>>=(-nBits);nBits+=eLen;for(;nBits>0;e=e*256+buffer[offset+i],i+=d,nBits-=8){}
m=e&((1<<(-nBits))-1);e>>=(-nBits);nBits+=mLen;for(;nBits>0;m=m*256+buffer[offset+i],i+=d,nBits-=8){}
if(e===0){e=1-eBias;}else if(e===eMax){return m?NaN:((s?-1:1)*Infinity);}else{m=m+Math.pow(2,mLen);e=e-eBias;}
return(s?-1:1)*m*Math.pow(2,e-mLen);}
function ieee754_write(buffer,value,offset,isLE,mLen,nBytes){var e,m,c,eLen=nBytes*8-mLen-1,eMax=(1<<eLen)-1,eBias=eMax>>1,rt=(mLen===23?Math.pow(2,-24)-Math.pow(2,-77):0),i=isLE?0:(nBytes-1),d=isLE?1:-1,s=value<0||(value===0&&1/value<0)?1:0;value=Math.abs(value);if(isNaN(value)||value===Infinity){m=isNaN(value)?1:0;e=eMax;}else{e=Math.floor(Math.log(value)/Math.LN2);if(value*(c=Math.pow(2,-e))<1){e--;c*=2;}
if(e+eBias>=1){value+=rt/c;}else{value+=rt*Math.pow(2,1-eBias);}
if(value*c>=2){e++;c/=2;}
if(e+eBias>=eMax){m=0;e=eMax;}else if(e+eBias>=1){m=(value*c-1)*Math.pow(2,mLen);e=e+eBias;}else{m=value*Math.pow(2,eBias-1)*Math.pow(2,mLen);e=0;}}
for(;mLen>=8;buffer[offset+i]=m&0xff,i+=d,m/=256,mLen-=8){}
e=(e<<mLen)|m;eLen+=mLen;for(;eLen>0;buffer[offset+i]=e&0xff,i+=d,e/=256,eLen-=8){}
buffer[offset+i-d]|=s*128;}
ByteBufferPrototype.writeFloat32=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof value!=='number')
throw TypeError("Illegal value: "+value+" (not a number)");if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
offset+=4;var capacity8=this.buffer.byteLength;if(offset>capacity8)
this.resize((capacity8*=2)>offset?capacity8:offset);offset-=4;ieee754_write(this.view,value,offset,this.littleEndian,23,4);if(relative)this.offset+=4;return this;};ByteBufferPrototype.writeFloat=ByteBufferPrototype.writeFloat32;ByteBufferPrototype.readFloat32=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+4>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+4+") <= "+this.buffer.byteLength);}
var value=ieee754_read(this.view,offset,this.littleEndian,23,4);if(relative)this.offset+=4;return value;};ByteBufferPrototype.readFloat=ByteBufferPrototype.readFloat32;ByteBufferPrototype.writeFloat64=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof value!=='number')
throw TypeError("Illegal value: "+value+" (not a number)");if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
offset+=8;var capacity9=this.buffer.byteLength;if(offset>capacity9)
this.resize((capacity9*=2)>offset?capacity9:offset);offset-=8;ieee754_write(this.view,value,offset,this.littleEndian,52,8);if(relative)this.offset+=8;return this;};ByteBufferPrototype.writeDouble=ByteBufferPrototype.writeFloat64;ByteBufferPrototype.readFloat64=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+8>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+8+") <= "+this.buffer.byteLength);}
var value=ieee754_read(this.view,offset,this.littleEndian,52,8);if(relative)this.offset+=8;return value;};ByteBufferPrototype.readDouble=ByteBufferPrototype.readFloat64;ByteBuffer.MAX_VARINT32_BYTES=5;ByteBuffer.calculateVarint32=function(value){value=value>>>0;if(value<1<<7)return 1;else if(value<1<<14)return 2;else if(value<1<<21)return 3;else if(value<1<<28)return 4;else return 5;};ByteBuffer.zigZagEncode32=function(n){return(((n|=0)<<1)^(n>>31))>>>0;};ByteBuffer.zigZagDecode32=function(n){return((n>>>1)^-(n&1))|0;};ByteBufferPrototype.writeVarint32=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof value!=='number'||value%1!==0)
throw TypeError("Illegal value: "+value+" (not an integer)");value|=0;if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
var size=ByteBuffer.calculateVarint32(value),b;offset+=size;var capacity10=this.buffer.byteLength;if(offset>capacity10)
this.resize((capacity10*=2)>offset?capacity10:offset);offset-=size;value>>>=0;while(value>=0x80){b=(value&0x7f)|0x80;this.view[offset++]=b;value>>>=7;}
this.view[offset++]=value;if(relative){this.offset=offset;return this;}
return size;};ByteBufferPrototype.writeVarint32ZigZag=function(value,offset){return this.writeVarint32(ByteBuffer.zigZagEncode32(value),offset);};ByteBufferPrototype.readVarint32=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+1>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+1+") <= "+this.buffer.byteLength);}
var c=0,value=0>>>0,b;do{if(!this.noAssert&&offset>this.limit){var err=Error("Truncated");err['truncated']=true;throw err;}
b=this.view[offset++];if(c<5)
value|=(b&0x7f)<<(7*c);++c;}while((b&0x80)!==0);value|=0;if(relative){this.offset=offset;return value;}
return{"value":value,"length":c};};ByteBufferPrototype.readVarint32ZigZag=function(offset){var val=this.readVarint32(offset);if(typeof val==='object')
val["value"]=ByteBuffer.zigZagDecode32(val["value"]);else
val=ByteBuffer.zigZagDecode32(val);return val;};if(Long){ByteBuffer.MAX_VARINT64_BYTES=10;ByteBuffer.calculateVarint64=function(value){if(typeof value==='number')
value=Long.fromNumber(value);else if(typeof value==='string')
value=Long.fromString(value);var part0=value.toInt()>>>0,part1=value.shiftRightUnsigned(28).toInt()>>>0,part2=value.shiftRightUnsigned(56).toInt()>>>0;if(part2==0){if(part1==0){if(part0<1<<14)
return part0<1<<7?1:2;else
return part0<1<<21?3:4;}else{if(part1<1<<14)
return part1<1<<7?5:6;else
return part1<1<<21?7:8;}}else
return part2<1<<7?9:10;};ByteBuffer.zigZagEncode64=function(value){if(typeof value==='number')
value=Long.fromNumber(value,false);else if(typeof value==='string')
value=Long.fromString(value,false);else if(value.unsigned!==false)value=value.toSigned();return value.shiftLeft(1).xor(value.shiftRight(63)).toUnsigned();};ByteBuffer.zigZagDecode64=function(value){if(typeof value==='number')
value=Long.fromNumber(value,false);else if(typeof value==='string')
value=Long.fromString(value,false);else if(value.unsigned!==false)value=value.toSigned();return value.shiftRightUnsigned(1).xor(value.and(Long.ONE).toSigned().negate()).toSigned();};ByteBufferPrototype.writeVarint64=function(value,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof value==='number')
value=Long.fromNumber(value);else if(typeof value==='string')
value=Long.fromString(value);else if(!(value&&value instanceof Long))
throw TypeError("Illegal value: "+value+" (not an integer or Long)");if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
if(typeof value==='number')
value=Long.fromNumber(value,false);else if(typeof value==='string')
value=Long.fromString(value,false);else if(value.unsigned!==false)value=value.toSigned();var size=ByteBuffer.calculateVarint64(value),part0=value.toInt()>>>0,part1=value.shiftRightUnsigned(28).toInt()>>>0,part2=value.shiftRightUnsigned(56).toInt()>>>0;offset+=size;var capacity11=this.buffer.byteLength;if(offset>capacity11)
this.resize((capacity11*=2)>offset?capacity11:offset);offset-=size;switch(size){case 10:this.view[offset+9]=(part2>>>7)&0x01;case 9:this.view[offset+8]=size!==9?(part2)|0x80:(part2)&0x7F;case 8:this.view[offset+7]=size!==8?(part1>>>21)|0x80:(part1>>>21)&0x7F;case 7:this.view[offset+6]=size!==7?(part1>>>14)|0x80:(part1>>>14)&0x7F;case 6:this.view[offset+5]=size!==6?(part1>>>7)|0x80:(part1>>>7)&0x7F;case 5:this.view[offset+4]=size!==5?(part1)|0x80:(part1)&0x7F;case 4:this.view[offset+3]=size!==4?(part0>>>21)|0x80:(part0>>>21)&0x7F;case 3:this.view[offset+2]=size!==3?(part0>>>14)|0x80:(part0>>>14)&0x7F;case 2:this.view[offset+1]=size!==2?(part0>>>7)|0x80:(part0>>>7)&0x7F;case 1:this.view[offset]=size!==1?(part0)|0x80:(part0)&0x7F;}
if(relative){this.offset+=size;return this;}else{return size;}};ByteBufferPrototype.writeVarint64ZigZag=function(value,offset){return this.writeVarint64(ByteBuffer.zigZagEncode64(value),offset);};ByteBufferPrototype.readVarint64=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+1>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+1+") <= "+this.buffer.byteLength);}
var start=offset,part0=0,part1=0,part2=0,b=0;b=this.view[offset++];part0=(b&0x7F);if(b&0x80){b=this.view[offset++];part0|=(b&0x7F)<<7;if((b&0x80)||(this.noAssert&&typeof b==='undefined')){b=this.view[offset++];part0|=(b&0x7F)<<14;if((b&0x80)||(this.noAssert&&typeof b==='undefined')){b=this.view[offset++];part0|=(b&0x7F)<<21;if((b&0x80)||(this.noAssert&&typeof b==='undefined')){b=this.view[offset++];part1=(b&0x7F);if((b&0x80)||(this.noAssert&&typeof b==='undefined')){b=this.view[offset++];part1|=(b&0x7F)<<7;if((b&0x80)||(this.noAssert&&typeof b==='undefined')){b=this.view[offset++];part1|=(b&0x7F)<<14;if((b&0x80)||(this.noAssert&&typeof b==='undefined')){b=this.view[offset++];part1|=(b&0x7F)<<21;if((b&0x80)||(this.noAssert&&typeof b==='undefined')){b=this.view[offset++];part2=(b&0x7F);if((b&0x80)||(this.noAssert&&typeof b==='undefined')){b=this.view[offset++];part2|=(b&0x7F)<<7;if((b&0x80)||(this.noAssert&&typeof b==='undefined')){throw Error("Buffer overrun");}}}}}}}}}}
var value=Long.fromBits(part0|(part1<<28),(part1>>>4)|(part2)<<24,false);if(relative){this.offset=offset;return value;}else{return{'value':value,'length':offset-start};}};ByteBufferPrototype.readVarint64ZigZag=function(offset){var val=this.readVarint64(offset);if(val&&val['value']instanceof Long)
val["value"]=ByteBuffer.zigZagDecode64(val["value"]);else
val=ByteBuffer.zigZagDecode64(val);return val;};}
ByteBufferPrototype.writeCString=function(str,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;var i,k=str.length;if(!this.noAssert){if(typeof str!=='string')
throw TypeError("Illegal str: Not a string");for(i=0;i<k;++i){if(str.charCodeAt(i)===0)
throw RangeError("Illegal str: Contains NULL-characters");}
if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+0>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+0+") <= "+this.buffer.byteLength);}
k=utfx.calculateUTF16asUTF8(stringSource(str))[1];offset+=k+1;var capacity12=this.buffer.byteLength;if(offset>capacity12)
this.resize((capacity12*=2)>offset?capacity12:offset);offset-=k+1;utfx.encodeUTF16toUTF8(stringSource(str),function(b){this.view[offset++]=b;}.bind(this));this.view[offset++]=0;if(relative){this.offset=offset;return this;}
return k;};ByteBufferPrototype.readCString=function(offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof offset!=='number'||offset%1!==0)
throw TypeError("Illegal offset: "+offset+" (not an integer)");offset>>>=0;if(offset<0||offset+1>this.buffer.byteLength)
throw RangeError("Illegal offset: 0 <= "+offset+" (+"+1+") <= "+this.buffer.byteLength);}
var start=offset,temp;var sd,b=-1;utfx.decodeUTF8toUTF16(function(){if(b===0)return null;if(offset>=this.limit)
throw RangeError("Illegal range: Truncated data, "+offset+" < "+this.limit);b=this.view[offset++];return b===0?null:b;}.bind(this),sd=stringDestination(),true);if(relative){this.offset=offset;return sd();}else{return{"string":sd(),"length":offset-start};}};ByteBufferPrototype.writeIString=function(str,offset){var relative=typeof offset==='undefined';if(relative)offset=this.offset;if(!this.noAssert){if(typeof str!=='string')
throw TypeError("Illegal str: Not a string");if(typeof offset!=='number'||offset%1!==0)
