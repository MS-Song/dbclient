/**
 * 브라우저 버전 식별
 * 
 */
function isBrowserCheck(){ 
	var agt = navigator.userAgent.toLowerCase(); 
	if (agt.indexOf("chrome") != -1) return 'Chrome'; 
	if (agt.indexOf("opera") != -1) return 'Opera'; 
	if (agt.indexOf("staroffice") != -1) return 'Star Office'; 
	if (agt.indexOf("webtv") != -1) return 'WebTV'; 
	if (agt.indexOf("beonex") != -1) return 'Beonex'; 
	if (agt.indexOf("chimera") != -1) return 'Chimera'; 
	if (agt.indexOf("netpositive") != -1) return 'NetPositive';
	if (agt.indexOf("phoenix") != -1) return 'Phoenix'; 
	if (agt.indexOf("firefox") != -1) return 'Firefox'; 
	if (agt.indexOf("safari") != -1) return 'Safari'; 
	if (agt.indexOf("skipstone") != -1) return 'SkipStone'; 
	if (agt.indexOf("netscape") != -1) return 'Netscape'; 
	if (agt.indexOf("mozilla/5.0") != -1) return 'Mozilla'; 
	if (agt.indexOf("msie") != -1) { // 익스플로러
		var rv = -1; 
		if (navigator.appName == 'Microsoft Internet Explorer') { 
			var ua = navigator.userAgent; 
			var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})"); 
			if (re.exec(ua) != null) rv = parseFloat(RegExp.$1); 
		} 
		return 'Internet Explorer '+rv; 
	} 
}

if(isBrowserCheck() != 'Chrome'){
	document.writeln('크롬 브라우저에서만 작동 됩니다.');
	document.writeln('아래 링크를 통해서 다운로드 하세요');
	document.writeln('<br/> <a href="https://www.google.co.kr/chrome/browser/desktop/index.html">링크</a>');
}