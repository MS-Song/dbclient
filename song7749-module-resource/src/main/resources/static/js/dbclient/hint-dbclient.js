var dbclientHint = {
    view: "hint",
    id: "dbclient-hint",
	prevButton: "Prev Step",
	on: {
		onEnd: function() {
			this.config.nextButton = "Next";
			this.resume();
		},
		onNext: function(stepNumber) {
			if(stepNumber === this.config.steps.length - 1) {
				this.config.nextButton = "Restart";
			} else {
				this.config.nextButton = "Next";
			}
		},
		onPrevious: function(stepNumber) {
			if(stepNumber === this.config.steps.length) {
				this.config.nextButton = "Restart";
			} else {
				this.config.nextButton = "Next";
			}
		},
		onSkip : function(stepNumber) {
			webix.message({ type:"error", text:"TIP 을 OFF 했습니다. 다시 보고 싶으시면 환경 설정에서 켜주세요"});
			webix.storage.local.put("dbclient_hint_on","false");
		}
	},
    steps: [
        {
            el: "menu_left_icon",
            title: "DBClint를 사용해주셔서 감사합니다.",
            text: "Database IDE Tool 로 Database 관리 및 개발 관련 자동완성 기능을 지원 합니다.",
            event:"click"
        },
        {}
    ]
};
webix.ready(function(){
	// hint 추가
	//webix.storage.local.put("dbclient_hint_on","true");
//	let dbclient_hint_on = webix.storage.local.get("dbclient_hint_on");
//	if(dbclient_hint_on!="false"){
//		setTimeout(() => {webix.ui(dbclientHint).start()}, 1000);		
//	}
});