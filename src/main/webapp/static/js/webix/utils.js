/**
 * 유틸리티
 */

// 동등성 비교
var equals=function (a,b){
	if(a != null && b != null){
		a = a.toString().toLowerCase();
		return a.indexOf(b) !== -1;
	} else {
		return false;
	}
};