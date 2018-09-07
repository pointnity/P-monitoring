(function(global,factory){if(typeof define==='function'&&define["amd"])
define([],factory);else if(typeof require==='function'&&typeof module==="object"&&module&&module["exports"])
module["exports"]=factory();else
(global["dcodeIO"]=global["dcodeIO"]||{})["Long"]=factory();})(this,function(){"use strict";function Long(low,high,unsigned){this.low=low|0;this.high=high|0;this.unsigned=!!unsigned;}
Long.prototype.__isLong__;Object.defineProperty(Long.prototype,"__isLong__",{value:true,enumerable:false,configurable:false});function isLong(obj){return(obj&&obj["__isLong__"])===true;}
Long.isLong=isLong;var INT_CACHE={};var UINT_CACHE={};function fromInt(value,unsigned){var obj,cachedObj,cache;if(unsigned){value>>>=0;if(cache=(0<=value&&value<256)){cachedObj=UINT_CACHE[value];if(cachedObj)
return cachedObj;}
obj=fromBits(value,(value|0)<0?-1:0,true);if(cache)
UINT_CACHE[value]=obj;return obj;}else{value|=0;if(cache=(-128<=value&&value<128)){cachedObj=INT_CACHE[value];if(cachedObj)
return cachedObj;}
obj=fromBits(value,value<0?-1:0,false);if(cache)
INT_CACHE[value]=obj;return obj;}}
Long.fromInt=fromInt;function fromNumber(value,unsigned){if(isNaN(value)||!isFinite(value))
return unsigned?UZERO:ZERO;if(unsigned){if(value<0)
return UZERO;if(value>=TWO_PWR_64_DBL)
return MAX_UNSIGNED_VALUE;}else{if(value<=-TWO_PWR_63_DBL)
return MIN_VALUE;if(value+1>=TWO_PWR_63_DBL)
return MAX_VALUE;}
if(value<0)
return fromNumber(-value,unsigned).neg();return fromBits((value%TWO_PWR_32_DBL)|0,(value/TWO_PWR_32_DBL)|0,unsigned);}
Long.fromNumber=fromNumber;function fromBits(lowBits,highBits,unsigned){return new Long(lowBits,highBits,unsigned);}
Long.fromBits=fromBits;var pow_dbl=Math.pow;function fromString(str,unsigned,radix){if(str.length===0)
throw Error('empty string');if(str==="NaN"||str==="Infinity"||str==="+Infinity"||str==="-Infinity")
return ZERO;if(typeof unsigned==='number'){radix=unsigned,unsigned=false;}else{unsigned=!!unsigned;}
radix=radix||10;if(radix<2||36<radix)
throw RangeError('radix');var p;if((p=str.indexOf('-'))>0)
throw Error('interior hyphen');else if(p===0){return fromString(str.substring(1),unsigned,radix).neg();}
var radixToPower=fromNumber(pow_dbl(radix,8));var result=ZERO;for(var i=0;i<str.length;i+=8){var size=Math.min(8,str.length-i),value=parseInt(str.substring(i,i+size),radix);if(size<8){var power=fromNumber(pow_dbl(radix,size));result=result.mul(power).add(fromNumber(value));}else{result=result.mul(radixToPower);result=result.add(fromNumber(value));}}
result.unsigned=unsigned;return result;}
Long.fromString=fromString;function fromValue(val){if(val instanceof Long)
return val;if(typeof val==='number')
return fromNumber(val);if(typeof val==='string')
return fromString(val);return fromBits(val.low,val.high,val.unsigned);}
Long.fromValue=fromValue;var TWO_PWR_16_DBL=1<<16;var TWO_PWR_24_DBL=1<<24;var TWO_PWR_32_DBL=TWO_PWR_16_DBL*TWO_PWR_16_DBL;var TWO_PWR_64_DBL=TWO_PWR_32_DBL*TWO_PWR_32_DBL;var TWO_PWR_63_DBL=TWO_PWR_64_DBL/2;var TWO_PWR_24=fromInt(TWO_PWR_24_DBL);var ZERO=fromInt(0);Long.ZERO=ZERO;var UZERO=fromInt(0,true);Long.UZERO=UZERO;var ONE=fromInt(1);Long.ONE=ONE;var UONE=fromInt(1,true);Long.UONE=UONE;var NEG_ONE=fromInt(-1);Long.NEG_ONE=NEG_ONE;var MAX_VALUE=fromBits(0xFFFFFFFF|0,0x7FFFFFFF|0,false);Long.MAX_VALUE=MAX_VALUE;var MAX_UNSIGNED_VALUE=fromBits(0xFFFFFFFF|0,0xFFFFFFFF|0,true);Long.MAX_UNSIGNED_VALUE=MAX_UNSIGNED_VALUE;var MIN_VALUE=fromBits(0,0x80000000|0,false);Long.MIN_VALUE=MIN_VALUE;var LongPrototype=Long.prototype;LongPrototype.toInt=function toInt(){return this.unsigned?this.low>>>0:this.low;};LongPrototype.toNumber=function toNumber(){if(this.unsigned)
return((this.high>>>0)*TWO_PWR_32_DBL)+(this.low>>>0);return this.high*TWO_PWR_32_DBL+(this.low>>>0);};LongPrototype.toString=function toString(radix){radix=radix||10;if(radix<2||36<radix)
throw RangeError('radix');if(this.isZero())
return'0';if(this.isNegative()){if(this.eq(MIN_VALUE)){var radixLong=fromNumber(radix),div=this.div(radixLong),rem1=div.mul(radixLong).sub(this);return div.toString(radix)+rem1.toInt().toString(radix);}else
return'-'+this.neg().toString(radix);}
var radixToPower=fromNumber(pow_dbl(radix,6),this.unsigned),rem=this;var result='';while(true){var remDiv=rem.div(radixToPower),intval=rem.sub(remDiv.mul(radixToPower)).toInt()>>>0,digits=intval.toString(radix);rem=remDiv;if(rem.isZero())
return digits+result;else{while(digits.length<6)
digits='0'+digits;result=''+digits+result;}}};LongPrototype.getHighBits=function getHighBits(){return this.high;};LongPrototype.getHighBitsUnsigned=function getHighBitsUnsigned(){return this.high>>>0;};LongPrototype.getLowBits=function getLowBits(){return this.low;};LongPrototype.getLowBitsUnsigned=function getLowBitsUnsigned(){return this.low>>>0;};LongPrototype.getNumBitsAbs=function getNumBitsAbs(){if(this.isNegative())
return this.eq(MIN_VALUE)?64:this.neg().getNumBitsAbs();var val=this.high!=0?this.high:this.low;for(var bit=31;bit>0;bit--)
if((val&(1<<bit))!=0)
