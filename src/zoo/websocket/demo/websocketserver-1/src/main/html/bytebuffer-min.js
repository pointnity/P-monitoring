(function(global,factory){if(typeof define==='function'&&define["amd"])
define(["long"],factory);else if(typeof require==='function'&&typeof module==="object"&&module&&module["exports"])
module['exports']=(function(){var Long;try{Long=require("long");}catch(e){}
return factory(Long);})();else
