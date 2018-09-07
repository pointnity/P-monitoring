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
