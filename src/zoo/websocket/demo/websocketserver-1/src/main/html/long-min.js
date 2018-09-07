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
