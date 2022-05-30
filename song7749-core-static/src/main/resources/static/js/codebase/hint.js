/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "/codebase/";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 2);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
exports.locale = undefined;

__webpack_require__(5);

__webpack_require__(7);

__webpack_require__(3);

__webpack_require__(4);

__webpack_require__(6);

__webpack_require__(8);

__webpack_require__(9);

__webpack_require__(10);

__webpack_require__(11);

__webpack_require__(12);

var locale = exports.locale = {
	hint: {
		next: "Next",
		prev: "Previous",
		last: "End Tour"
	}
};

/***/ }),
/* 1 */
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),
/* 2 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


__webpack_require__(1);

var _locales = __webpack_require__(0);

webix.i18n.hint = webix.extend(webix.i18n, _locales.locale).hint;

webix.protoUI({
	name: "hint",
	defaults: {
		steps: [],
		borderless: true,
		nextButton: true,
		prevButton: true,
		top: false,
		left: false,
		stepTimeout: 500
	},
	$init: function $init() {
		var _this = this;

		this.$view.className += " webix_hint_view";
		this._i = -1;
		this.attachEvent("onDestruct", function () {
			_this._setBodyClass("remove");
			if (_this._eventObj) {
				webix.eventRemove(_this._eventObj);
			}
			if (_this._eventObjEsc) {
				webix.eventRemove(_this._eventObjEsc);
			}
			if (_this._eventResize) {
				webix.detachEvent(_this._eventResize);
			}
		});
		this._eventObjEsc = webix.event(document.body, "keydown", function (e) {
			// escape
			if (e.keyCode == 27) {
				_this._skip();
			}
		});
		this._setResize();
	},
	steps_setter: function steps_setter(config) {
		var newConfig = [];
		for (var i = 0; i < config.length; i++) {
			config[i].padding = config[i].padding || 0;
			config[i].text = config[i].text || "";
			newConfig.push(config[i]);
		}
		return newConfig;
	},
	_drawOver: function _drawOver(stepEl) {
		this.$view.innerHTML += "<svg preserveAspectRatio=\"none\" width=\"100%\" height=\"100%\" class=\"webix_hint_overlay\" preserveAspectRatio=\"none\">\n\t\t\t<defs>\n\t\t\t\t<mask id=\"hole\">\n\t\t\t\t\t<rect class=\"webix_hint_overlay_hole\" width=\"100%\" height=\"100%\" fill=\"white\"/>\n\t\t\t\t\t<rect class=\"webix_hint_overlay_hole webix_hint_overlay_hole_el\" x=\"0\" y=\"0\" width=\"0\" height=\"0\" fill=\"white\"/>\n\t\t\t\t</mask>\n\t\t\t</defs>\n\t\t\t<rect class=\"webix_hint_overlay_hole\" width=\"100%\" height=\"100%\" mask=\"url(#hole)\" />\n\t\t</svg>";
		this._setProperties(stepEl);
		this.callEvent("onAfterStart", []);
	},
	_drawHint: function _drawHint() {
		var settings = this.config;
		this.$view.innerHTML += "<div class=\"webix_hint\">\n\t\t\t<div class='webix_hint_title'>" + (this._step.title ? this._step.title : "") + "</div>\n\t\t\t<div class=\"webix_hint_label\">" + this._step.text + "</div>\n\t\t\t<div class=\"webix_hint_progress\">\n\t\t\t\t" + (this._i + 1) + "/" + this.config.steps.length + "\n\t\t\t</div>\n\t\t\t<div class=\"webix_hint_buttons\">\n\t\t\t\t" + (settings.prevButton !== false ? "<button class=\"webix_hint_button webix_hint_button_prev webix_hint_button_hidden\">" + (typeof settings.prevButton == "string" ? settings.prevButton : "" + webix.i18n.hint.prev) + "</button>" : "") + "\n\t\t\t\t" + (settings.nextButton !== false ? "<button class=\"webix_hint_button webix_hint_button_next\">" + (typeof settings.nextButton == "string" ? settings.nextButton : "" + webix.i18n.hint.next) + "</button>" : "") + "\n\t\t\t</div>\n\t\t\t<button class=\"webix_hint_button_close\" title=\"Close\">&#10005;</button>\n\t\t</div>";
	},
	_setProperties: function _setProperties(stepEl, refresh) {
		var _this2 = this;

		if (!stepEl) {
			return;
		}

		if (!webix.env.mobile) {
			stepEl.scrollIntoView(false);
		}
		this._step = this.config.steps[this._i];
		this._reDraw(stepEl, refresh);
		this._hint = this.$view.querySelector(".webix_hint");

		var padding = 30;
		var docElem = document.documentElement;
		var box = stepEl.getBoundingClientRect();
		var elLeft = box.left + this._step.padding;
		var highlightWidth = box.width;
		var highlightHeight = box.height;
		var hintLeft = elLeft - this._step.padding;
		var hintWidth = this._hint.offsetWidth;
		var hintHeight = this._hint.offsetHeight;
		var elTop = webix.env.mobile ? box.top + this._step.padding : box.top + this._step.padding + window.pageYOffset;
		var hintTop = elTop + highlightHeight + this._step.padding + padding;
		var windowWidth = window.innerWidth && docElem.clientWidth ? Math.min(window.innerWidth, docElem.clientWidth) : window.innerWidth || docElem.clientWidth || document.getElementsByTagName("body")[0].clientWidth;
		var windowHeight = window.innerHeight && docElem.clientHeight ? Math.min(window.innerHeight, docElem.clientHeight) : window.innerHeight || docElem.clientHeight || document.getElementsByTagName("body")[0].clientHeight;

		stepEl.style.pointerEvents = "all";
		stepEl.style.userSelect = "initial";

		// set hint position
		if (elLeft - windowWidth > 0) {
			elLeft = elLeft - windowWidth + hintWidth + highlightWidth;
		}

		if (windowHeight / 2 < elTop) {
			// bottom
			hintTop = elTop - hintHeight - padding - this._step.padding * 2;
		} else if (windowWidth / 2 < elLeft && elLeft + hintWidth < windowWidth && highlightWidth + hintWidth < windowWidth) {
			// right
			hintTop = highlightHeight / 2 + elTop - this._step.padding;
			hintLeft = elLeft - hintWidth - this._step.padding - padding;
		} else if (windowWidth / 2 > elLeft && elLeft + hintWidth + highlightWidth < windowWidth) {
			// left
			hintLeft = highlightWidth + elLeft + padding;
			hintTop = elTop - this._step.padding;
		} else if (hintTop > windowHeight && hintHeight + highlightHeight < windowHeight) {
			//top, but hint does not fit
			hintTop = elTop - hintHeight - padding - this._step.padding * 2;
		} else if (hintTop > windowHeight || hintTop + hintHeight > windowHeight) {
			hintLeft = elLeft - hintWidth - this._step.padding * 2 - padding;
			hintTop = elTop - this._step.padding;
		}

		if (hintLeft + hintWidth > windowWidth) {
			// for overflow
			hintLeft = windowWidth - hintWidth;
		} else if (hintTop < 0 || hintTop > windowHeight) {
			hintTop = padding;
		} else if (windowWidth < highlightWidth || hintLeft < 0) {
			hintLeft = padding;
		}
		hintTop = this._setPos("top") ? this._setPos("top") : hintTop;
		hintLeft = this._setPos("left") ? this._setPos("left") : hintLeft;

		if (webix.env.mobile) {
			stepEl.scrollIntoView(false);
		}
		if (this._timer) {
			clearTimeout(this._timer);
		}
		this._timer = setTimeout(function () {
			_this2._hint.style.cssText = "top:" + hintTop + "px; left:" + hintLeft + "px;";
			_this2._setAttributes(_this2.$view.getElementsByClassName("webix_hint_overlay_hole_el")[0], { "x": elLeft - _this2._step.padding * 2, "y": elTop - _this2._step.padding * 2, "width": highlightWidth + _this2._step.padding * 2, "height": highlightHeight + _this2._step.padding * 2 });
			webix.html.addCss(_this2.getNode(), "webix_hint_animated");
		}, this.config.stepTimeout);
	},
	_setPos: function _setPos(name) {
		if (this._isInteger(this._step[name])) {
			return this._step[name];
		} else if (this._isInteger(this.config[name]) && this._step[name] !== false) {
			return this.config[name];
		}
	},
	_setResize: function _setResize() {
		var _this3 = this;

		this._eventResize = webix.attachEvent("onResize", function () {
			if (_this3.getCurrentStep() && _this3._i !== _this3.config.steps.length) {
				_this3._refresh(_this3.getCurrentStep(), false, true);
			}
		});
	},
	_isInteger: function _isInteger(value) {
		if (Number.isInteger) return Number.isInteger(value);
		return typeof value === "number" && isFinite(value) && Math.floor(value) === value;
	},
	_setAttributes: function _setAttributes(el, attrs) {
		for (var key in attrs) {
			el.setAttribute(key, attrs[key]);
		}
	},
	_reDraw: function _reDraw(stepEl, refresh) {
		var title = this.$view.querySelector(".webix_hint_title");
		var el = void 0;

		this._step.eventEl ? el = this._getEl(this._step.eventEl) : el = stepEl;
		if (this._i > 0 && !refresh) {
			webix.html.removeCss(this.getNode(), "webix_hint_animated");
			title.innerHTML = this._step.title || "";
			this.$view.querySelector(".webix_hint_label").innerHTML = this._step.text || "";
			this.$view.querySelector(".webix_hint_progress").innerHTML = this._i + 1 + "/" + this.config.steps.length;
		} else {
			this._drawHint();
			this._setEventsButtons(el);
		}
		if (!this._step.title && title) {
			title.style.margin = "0";
		}
		this._setElEvents(el);

		if (this._prevButton) {
			if (this._i > 0) {
				// previous button show
				webix.html.removeCss(this._prevButton, "webix_hint_button_hidden");
			} else if (this._prevButton && !this._prevButton.classList.contains("webix_hint_button_hidden")) {
				webix.html.addCss(this._prevButton, "webix_hint_button_hidden");
			}
		}

		if (this._i === this.config.steps.length - 1 && this._nextButton) {
			// next button text
			this._nextButton.innerHTML = "" + (typeof this.config.nextButton == "string" ? this.config.nextButton : "" + webix.i18n.hint.last);
		}
	},
	_setBodyClass: function _setBodyClass(remove) {
		var body = document.body;
		if (remove) {
			webix.html.removeCss(body, "webix_hint_overflow");
		} else if (!body.classList.contains("webix_hint_overflow")) {
			webix.html.addCss(body, "webix_hint_overflow");
		}
	},
	_getEl: function _getEl(el) {
		if ($$(el)) {
			return $$(el).getNode();
		} else {
			return document.querySelector(el);
		}
	},
	_drawSteps: function _drawSteps(refresh) {
		var _this4 = this;

		if (this.config.steps[this._i]) {
			var el = this._getEl(this.config.steps[this._i].el);
			if (this._i === 0 && !refresh) {
				this.callEvent("onBeforeStart", []);
				setTimeout(function () {
					// for first init
					_this4._drawOver(el);
				}, 100);
			} else {
				this._setProperties(el, refresh);
			}
		} else {
			this._skip();
		}
	},
	_setEventsButtons: function _setEventsButtons() {
		var _this5 = this;

		this._prevButton = this.$view.querySelectorAll(".webix_hint_button_prev")[0];
		this._nextButton = this.$view.querySelectorAll(".webix_hint_button_next")[0];
		var el = void 0;
		if (this._nextButton) {
			webix.event(this._nextButton, "click", function () {
				_this5._next(el, "next");
			});
		}
		if (this._prevButton) {
			webix.event(this._prevButton, "click", function () {
				_this5._next(el, "previous");
			});
		}
		webix.event(this.$view.querySelector(".webix_hint_button_close"), "click", function () {
			_this5._skip();
		});
	},
	_setElEvents: function _setElEvents(stepEl) {
		var _this6 = this;

		var eventStep = this._step.event;
		stepEl.focus();
		if (eventStep) {
			if (eventStep === "enter") {
				eventStep = "keydown";
			}
			if (this._eventObj) {
				webix.eventRemove(this._eventObj);
			}
			this._eventObj = webix.event(stepEl, eventStep, function (e) {
				if (eventStep == e.type) {
					if (e.type === "keydown" && e.keyCode !== 13) return;
					stepEl.focus();
					_this6._next(stepEl);
				}
			});
		} else {
			return;
		}
	},
	_next: function _next(stepEl, action) {
		var _this7 = this;

		action = action || "next";
		if (this._step.next && action === "next" || this._step.previous && action === "previous") {
			var promise = this._step[action]();
			if (promise) {
				promise.resolve().then(function () {
					_this7._nextStep(stepEl, action);
				});
			} else {
				this._nextStep(stepEl, action);
			}
		} else {
			this._nextStep(stepEl, action);
		}
	},
	_nextStep: function _nextStep(stepEl, action) {
		var el = this._getEl(this._step.el);
		el.style.pointerEvents = "";
		el.style.userSelect = "";
		el.blur();
		if (action !== "previous") {
			this._i++;
			this._drawSteps();
			this.callEvent("onNext", [this._i + 1]);
		}
		if (action === "previous") {
			this.callEvent("onPrevious", [this._i]);
			this._refresh(this._i--, false);
		}
	},
	_skip: function _skip() {
		if (this._i === -1) return;
		if (this._eventObj) {
			webix.eventRemove(this._eventObj);
			delete this._eventObj;
		}
		if (this._eventResize) {
			webix.detachEvent(this._eventResize);
			delete this._eventResize;
		}
		this.callEvent("onSkip", [this._i + 1]);
		this.hide();
		this._setBodyClass("remove");
		if (this._i === this.config.steps.length) {
			this.callEvent("onEnd", [this._i + 1]);
		}
	},
	_refresh: function _refresh(i, firstDraw) {
		if (!this._eventResize) {
			this._setResize();
		}
		this._i = i - 1;
		this._setBodyClass();
		if (this._hint) {
			if (this._hint.parentNode) this._hint.parentNode.removeChild(this._hint);
			webix.html.removeCss(this.getNode(), "webix_hint_animated");
		}
		this.show();
		if (firstDraw) {
			var svg = this.$view.querySelector("svg");
			if (svg) svg.parentNode.removeChild(svg);
			this._drawSteps();
		} else {
			this._drawSteps("refresh");
		}
	},
	start: function start() {
		this._refresh(1, true);
	},
	end: function end() {
		this._skip();
	},
	getCurrentStep: function getCurrentStep() {
		return this._i + 1;
	},
	resume: function resume(stepNumber) {
		if (this._hint) {
			stepNumber = stepNumber || 1;
			this._refresh(stepNumber);
		}
	},
	getSteps: function getSteps() {
		return this.config.steps;
	},
	setSteps: function setSteps(value) {
		this.define("steps", value);
	}
}, webix.ui.view, webix.EventSystem);

/***/ }),
/* 3 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
var be = exports.be = webix.i18n.locales["be-BY"] = {
	hint: {
		next: "Наступны",
		prev: "Папярэдні",
		last: "Канец Тура"
	}
};

/***/ }),
/* 4 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
/*German (Germany) locale*/
var de = exports.de = webix.i18n.locales["de-DE"] = {
	hint: {
		next: "Nächster",
		prev: "Bisherige",
		last: "Ende Tour"
	}
};

/***/ }),
/* 5 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
var en = exports.en = webix.i18n.locales["en-US"] = {
	hint: {
		next: "Next",
		prev: "Previous",
		last: "End Tour"
	}
};

/***/ }),
/* 6 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
/*Spanish (Spain, International Sort) locale*/
var es = exports.es = webix.i18n.locales["es-ES"] = {
	hint: {
		next: "Siguiente",
		prev: "Anterior",
		last: "Fin de Viaje"
	}
};

/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
var fr = exports.fr = webix.i18n.locales["fr-FR"] = {
	hint: {
		next: "Prochain",
		prev: "Précédent",
		last: "End Tour"
	}
};

/***/ }),
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
/*Italian (Italy) locale*/
var it = exports.it = webix.i18n.locales["it-IT"] = {
	hint: {
		next: "Seguente",
		prev: "Precedente",
		last: "End Tour"
	}
};

/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
var ja = exports.ja = webix.i18n.locales["ja-JP"] = {
	hint: {
		next: "次",
		prev: "前",
		last: "終了ツアー"
	}
};

/***/ }),
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
var pt = exports.pt = webix.i18n.locales["pt-BR"] = {
	hint: {
		next: "Próximo",
		prev: "Anterior",
		last: "End Tour"
	}
};

/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
var ru = exports.ru = webix.i18n.locales["ru-RU"] = {
	hint: {
		next: "Следующий",
		prev: "Предыдущий",
		last: "Конец Тура"
	}
};

/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
	value: true
});
/*Chinese (Simplified, PRC) locale*/
var zh = exports.zh = webix.i18n.locales["zh-CN"] = {
	hint: {
		next: "下一个",
		prev: "以前",
		last: "结束巡视"
	}
};

/***/ })
/******/ ]);
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8vd2VicGFjay9ib290c3RyYXAgMGQ5M2RhZGQ1YmIwYjlmNDljMjUiLCJ3ZWJwYWNrOi8vLy4vc291cmNlcy9sb2NhbGVzLmpzIiwid2VicGFjazovLy8uL3NvdXJjZXMvaGludC5sZXNzIiwid2VicGFjazovLy8uL3NvdXJjZXMvaGludC5qcyIsIndlYnBhY2s6Ly8vLi9zb3VyY2VzL2kxOG4vYmUuanMiLCJ3ZWJwYWNrOi8vLy4vc291cmNlcy9pMThuL2RlLmpzIiwid2VicGFjazovLy8uL3NvdXJjZXMvaTE4bi9lbi5qcyIsIndlYnBhY2s6Ly8vLi9zb3VyY2VzL2kxOG4vZXMuanMiLCJ3ZWJwYWNrOi8vLy4vc291cmNlcy9pMThuL2ZyLmpzIiwid2VicGFjazovLy8uL3NvdXJjZXMvaTE4bi9pdC5qcyIsIndlYnBhY2s6Ly8vLi9zb3VyY2VzL2kxOG4vamEuanMiLCJ3ZWJwYWNrOi8vLy4vc291cmNlcy9pMThuL3B0LmpzIiwid2VicGFjazovLy8uL3NvdXJjZXMvaTE4bi9ydS5qcyIsIndlYnBhY2s6Ly8vLi9zb3VyY2VzL2kxOG4vemguanMiXSwibmFtZXMiOlsibG9jYWxlIiwiaGludCIsIm5leHQiLCJwcmV2IiwibGFzdCIsIndlYml4IiwiaTE4biIsImV4dGVuZCIsInByb3RvVUkiLCJuYW1lIiwiZGVmYXVsdHMiLCJzdGVwcyIsImJvcmRlcmxlc3MiLCJuZXh0QnV0dG9uIiwicHJldkJ1dHRvbiIsInRvcCIsImxlZnQiLCJzdGVwVGltZW91dCIsIiRpbml0IiwiJHZpZXciLCJjbGFzc05hbWUiLCJfaSIsImF0dGFjaEV2ZW50IiwiX3NldEJvZHlDbGFzcyIsIl9ldmVudE9iaiIsImV2ZW50UmVtb3ZlIiwiX2V2ZW50T2JqRXNjIiwiX2V2ZW50UmVzaXplIiwiZGV0YWNoRXZlbnQiLCJldmVudCIsImRvY3VtZW50IiwiYm9keSIsImUiLCJrZXlDb2RlIiwiX3NraXAiLCJfc2V0UmVzaXplIiwic3RlcHNfc2V0dGVyIiwiY29uZmlnIiwibmV3Q29uZmlnIiwiaSIsImxlbmd0aCIsInBhZGRpbmciLCJ0ZXh0IiwicHVzaCIsIl9kcmF3T3ZlciIsInN0ZXBFbCIsImlubmVySFRNTCIsIl9zZXRQcm9wZXJ0aWVzIiwiY2FsbEV2ZW50IiwiX2RyYXdIaW50Iiwic2V0dGluZ3MiLCJfc3RlcCIsInRpdGxlIiwicmVmcmVzaCIsImVudiIsIm1vYmlsZSIsInNjcm9sbEludG9WaWV3IiwiX3JlRHJhdyIsIl9oaW50IiwicXVlcnlTZWxlY3RvciIsImRvY0VsZW0iLCJkb2N1bWVudEVsZW1lbnQiLCJib3giLCJnZXRCb3VuZGluZ0NsaWVudFJlY3QiLCJlbExlZnQiLCJoaWdobGlnaHRXaWR0aCIsIndpZHRoIiwiaGlnaGxpZ2h0SGVpZ2h0IiwiaGVpZ2h0IiwiaGludExlZnQiLCJoaW50V2lkdGgiLCJvZmZzZXRXaWR0aCIsImhpbnRIZWlnaHQiLCJvZmZzZXRIZWlnaHQiLCJlbFRvcCIsIndpbmRvdyIsInBhZ2VZT2Zmc2V0IiwiaGludFRvcCIsIndpbmRvd1dpZHRoIiwiaW5uZXJXaWR0aCIsImNsaWVudFdpZHRoIiwiTWF0aCIsIm1pbiIsImdldEVsZW1lbnRzQnlUYWdOYW1lIiwid2luZG93SGVpZ2h0IiwiaW5uZXJIZWlnaHQiLCJjbGllbnRIZWlnaHQiLCJzdHlsZSIsInBvaW50ZXJFdmVudHMiLCJ1c2VyU2VsZWN0IiwiX3NldFBvcyIsIl90aW1lciIsImNsZWFyVGltZW91dCIsInNldFRpbWVvdXQiLCJjc3NUZXh0IiwiX3NldEF0dHJpYnV0ZXMiLCJnZXRFbGVtZW50c0J5Q2xhc3NOYW1lIiwiaHRtbCIsImFkZENzcyIsImdldE5vZGUiLCJfaXNJbnRlZ2VyIiwiZ2V0Q3VycmVudFN0ZXAiLCJfcmVmcmVzaCIsInZhbHVlIiwiTnVtYmVyIiwiaXNJbnRlZ2VyIiwiaXNGaW5pdGUiLCJmbG9vciIsImVsIiwiYXR0cnMiLCJrZXkiLCJzZXRBdHRyaWJ1dGUiLCJldmVudEVsIiwiX2dldEVsIiwicmVtb3ZlQ3NzIiwiX3NldEV2ZW50c0J1dHRvbnMiLCJtYXJnaW4iLCJfc2V0RWxFdmVudHMiLCJfcHJldkJ1dHRvbiIsImNsYXNzTGlzdCIsImNvbnRhaW5zIiwiX25leHRCdXR0b24iLCJyZW1vdmUiLCIkJCIsIl9kcmF3U3RlcHMiLCJxdWVyeVNlbGVjdG9yQWxsIiwiX25leHQiLCJldmVudFN0ZXAiLCJmb2N1cyIsInR5cGUiLCJhY3Rpb24iLCJwcmV2aW91cyIsInByb21pc2UiLCJyZXNvbHZlIiwidGhlbiIsIl9uZXh0U3RlcCIsImJsdXIiLCJoaWRlIiwiZmlyc3REcmF3IiwicGFyZW50Tm9kZSIsInJlbW92ZUNoaWxkIiwic2hvdyIsInN2ZyIsInN0YXJ0IiwiZW5kIiwicmVzdW1lIiwic3RlcE51bWJlciIsImdldFN0ZXBzIiwic2V0U3RlcHMiLCJkZWZpbmUiLCJ1aSIsInZpZXciLCJFdmVudFN5c3RlbSIsImJlIiwibG9jYWxlcyIsImRlIiwiZW4iLCJlcyIsImZyIiwiaXQiLCJqYSIsInB0IiwicnUiLCJ6aCJdLCJtYXBwaW5ncyI6IjtBQUFBO0FBQ0E7O0FBRUE7QUFDQTs7QUFFQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTs7QUFFQTtBQUNBOztBQUVBO0FBQ0E7O0FBRUE7QUFDQTtBQUNBOzs7QUFHQTtBQUNBOztBQUVBO0FBQ0E7O0FBRUE7QUFDQSxtREFBMkMsY0FBYzs7QUFFekQ7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxhQUFLO0FBQ0w7QUFDQTs7QUFFQTtBQUNBO0FBQ0E7QUFDQSxtQ0FBMkIsMEJBQTBCLEVBQUU7QUFDdkQseUNBQWlDLGVBQWU7QUFDaEQ7QUFDQTtBQUNBOztBQUVBO0FBQ0EsOERBQXNELCtEQUErRDs7QUFFckg7QUFDQTs7QUFFQTtBQUNBOzs7Ozs7Ozs7Ozs7Ozs7QUNoRUE7O0FBQ0E7O0FBQ0E7O0FBQ0E7O0FBQ0E7O0FBQ0E7O0FBQ0E7O0FBQ0E7O0FBQ0E7O0FBQ0E7O0FBRU8sSUFBSUEsMEJBQVM7QUFDbkJDLE9BQU07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LFFBQU0sR0FERjtBQUVKQyxRQUFNLEdBRkY7QUFHSkMsUUFBTTtBQUhGO0FBRHNDLENBQXJDLEM7Ozs7Ozs7Ozs7OztBQ0FBLElBQUk2SixrQkFBSzVKLE1BQU1DLElBQU4sQ0FBV29KLE9BQVgsQ0FBbUIsT0FBbkIsSUFBOEI7QUFDN0N6SixPQUFLO0FBQ0pDLFFBQU0sU0FERjtBQUVKQyxRQUFNLFVBRkY7QUFHSkMsUUFBTTtBQUhGO0FBRHdDLENBQXZDLEM7Ozs7Ozs7Ozs7OztBQ0FBLElBQUk4SixrQkFBSzdKLE1BQU1DLElBQU4sQ0FBV29KLE9BQVgsQ0FBbUIsT0FBbkIsSUFBNEI7QUFDM0N6SixPQUFLO0FBQ0pDLFFBQU0sV0FERjtBQUVKQyxRQUFNLFlBRkY7QUFHSkMsUUFBTTtBQUhGO0FBRHNDLENBQXJDLEM7Ozs7Ozs7Ozs7OztBQ0FQO0FBQ08sSUFBSStKLGtCQUFLOUosTUFBTUMsSUFBTixDQUFXb0osT0FBWCxDQUFtQixPQUFuQixJQUE4QjtBQUM3Q3pKLE9BQUs7QUFDSkMsUUFBTSxLQURGO0FBRUpDLFFBQU0sSUFGRjtBQUdKQyxRQUFNO0FBSEY7QUFEd0MsQ0FBdkMsQyIsImZpbGUiOiJoaW50LmpzIiwic291cmNlc0NvbnRlbnQiOlsiIFx0Ly8gVGhlIG1vZHVsZSBjYWNoZVxuIFx0dmFyIGluc3RhbGxlZE1vZHVsZXMgPSB7fTtcblxuIFx0Ly8gVGhlIHJlcXVpcmUgZnVuY3Rpb25cbiBcdGZ1bmN0aW9uIF9fd2VicGFja19yZXF1aXJlX18obW9kdWxlSWQpIHtcblxuIFx0XHQvLyBDaGVjayBpZiBtb2R1bGUgaXMgaW4gY2FjaGVcbiBcdFx0aWYoaW5zdGFsbGVkTW9kdWxlc1ttb2R1bGVJZF0pIHtcbiBcdFx0XHRyZXR1cm4gaW5zdGFsbGVkTW9kdWxlc1ttb2R1bGVJZF0uZXhwb3J0cztcbiBcdFx0fVxuIFx0XHQvLyBDcmVhdGUgYSBuZXcgbW9kdWxlIChhbmQgcHV0IGl0IGludG8gdGhlIGNhY2hlKVxuIFx0XHR2YXIgbW9kdWxlID0gaW5zdGFsbGVkTW9kdWxlc1ttb2R1bGVJZF0gPSB7XG4gXHRcdFx0aTogbW9kdWxlSWQsXG4gXHRcdFx0bDogZmFsc2UsXG4gXHRcdFx0ZXhwb3J0czoge31cbiBcdFx0fTtcblxuIFx0XHQvLyBFeGVjdXRlIHRoZSBtb2R1bGUgZnVuY3Rpb25cbiBcdFx0bW9kdWxlc1ttb2R1bGVJZF0uY2FsbChtb2R1bGUuZXhwb3J0cywgbW9kdWxlLCBtb2R1bGUuZXhwb3J0cywgX193ZWJwYWNrX3JlcXVpcmVfXyk7XG5cbiBcdFx0Ly8gRmxhZyB0aGUgbW9kdWxlIGFzIGxvYWRlZFxuIFx0XHRtb2R1bGUubCA9IHRydWU7XG5cbiBcdFx0Ly8gUmV0dXJuIHRoZSBleHBvcnRzIG9mIHRoZSBtb2R1bGVcbiBcdFx0cmV0dXJuIG1vZHVsZS5leHBvcnRzO1xuIFx0fVxuXG5cbiBcdC8vIGV4cG9zZSB0aGUgbW9kdWxlcyBvYmplY3QgKF9fd2VicGFja19tb2R1bGVzX18pXG4gXHRfX3dlYnBhY2tfcmVxdWlyZV9fLm0gPSBtb2R1bGVzO1xuXG4gXHQvLyBleHBvc2UgdGhlIG1vZHVsZSBjYWNoZVxuIFx0X193ZWJwYWNrX3JlcXVpcmVfXy5jID0gaW5zdGFsbGVkTW9kdWxlcztcblxuIFx0Ly8gaWRlbnRpdHkgZnVuY3Rpb24gZm9yIGNhbGxpbmcgaGFybW9ueSBpbXBvcnRzIHdpdGggdGhlIGNvcnJlY3QgY29udGV4dFxuIFx0X193ZWJwYWNrX3JlcXVpcmVfXy5pID0gZnVuY3Rpb24odmFsdWUpIHsgcmV0dXJuIHZhbHVlOyB9O1xuXG4gXHQvLyBkZWZpbmUgZ2V0dGVyIGZ1bmN0aW9uIGZvciBoYXJtb255IGV4cG9ydHNcbiBcdF9fd2VicGFja19yZXF1aXJlX18uZCA9IGZ1bmN0aW9uKGV4cG9ydHMsIG5hbWUsIGdldHRlcikge1xuIFx0XHRpZighX193ZWJwYWNrX3JlcXVpcmVfXy5vKGV4cG9ydHMsIG5hbWUpKSB7XG4gXHRcdFx0T2JqZWN0LmRlZmluZVByb3BlcnR5KGV4cG9ydHMsIG5hbWUsIHtcbiBcdFx0XHRcdGNvbmZpZ3VyYWJsZTogZmFsc2UsXG4gXHRcdFx0XHRlbnVtZXJhYmxlOiB0cnVlLFxuIFx0XHRcdFx0Z2V0OiBnZXR0ZXJcbiBcdFx0XHR9KTtcbiBcdFx0fVxuIFx0fTtcblxuIFx0Ly8gZ2V0RGVmYXVsdEV4cG9ydCBmdW5jdGlvbiBmb3IgY29tcGF0aWJpbGl0eSB3aXRoIG5vbi1oYXJtb255IG1vZHVsZXNcbiBcdF9fd2VicGFja19yZXF1aXJlX18ubiA9IGZ1bmN0aW9uKG1vZHVsZSkge1xuIFx0XHR2YXIgZ2V0dGVyID0gbW9kdWxlICYmIG1vZHVsZS5fX2VzTW9kdWxlID9cbiBcdFx0XHRmdW5jdGlvbiBnZXREZWZhdWx0KCkgeyByZXR1cm4gbW9kdWxlWydkZWZhdWx0J107IH0gOlxuIFx0XHRcdGZ1bmN0aW9uIGdldE1vZHVsZUV4cG9ydHMoKSB7IHJldHVybiBtb2R1bGU7IH07XG4gXHRcdF9fd2VicGFja19yZXF1aXJlX18uZChnZXR0ZXIsICdhJywgZ2V0dGVyKTtcbiBcdFx0cmV0dXJuIGdldHRlcjtcbiBcdH07XG5cbiBcdC8vIE9iamVjdC5wcm90b3R5cGUuaGFzT3duUHJvcGVydHkuY2FsbFxuIFx0X193ZWJwYWNrX3JlcXVpcmVfXy5vID0gZnVuY3Rpb24ob2JqZWN0LCBwcm9wZXJ0eSkgeyByZXR1cm4gT2JqZWN0LnByb3RvdHlwZS5oYXNPd25Qcm9wZXJ0eS5jYWxsKG9iamVjdCwgcHJvcGVydHkpOyB9O1xuXG4gXHQvLyBfX3dlYnBhY2tfcHVibGljX3BhdGhfX1xuIFx0X193ZWJwYWNrX3JlcXVpcmVfXy5wID0gXCIvY29kZWJhc2UvXCI7XG5cbiBcdC8vIExvYWQgZW50cnkgbW9kdWxlIGFuZCByZXR1cm4gZXhwb3J0c1xuIFx0cmV0dXJuIF9fd2VicGFja19yZXF1aXJlX18oX193ZWJwYWNrX3JlcXVpcmVfXy5zID0gMik7XG5cblxuXG4vLyBXRUJQQUNLIEZPT1RFUiAvL1xuLy8gd2VicGFjay9ib290c3RyYXAgMGQ5M2RhZGQ1YmIwYjlmNDljMjUiLCJpbXBvcnQgXCIuL2kxOG4vZW5cIjtcbmltcG9ydCBcIi4vaTE4bi9mclwiO1xuaW1wb3J0IFwiLi9pMThuL2JlXCI7XG5pbXBvcnQgXCIuL2kxOG4vZGVcIjtcbmltcG9ydCBcIi4vaTE4bi9lc1wiO1xuaW1wb3J0IFwiLi9pMThuL2l0XCI7XG5pbXBvcnQgXCIuL2kxOG4vamFcIjtcbmltcG9ydCBcIi4vaTE4bi9wdFwiO1xuaW1wb3J0IFwiLi9pMThuL3J1XCI7XG5pbXBvcnQgXCIuL2kxOG4vemhcIjtcblxuZXhwb3J0IGxldCBsb2NhbGUgPSB7XG5cdGhpbnQ6IHtcblx0XHRuZXh0OiBcIk5leHRcIixcblx0XHRwcmV2OiBcIlByZXZpb3VzXCIsXG5cdFx0bGFzdDogXCJFbmQgVG91clwiXG5cdH1cbn07XG5cblxuLy8gV0VCUEFDSyBGT09URVIgLy9cbi8vIC4vc291cmNlcy9sb2NhbGVzLmpzIiwiLy8gcmVtb3ZlZCBieSBleHRyYWN0LXRleHQtd2VicGFjay1wbHVnaW5cblxuXG4vLy8vLy8vLy8vLy8vLy8vLy9cbi8vIFdFQlBBQ0sgRk9PVEVSXG4vLyAuL3NvdXJjZXMvaGludC5sZXNzXG4vLyBtb2R1bGUgaWQgPSAxXG4vLyBtb2R1bGUgY2h1bmtzID0gMCIsImltcG9ydCBcIi4vaGludC5sZXNzXCI7XG5pbXBvcnQgeyBsb2NhbGUgfSBmcm9tIFwiLi9sb2NhbGVzXCI7XG5cbndlYml4LmkxOG4uaGludCA9IHdlYml4LmV4dGVuZCh3ZWJpeC5pMThuLCBsb2NhbGUpLmhpbnQ7XG5cbndlYml4LnByb3RvVUkoe1xuXHRuYW1lOiBcImhpbnRcIixcblx0ZGVmYXVsdHM6IHtcblx0XHRzdGVwczogW10sXG5cdFx0Ym9yZGVybGVzczogdHJ1ZSxcblx0XHRuZXh0QnV0dG9uOiB0cnVlLFxuXHRcdHByZXZCdXR0b246IHRydWUsXG5cdFx0dG9wOiBmYWxzZSxcblx0XHRsZWZ0OiBmYWxzZSxcblx0XHRzdGVwVGltZW91dDo1MDBcblx0fSxcblx0JGluaXQoKSB7XG5cdFx0dGhpcy4kdmlldy5jbGFzc05hbWUgKz0gXCIgd2ViaXhfaGludF92aWV3XCI7XG5cdFx0dGhpcy5faSA9IC0xO1xuXHRcdHRoaXMuYXR0YWNoRXZlbnQoXCJvbkRlc3RydWN0XCIsICgpID0+IHtcblx0XHRcdHRoaXMuX3NldEJvZHlDbGFzcyhcInJlbW92ZVwiKTtcblx0XHRcdGlmKHRoaXMuX2V2ZW50T2JqKSB7XG5cdFx0XHRcdHdlYml4LmV2ZW50UmVtb3ZlKHRoaXMuX2V2ZW50T2JqKTtcblx0XHRcdH1cblx0XHRcdGlmKHRoaXMuX2V2ZW50T2JqRXNjKSB7XG5cdFx0XHRcdHdlYml4LmV2ZW50UmVtb3ZlKHRoaXMuX2V2ZW50T2JqRXNjKTtcblx0XHRcdH1cblx0XHRcdGlmKHRoaXMuX2V2ZW50UmVzaXplKSB7XG5cdFx0XHRcdHdlYml4LmRldGFjaEV2ZW50KHRoaXMuX2V2ZW50UmVzaXplKTtcblx0XHRcdH1cblx0XHR9KTtcblx0XHR0aGlzLl9ldmVudE9iakVzYyA9IHdlYml4LmV2ZW50KGRvY3VtZW50LmJvZHksXCJrZXlkb3duXCIsIChlKSA9PiB7XG5cdFx0XHQvLyBlc2NhcGVcblx0XHRcdGlmIChlLmtleUNvZGUgPT0gMjcpe1xuXHRcdFx0XHR0aGlzLl9za2lwKCk7XG5cdFx0XHR9XG5cdFx0fSk7XG5cdFx0dGhpcy5fc2V0UmVzaXplKCk7XG5cdH0sXG5cdHN0ZXBzX3NldHRlcihjb25maWcpIHtcblx0XHRsZXQgbmV3Q29uZmlnID0gW107XG5cdFx0Zm9yICh2YXIgaSA9IDA7IGkgPCBjb25maWcubGVuZ3RoOyBpKyspIHtcblx0XHRcdGNvbmZpZ1tpXS5wYWRkaW5nID0gY29uZmlnW2ldLnBhZGRpbmcgfHwgMDtcblx0XHRcdGNvbmZpZ1tpXS50ZXh0ID0gY29uZmlnW2ldLnRleHQgfHwgXCJcIjtcblx0XHRcdG5ld0NvbmZpZy5wdXNoKGNvbmZpZ1tpXSk7XG5cdFx0fVxuXHRcdHJldHVybiBuZXdDb25maWc7XG5cdH0sXG5cdF9kcmF3T3ZlcihzdGVwRWwpIHtcblx0XHR0aGlzLiR2aWV3LmlubmVySFRNTCArPSBgPHN2ZyBwcmVzZXJ2ZUFzcGVjdFJhdGlvPVwibm9uZVwiIHdpZHRoPVwiMTAwJVwiIGhlaWdodD1cIjEwMCVcIiBjbGFzcz1cIndlYml4X2hpbnRfb3ZlcmxheVwiIHByZXNlcnZlQXNwZWN0UmF0aW89XCJub25lXCI+XG5cdFx0XHQ8ZGVmcz5cblx0XHRcdFx0PG1hc2sgaWQ9XCJob2xlXCI+XG5cdFx0XHRcdFx0PHJlY3QgY2xhc3M9XCJ3ZWJpeF9oaW50X292ZXJsYXlfaG9sZVwiIHdpZHRoPVwiMTAwJVwiIGhlaWdodD1cIjEwMCVcIiBmaWxsPVwid2hpdGVcIi8+XG5cdFx0XHRcdFx0PHJlY3QgY2xhc3M9XCJ3ZWJpeF9oaW50X292ZXJsYXlfaG9sZSB3ZWJpeF9oaW50X292ZXJsYXlfaG9sZV9lbFwiIHg9XCIwXCIgeT1cIjBcIiB3aWR0aD1cIjBcIiBoZWlnaHQ9XCIwXCIgZmlsbD1cIndoaXRlXCIvPlxuXHRcdFx0XHQ8L21hc2s+XG5cdFx0XHQ8L2RlZnM+XG5cdFx0XHQ8cmVjdCBjbGFzcz1cIndlYml4X2hpbnRfb3ZlcmxheV9ob2xlXCIgd2lkdGg9XCIxMDAlXCIgaGVpZ2h0PVwiMTAwJVwiIG1hc2s9XCJ1cmwoI2hvbGUpXCIgLz5cblx0XHQ8L3N2Zz5gO1xuXHRcdHRoaXMuX3NldFByb3BlcnRpZXMoc3RlcEVsKTtcblx0XHR0aGlzLmNhbGxFdmVudChcIm9uQWZ0ZXJTdGFydFwiLCBbXSk7XG5cdH0sXG5cdF9kcmF3SGludCgpIHtcblx0XHRsZXQgc2V0dGluZ3MgPSB0aGlzLmNvbmZpZztcblx0XHR0aGlzLiR2aWV3LmlubmVySFRNTCArPSBgPGRpdiBjbGFzcz1cIndlYml4X2hpbnRcIj5cblx0XHRcdDxkaXYgY2xhc3M9J3dlYml4X2hpbnRfdGl0bGUnPiR7dGhpcy5fc3RlcC50aXRsZT90aGlzLl9zdGVwLnRpdGxlOlwiXCJ9PC9kaXY+XG5cdFx0XHQ8ZGl2IGNsYXNzPVwid2ViaXhfaGludF9sYWJlbFwiPiR7dGhpcy5fc3RlcC50ZXh0fTwvZGl2PlxuXHRcdFx0PGRpdiBjbGFzcz1cIndlYml4X2hpbnRfcHJvZ3Jlc3NcIj5cblx0XHRcdFx0JHt0aGlzLl9pKzF9LyR7dGhpcy5jb25maWcuc3RlcHMubGVuZ3RofVxuXHRcdFx0PC9kaXY+XG5cdFx0XHQ8ZGl2IGNsYXNzPVwid2ViaXhfaGludF9idXR0b25zXCI+XG5cdFx0XHRcdCR7c2V0dGluZ3MucHJldkJ1dHRvbiE9PSBmYWxzZT9gPGJ1dHRvbiBjbGFzcz1cIndlYml4X2hpbnRfYnV0dG9uIHdlYml4X2hpbnRfYnV0dG9uX3ByZXYgd2ViaXhfaGludF9idXR0b25faGlkZGVuXCI+JHt0eXBlb2Ygc2V0dGluZ3MucHJldkJ1dHRvbiA9PSBcInN0cmluZ1wiP3NldHRpbmdzLnByZXZCdXR0b246YCR7d2ViaXguaTE4bi5oaW50LnByZXZ9YH08L2J1dHRvbj5gOlwiXCJ9XG5cdFx0XHRcdCR7c2V0dGluZ3MubmV4dEJ1dHRvbiE9PSBmYWxzZT9gPGJ1dHRvbiBjbGFzcz1cIndlYml4X2hpbnRfYnV0dG9uIHdlYml4X2hpbnRfYnV0dG9uX25leHRcIj4ke3R5cGVvZiBzZXR0aW5ncy5uZXh0QnV0dG9uID09IFwic3RyaW5nXCI/c2V0dGluZ3MubmV4dEJ1dHRvbjpgJHt3ZWJpeC5pMThuLmhpbnQubmV4dH1gfTwvYnV0dG9uPmA6XCJcIn1cblx0XHRcdDwvZGl2PlxuXHRcdFx0PGJ1dHRvbiBjbGFzcz1cIndlYml4X2hpbnRfYnV0dG9uX2Nsb3NlXCIgdGl0bGU9XCJDbG9zZVwiPiYjMTAwMDU7PC9idXR0b24+XG5cdFx0PC9kaXY+YDtcblx0fSxcblx0X3NldFByb3BlcnRpZXMoc3RlcEVsLCByZWZyZXNoKSB7XG5cdFx0aWYoIXN0ZXBFbCkge1xuXHRcdFx0cmV0dXJuO1xuXHRcdH1cblxuXHRcdGlmKCF3ZWJpeC5lbnYubW9iaWxlKSB7XG5cdFx0XHRzdGVwRWwuc2Nyb2xsSW50b1ZpZXcoZmFsc2UpO1xuXHRcdH1cblx0XHR0aGlzLl9zdGVwID0gdGhpcy5jb25maWcuc3RlcHNbdGhpcy5faV07XG5cdFx0dGhpcy5fcmVEcmF3KHN0ZXBFbCwgcmVmcmVzaCk7XG5cdFx0dGhpcy5faGludCA9IHRoaXMuJHZpZXcucXVlcnlTZWxlY3RvcihcIi53ZWJpeF9oaW50XCIpO1xuXG5cdFx0bGV0IHBhZGRpbmcgPSAzMDtcblx0XHRsZXQgZG9jRWxlbSA9IGRvY3VtZW50LmRvY3VtZW50RWxlbWVudDtcblx0XHRsZXQgYm94ID0gc3RlcEVsLmdldEJvdW5kaW5nQ2xpZW50UmVjdCgpO1xuXHRcdGxldCBlbExlZnQgPSBib3gubGVmdCArIHRoaXMuX3N0ZXAucGFkZGluZztcblx0XHRsZXQgaGlnaGxpZ2h0V2lkdGggPSBib3gud2lkdGg7XG5cdFx0bGV0IGhpZ2hsaWdodEhlaWdodCA9IGJveC5oZWlnaHQ7XG5cdFx0bGV0IGhpbnRMZWZ0ID0gZWxMZWZ0IC0gdGhpcy5fc3RlcC5wYWRkaW5nO1xuXHRcdGxldCBoaW50V2lkdGggPSB0aGlzLl9oaW50Lm9mZnNldFdpZHRoO1xuXHRcdGxldCBoaW50SGVpZ2h0ID0gdGhpcy5faGludC5vZmZzZXRIZWlnaHQ7XG5cdFx0bGV0IGVsVG9wID0gd2ViaXguZW52Lm1vYmlsZSA/IGJveC50b3AgKyB0aGlzLl9zdGVwLnBhZGRpbmcgOiBib3gudG9wICsgdGhpcy5fc3RlcC5wYWRkaW5nICsgd2luZG93LnBhZ2VZT2Zmc2V0O1xuXHRcdGxldCBoaW50VG9wID0gZWxUb3AgKyBoaWdobGlnaHRIZWlnaHQgKyB0aGlzLl9zdGVwLnBhZGRpbmcgKyBwYWRkaW5nO1xuXHRcdGxldCB3aW5kb3dXaWR0aCA9IHdpbmRvdy5pbm5lcldpZHRoICYmIGRvY0VsZW0uY2xpZW50V2lkdGggPyBNYXRoLm1pbih3aW5kb3cuaW5uZXJXaWR0aCwgZG9jRWxlbS5jbGllbnRXaWR0aCkgOiB3aW5kb3cuaW5uZXJXaWR0aCB8fCBkb2NFbGVtLmNsaWVudFdpZHRoIHx8IGRvY3VtZW50LmdldEVsZW1lbnRzQnlUYWdOYW1lKFwiYm9keVwiKVswXS5jbGllbnRXaWR0aDtcblx0XHRsZXQgd2luZG93SGVpZ2h0ID0gd2luZG93LmlubmVySGVpZ2h0ICYmIGRvY0VsZW0uY2xpZW50SGVpZ2h0ID8gTWF0aC5taW4od2luZG93LmlubmVySGVpZ2h0LCBkb2NFbGVtLmNsaWVudEhlaWdodCkgOiB3aW5kb3cuaW5uZXJIZWlnaHQgfHwgZG9jRWxlbS5jbGllbnRIZWlnaHQgfHwgZG9jdW1lbnQuZ2V0RWxlbWVudHNCeVRhZ05hbWUoXCJib2R5XCIpWzBdLmNsaWVudEhlaWdodDtcblx0XHRcblx0XHRzdGVwRWwuc3R5bGUucG9pbnRlckV2ZW50cyA9IFwiYWxsXCI7XG5cdFx0c3RlcEVsLnN0eWxlLnVzZXJTZWxlY3QgPSBcImluaXRpYWxcIjtcblxuXHRcdC8vIHNldCBoaW50IHBvc2l0aW9uXG5cdFx0aWYoZWxMZWZ0IC0gd2luZG93V2lkdGggPiAwKSB7XG5cdFx0XHRlbExlZnQgPSBlbExlZnQgLSB3aW5kb3dXaWR0aCArIGhpbnRXaWR0aCArIGhpZ2hsaWdodFdpZHRoO1xuXHRcdH1cblxuXHRcdGlmKHdpbmRvd0hlaWdodCAvMiA8IGVsVG9wKSB7IC8vIGJvdHRvbVxuXHRcdFx0aGludFRvcCA9IGVsVG9wIC0gaGludEhlaWdodCAtIHBhZGRpbmcgLSB0aGlzLl9zdGVwLnBhZGRpbmcqMjtcblx0XHR9IGVsc2UgaWYod2luZG93V2lkdGggLzIgPCBlbExlZnQgJiYgZWxMZWZ0ICsgaGludFdpZHRoIDwgd2luZG93V2lkdGggJiYgaGlnaGxpZ2h0V2lkdGggKyBoaW50V2lkdGggPCB3aW5kb3dXaWR0aCkgeyAvLyByaWdodFxuXHRcdFx0aGludFRvcCA9IGhpZ2hsaWdodEhlaWdodCAvIDIgKyBlbFRvcCAtIHRoaXMuX3N0ZXAucGFkZGluZztcblx0XHRcdGhpbnRMZWZ0ID0gZWxMZWZ0IC0gaGludFdpZHRoIC0gdGhpcy5fc3RlcC5wYWRkaW5nIC0gcGFkZGluZztcblx0XHR9IGVsc2UgaWYod2luZG93V2lkdGggLzIgPiBlbExlZnQgJiYgZWxMZWZ0ICsgaGludFdpZHRoICsgaGlnaGxpZ2h0V2lkdGggPCB3aW5kb3dXaWR0aCkgeyAvLyBsZWZ0XG5cdFx0XHRoaW50TGVmdCA9IGhpZ2hsaWdodFdpZHRoICsgZWxMZWZ0ICsgcGFkZGluZztcblx0XHRcdGhpbnRUb3AgPSBlbFRvcCAtIHRoaXMuX3N0ZXAucGFkZGluZztcblx0XHR9IGVsc2UgaWYoaGludFRvcD53aW5kb3dIZWlnaHQgJiYgaGludEhlaWdodCtoaWdobGlnaHRIZWlnaHQ8d2luZG93SGVpZ2h0KXsvL3RvcCwgYnV0IGhpbnQgZG9lcyBub3QgZml0XG5cdFx0XHRoaW50VG9wID0gZWxUb3AgLSBoaW50SGVpZ2h0IC0gcGFkZGluZyAtIHRoaXMuX3N0ZXAucGFkZGluZyoyO1xuXHRcdH0gZWxzZSBpZihoaW50VG9wID53aW5kb3dIZWlnaHQgfHwgaGludFRvcCtoaW50SGVpZ2h0PndpbmRvd0hlaWdodCl7XG5cdFx0XHRoaW50TGVmdCA9IGVsTGVmdCAtIGhpbnRXaWR0aCAtIHRoaXMuX3N0ZXAucGFkZGluZyoyIC0gcGFkZGluZztcblx0XHRcdGhpbnRUb3AgPSBlbFRvcCAtIHRoaXMuX3N0ZXAucGFkZGluZztcblx0XHR9XG5cblx0XHRpZihoaW50TGVmdCArIGhpbnRXaWR0aCA+IHdpbmRvd1dpZHRoKSB7IC8vIGZvciBvdmVyZmxvd1xuXHRcdFx0aGludExlZnQgPSB3aW5kb3dXaWR0aCAtIGhpbnRXaWR0aDtcblx0XHR9IGVsc2UgaWYoaGludFRvcCA8IDAgfHwgaGludFRvcCA+IHdpbmRvd0hlaWdodCkge1xuXHRcdFx0aGludFRvcCA9IHBhZGRpbmc7XG5cdFx0fSBlbHNlIGlmKHdpbmRvd1dpZHRoIDwgaGlnaGxpZ2h0V2lkdGggfHwgaGludExlZnQgPCAwKSB7XG5cdFx0XHRoaW50TGVmdCA9IHBhZGRpbmc7XG5cdFx0fVxuXHRcdGhpbnRUb3AgPSB0aGlzLl9zZXRQb3MoXCJ0b3BcIik/dGhpcy5fc2V0UG9zKFwidG9wXCIpOmhpbnRUb3A7XG5cdFx0aGludExlZnQgPSB0aGlzLl9zZXRQb3MoXCJsZWZ0XCIpP3RoaXMuX3NldFBvcyhcImxlZnRcIik6aGludExlZnQ7XG5cblx0XHRpZih3ZWJpeC5lbnYubW9iaWxlKSB7XG5cdFx0XHRzdGVwRWwuc2Nyb2xsSW50b1ZpZXcoZmFsc2UpO1xuXHRcdH1cblx0XHRpZih0aGlzLl90aW1lcikge2NsZWFyVGltZW91dCh0aGlzLl90aW1lcik7fVxuXHRcdHRoaXMuX3RpbWVyID0gc2V0VGltZW91dCgoKSA9PiB7XG5cdFx0XHR0aGlzLl9oaW50LnN0eWxlLmNzc1RleHQgPSBgdG9wOiR7aGludFRvcH1weDsgbGVmdDoke2hpbnRMZWZ0fXB4O2A7XG5cdFx0XHR0aGlzLl9zZXRBdHRyaWJ1dGVzKHRoaXMuJHZpZXcuZ2V0RWxlbWVudHNCeUNsYXNzTmFtZShcIndlYml4X2hpbnRfb3ZlcmxheV9ob2xlX2VsXCIpWzBdLCB7XCJ4XCI6ZWxMZWZ0LXRoaXMuX3N0ZXAucGFkZGluZyoyLCBcInlcIjplbFRvcC10aGlzLl9zdGVwLnBhZGRpbmcqMiwgXCJ3aWR0aFwiOmhpZ2hsaWdodFdpZHRoK3RoaXMuX3N0ZXAucGFkZGluZyAqMiwgXCJoZWlnaHRcIjpoaWdobGlnaHRIZWlnaHQrdGhpcy5fc3RlcC5wYWRkaW5nKjJ9KTtcblx0XHRcdHdlYml4Lmh0bWwuYWRkQ3NzKHRoaXMuZ2V0Tm9kZSgpLCBcIndlYml4X2hpbnRfYW5pbWF0ZWRcIik7XG5cdFx0fSwgdGhpcy5jb25maWcuc3RlcFRpbWVvdXQpO1xuXHR9LFxuXHRfc2V0UG9zKG5hbWUpIHtcblx0XHRpZih0aGlzLl9pc0ludGVnZXIodGhpcy5fc3RlcFtuYW1lXSkpIHtcblx0XHRcdHJldHVybiB0aGlzLl9zdGVwW25hbWVdO1xuXHRcdH0gZWxzZSBpZih0aGlzLl9pc0ludGVnZXIodGhpcy5jb25maWdbbmFtZV0pICYmIHRoaXMuX3N0ZXBbbmFtZV0gIT09IGZhbHNlKXtcblx0XHRcdHJldHVybiB0aGlzLmNvbmZpZ1tuYW1lXTtcblx0XHR9XG5cdH0sXG5cdF9zZXRSZXNpemUoKSB7XG5cdFx0dGhpcy5fZXZlbnRSZXNpemUgPSB3ZWJpeC5hdHRhY2hFdmVudChcIm9uUmVzaXplXCIsICgpID0+IHtcblx0XHRcdGlmKHRoaXMuZ2V0Q3VycmVudFN0ZXAoKSAmJiB0aGlzLl9pICE9PSB0aGlzLmNvbmZpZy5zdGVwcy5sZW5ndGgpIHtcblx0XHRcdFx0dGhpcy5fcmVmcmVzaCh0aGlzLmdldEN1cnJlbnRTdGVwKCksIGZhbHNlLCB0cnVlKTtcblx0XHRcdH1cblx0XHR9KTtcblx0fSxcblx0X2lzSW50ZWdlcih2YWx1ZSkge1xuXHRcdGlmKE51bWJlci5pc0ludGVnZXIpIHJldHVybiBOdW1iZXIuaXNJbnRlZ2VyKHZhbHVlKTtcblx0XHRyZXR1cm4gdHlwZW9mIHZhbHVlID09PSBcIm51bWJlclwiICYmIFxuXHRcdFx0aXNGaW5pdGUodmFsdWUpICYmIFxuXHRcdFx0TWF0aC5mbG9vcih2YWx1ZSkgPT09IHZhbHVlO1xuXHR9LFxuXHRfc2V0QXR0cmlidXRlcyhlbCwgYXR0cnMpIHtcblx0XHRmb3IodmFyIGtleSBpbiBhdHRycykge1xuXHRcdFx0ZWwuc2V0QXR0cmlidXRlKGtleSwgYXR0cnNba2V5XSk7XG5cdFx0fVxuXHR9LFxuXHRfcmVEcmF3KHN0ZXBFbCwgcmVmcmVzaCkge1xuXHRcdGxldCB0aXRsZSA9IHRoaXMuJHZpZXcucXVlcnlTZWxlY3RvcihcIi53ZWJpeF9oaW50X3RpdGxlXCIpO1xuXHRcdGxldCBlbDtcblxuXHRcdHRoaXMuX3N0ZXAuZXZlbnRFbD9lbCA9IHRoaXMuX2dldEVsKHRoaXMuX3N0ZXAuZXZlbnRFbCk6ZWwgPSBzdGVwRWw7XG5cdFx0aWYodGhpcy5faSA+IDAgJiYgIXJlZnJlc2gpIHtcblx0XHRcdHdlYml4Lmh0bWwucmVtb3ZlQ3NzKHRoaXMuZ2V0Tm9kZSgpLCBcIndlYml4X2hpbnRfYW5pbWF0ZWRcIik7XG5cdFx0XHR0aXRsZS5pbm5lckhUTUwgPSB0aGlzLl9zdGVwLnRpdGxlIHx8IFwiXCI7XG5cdFx0XHR0aGlzLiR2aWV3LnF1ZXJ5U2VsZWN0b3IoXCIud2ViaXhfaGludF9sYWJlbFwiKS5pbm5lckhUTUwgPSB0aGlzLl9zdGVwLnRleHQgfHwgXCJcIjtcblx0XHRcdHRoaXMuJHZpZXcucXVlcnlTZWxlY3RvcihcIi53ZWJpeF9oaW50X3Byb2dyZXNzXCIpLmlubmVySFRNTCA9IGAke3RoaXMuX2krMX0vJHt0aGlzLmNvbmZpZy5zdGVwcy5sZW5ndGh9YDtcblx0XHR9IGVsc2Uge1xuXHRcdFx0dGhpcy5fZHJhd0hpbnQoKTtcblx0XHRcdHRoaXMuX3NldEV2ZW50c0J1dHRvbnMoZWwpO1xuXHRcdH1cblx0XHRpZighdGhpcy5fc3RlcC50aXRsZSAmJiB0aXRsZSkge1xuXHRcdFx0dGl0bGUuc3R5bGUubWFyZ2luID0gXCIwXCI7XG5cdFx0fVxuXHRcdHRoaXMuX3NldEVsRXZlbnRzKGVsKTtcblxuXHRcdGlmKHRoaXMuX3ByZXZCdXR0b24pIHtcblx0XHRcdGlmKHRoaXMuX2kgPiAwKSB7IC8vIHByZXZpb3VzIGJ1dHRvbiBzaG93XG5cdFx0XHRcdHdlYml4Lmh0bWwucmVtb3ZlQ3NzKHRoaXMuX3ByZXZCdXR0b24sIFwid2ViaXhfaGludF9idXR0b25faGlkZGVuXCIpO1xuXHRcdFx0fSBlbHNlIGlmKHRoaXMuX3ByZXZCdXR0b24gJiYgIXRoaXMuX3ByZXZCdXR0b24uY2xhc3NMaXN0LmNvbnRhaW5zKFwid2ViaXhfaGludF9idXR0b25faGlkZGVuXCIpKSB7XG5cdFx0XHRcdHdlYml4Lmh0bWwuYWRkQ3NzKHRoaXMuX3ByZXZCdXR0b24sIFwid2ViaXhfaGludF9idXR0b25faGlkZGVuXCIpO1xuXHRcdFx0fVxuXHRcdH1cblx0XHRcblx0XHRpZih0aGlzLl9pID09PSB0aGlzLmNvbmZpZy5zdGVwcy5sZW5ndGggLTEgJiYgdGhpcy5fbmV4dEJ1dHRvbikgeyAvLyBuZXh0IGJ1dHRvbiB0ZXh0XG5cdFx0XHR0aGlzLl9uZXh0QnV0dG9uLmlubmVySFRNTCA9IGAke3R5cGVvZiB0aGlzLmNvbmZpZy5uZXh0QnV0dG9uID09IFwic3RyaW5nXCI/dGhpcy5jb25maWcubmV4dEJ1dHRvbjpgJHt3ZWJpeC5pMThuLmhpbnQubGFzdH1gfWA7XG5cdFx0fVxuXHR9LFxuXHRfc2V0Qm9keUNsYXNzKHJlbW92ZSkge1xuXHRcdGxldCBib2R5ID0gZG9jdW1lbnQuYm9keTtcblx0XHRpZihyZW1vdmUpIHtcblx0XHRcdHdlYml4Lmh0bWwucmVtb3ZlQ3NzKGJvZHksIFwid2ViaXhfaGludF9vdmVyZmxvd1wiKTtcblx0XHR9IGVsc2UgaWYoIWJvZHkuY2xhc3NMaXN0LmNvbnRhaW5zKFwid2ViaXhfaGludF9vdmVyZmxvd1wiKSkge1xuXHRcdFx0d2ViaXguaHRtbC5hZGRDc3MoYm9keSwgXCJ3ZWJpeF9oaW50X292ZXJmbG93XCIpO1xuXHRcdH1cblx0fSxcblx0X2dldEVsKGVsKSB7XG5cdFx0aWYoJCQoZWwpKSB7XG5cdFx0XHRyZXR1cm4gJCQoZWwpLmdldE5vZGUoKTtcblx0XHR9IGVsc2Uge1xuXHRcdFx0cmV0dXJuIGRvY3VtZW50LnF1ZXJ5U2VsZWN0b3IoZWwpO1xuXHRcdH1cblx0fSxcblx0X2RyYXdTdGVwcyhyZWZyZXNoKSB7XG5cdFx0aWYodGhpcy5jb25maWcuc3RlcHNbdGhpcy5faV0pIHtcblx0XHRcdGxldCBlbCA9IHRoaXMuX2dldEVsKHRoaXMuY29uZmlnLnN0ZXBzW3RoaXMuX2ldLmVsKTtcblx0XHRcdGlmKHRoaXMuX2kgPT09IDAgJiYgIXJlZnJlc2gpIHtcblx0XHRcdFx0dGhpcy5jYWxsRXZlbnQoXCJvbkJlZm9yZVN0YXJ0XCIsIFtdKTtcblx0XHRcdFx0c2V0VGltZW91dCgoKSA9PiB7IC8vIGZvciBmaXJzdCBpbml0XG5cdFx0XHRcdFx0dGhpcy5fZHJhd092ZXIoZWwpO1xuXHRcdFx0XHR9LCAxMDApO1xuXHRcdFx0fSBlbHNlIHtcblx0XHRcdFx0dGhpcy5fc2V0UHJvcGVydGllcyhlbCwgcmVmcmVzaCk7XG5cdFx0XHR9XG5cdFx0fSBlbHNlIHtcblx0XHRcdHRoaXMuX3NraXAoKTtcblx0XHR9XG5cdH0sXG5cdF9zZXRFdmVudHNCdXR0b25zKCkge1xuXHRcdHRoaXMuX3ByZXZCdXR0b24gPSB0aGlzLiR2aWV3LnF1ZXJ5U2VsZWN0b3JBbGwoXCIud2ViaXhfaGludF9idXR0b25fcHJldlwiKVswXTtcblx0XHR0aGlzLl9uZXh0QnV0dG9uID0gdGhpcy4kdmlldy5xdWVyeVNlbGVjdG9yQWxsKFwiLndlYml4X2hpbnRfYnV0dG9uX25leHRcIilbMF07XG5cdFx0bGV0IGVsO1xuXHRcdGlmKHRoaXMuX25leHRCdXR0b24pIHtcblx0XHRcdHdlYml4LmV2ZW50KHRoaXMuX25leHRCdXR0b24sIFwiY2xpY2tcIiwgKCkgPT4ge1xuXHRcdFx0XHR0aGlzLl9uZXh0KGVsLCBcIm5leHRcIik7XG5cdFx0XHR9KTtcblx0XHR9XG5cdFx0aWYodGhpcy5fcHJldkJ1dHRvbikge1xuXHRcdFx0d2ViaXguZXZlbnQodGhpcy5fcHJldkJ1dHRvbiwgXCJjbGlja1wiLCAoKSA9PiB7XG5cdFx0XHRcdHRoaXMuX25leHQoZWwsIFwicHJldmlvdXNcIik7XG5cdFx0XHR9KTtcblx0XHR9XG5cdFx0d2ViaXguZXZlbnQodGhpcy4kdmlldy5xdWVyeVNlbGVjdG9yKFwiLndlYml4X2hpbnRfYnV0dG9uX2Nsb3NlXCIpLCBcImNsaWNrXCIsICgpID0+IHsgdGhpcy5fc2tpcCgpOyB9KTtcblx0fSxcblx0X3NldEVsRXZlbnRzKHN0ZXBFbCkge1xuXHRcdGxldCBldmVudFN0ZXAgPSB0aGlzLl9zdGVwLmV2ZW50O1xuXHRcdHN0ZXBFbC5mb2N1cygpO1xuXHRcdGlmKGV2ZW50U3RlcCkge1xuXHRcdFx0aWYoZXZlbnRTdGVwID09PSBcImVudGVyXCIpIHtcblx0XHRcdFx0ZXZlbnRTdGVwID0gXCJrZXlkb3duXCI7XG5cdFx0XHR9XG5cdFx0XHRpZih0aGlzLl9ldmVudE9iaikge1xuXHRcdFx0XHR3ZWJpeC5ldmVudFJlbW92ZSh0aGlzLl9ldmVudE9iaik7XG5cdFx0XHR9XG5cdFx0XHR0aGlzLl9ldmVudE9iaiA9IHdlYml4LmV2ZW50KHN0ZXBFbCwgZXZlbnRTdGVwLCAoZSkgPT4ge1xuXHRcdFx0XHRpZihldmVudFN0ZXAgPT0gZS50eXBlKSB7XG5cdFx0XHRcdFx0aWYoZS50eXBlID09PSBcImtleWRvd25cIiAmJiBlLmtleUNvZGUgIT09IDEzKSByZXR1cm47XG5cdFx0XHRcdFx0c3RlcEVsLmZvY3VzKCk7XG5cdFx0XHRcdFx0dGhpcy5fbmV4dChzdGVwRWwpO1xuXHRcdFx0XHR9XG5cdFx0XHR9KTtcblx0XHR9IGVsc2Uge1xuXHRcdFx0cmV0dXJuO1xuXHRcdH1cblx0fSxcblx0X25leHQoc3RlcEVsLCBhY3Rpb24pIHtcblx0XHRhY3Rpb24gPSBhY3Rpb24gfHwgXCJuZXh0XCI7XG5cdFx0aWYgKHRoaXMuX3N0ZXAubmV4dCAmJiBhY3Rpb24gPT09IFwibmV4dFwiIHx8IHRoaXMuX3N0ZXAucHJldmlvdXMgJiYgYWN0aW9uID09PSBcInByZXZpb3VzXCIpIHtcblx0XHRcdGxldCBwcm9taXNlID0gdGhpcy5fc3RlcFthY3Rpb25dKCk7XG5cdFx0XHRpZiAocHJvbWlzZSl7XG5cdFx0XHRcdHByb21pc2UucmVzb2x2ZSgpLnRoZW4oKCkgPT4ge1xuXHRcdFx0XHRcdHRoaXMuX25leHRTdGVwKHN0ZXBFbCwgYWN0aW9uKTtcblx0XHRcdFx0fSk7XG5cdFx0XHR9IGVsc2Uge1xuXHRcdFx0XHR0aGlzLl9uZXh0U3RlcChzdGVwRWwsIGFjdGlvbik7XG5cdFx0XHR9XG5cdFx0fSBlbHNlIHtcblx0XHRcdHRoaXMuX25leHRTdGVwKHN0ZXBFbCwgYWN0aW9uKTtcblx0XHR9XG5cdH0sXG5cdF9uZXh0U3RlcChzdGVwRWwsIGFjdGlvbikge1xuXHRcdGxldCBlbCA9IHRoaXMuX2dldEVsKHRoaXMuX3N0ZXAuZWwpO1xuXHRcdGVsLnN0eWxlLnBvaW50ZXJFdmVudHMgPSBcIlwiO1xuXHRcdGVsLnN0eWxlLnVzZXJTZWxlY3QgPSBcIlwiO1xuXHRcdGVsLmJsdXIoKTtcblx0XHRpZihhY3Rpb24gIT09IFwicHJldmlvdXNcIikge1xuXHRcdFx0dGhpcy5faSsrO1xuXHRcdFx0dGhpcy5fZHJhd1N0ZXBzKCk7XG5cdFx0XHR0aGlzLmNhbGxFdmVudChcIm9uTmV4dFwiLCBbdGhpcy5faSsxXSk7XG5cdFx0fVxuXHRcdGlmKGFjdGlvbiA9PT0gXCJwcmV2aW91c1wiKSB7XG5cdFx0XHR0aGlzLmNhbGxFdmVudChcIm9uUHJldmlvdXNcIiwgW3RoaXMuX2ldKTtcblx0XHRcdHRoaXMuX3JlZnJlc2godGhpcy5faS0tLCBmYWxzZSk7XG5cdFx0fVxuXHR9LFxuXHRfc2tpcCgpIHtcblx0XHRpZiAodGhpcy5faSA9PT0gLTEpIHJldHVybjtcblx0XHRpZih0aGlzLl9ldmVudE9iaikge1xuXHRcdFx0d2ViaXguZXZlbnRSZW1vdmUodGhpcy5fZXZlbnRPYmopO1xuXHRcdFx0ZGVsZXRlIHRoaXMuX2V2ZW50T2JqO1xuXHRcdH1cblx0XHRpZih0aGlzLl9ldmVudFJlc2l6ZSkge1xuXHRcdFx0d2ViaXguZGV0YWNoRXZlbnQodGhpcy5fZXZlbnRSZXNpemUpO1xuXHRcdFx0ZGVsZXRlIHRoaXMuX2V2ZW50UmVzaXplO1xuXHRcdH1cblx0XHR0aGlzLmNhbGxFdmVudChcIm9uU2tpcFwiLCBbdGhpcy5faSsxXSk7XG5cdFx0dGhpcy5oaWRlKCk7XG5cdFx0dGhpcy5fc2V0Qm9keUNsYXNzKFwicmVtb3ZlXCIpO1xuXHRcdGlmKHRoaXMuX2kgPT09IHRoaXMuY29uZmlnLnN0ZXBzLmxlbmd0aCkge1xuXHRcdFx0dGhpcy5jYWxsRXZlbnQoXCJvbkVuZFwiLCBbdGhpcy5faSsxXSk7XG5cdFx0fVxuXHR9LFxuXHRfcmVmcmVzaChpLCBmaXJzdERyYXcpIHtcblx0XHRpZighdGhpcy5fZXZlbnRSZXNpemUpIHtcblx0XHRcdHRoaXMuX3NldFJlc2l6ZSgpO1xuXHRcdH1cblx0XHR0aGlzLl9pID0gaS0xO1xuXHRcdHRoaXMuX3NldEJvZHlDbGFzcygpO1xuXHRcdGlmKHRoaXMuX2hpbnQpIHtcblx0XHRcdGlmKHRoaXMuX2hpbnQucGFyZW50Tm9kZSlcblx0XHRcdFx0dGhpcy5faGludC5wYXJlbnROb2RlLnJlbW92ZUNoaWxkKHRoaXMuX2hpbnQpO1xuXHRcdFx0d2ViaXguaHRtbC5yZW1vdmVDc3ModGhpcy5nZXROb2RlKCksIFwid2ViaXhfaGludF9hbmltYXRlZFwiKTtcblx0XHR9XG5cdFx0dGhpcy5zaG93KCk7XG5cdFx0aWYoZmlyc3REcmF3KSB7XG5cdFx0XHRsZXQgc3ZnID0gdGhpcy4kdmlldy5xdWVyeVNlbGVjdG9yKFwic3ZnXCIpO1xuXHRcdFx0aWYgKHN2Zylcblx0XHRcdFx0c3ZnLnBhcmVudE5vZGUucmVtb3ZlQ2hpbGQoc3ZnKTtcblx0XHRcdHRoaXMuX2RyYXdTdGVwcygpO1xuXHRcdH0gZWxzZSB7XG5cdFx0XHR0aGlzLl9kcmF3U3RlcHMoXCJyZWZyZXNoXCIpO1xuXHRcdH1cblx0fSxcblx0c3RhcnQoKSB7XG5cdFx0dGhpcy5fcmVmcmVzaCgxLCB0cnVlKTtcblx0fSxcblx0ZW5kKCkge1xuXHRcdHRoaXMuX3NraXAoKTtcblx0fSxcblx0Z2V0Q3VycmVudFN0ZXAoKSB7XG5cdFx0cmV0dXJuIHRoaXMuX2krMTtcblx0fSxcblx0cmVzdW1lKHN0ZXBOdW1iZXIpIHtcblx0XHRpZih0aGlzLl9oaW50KXtcblx0XHRcdHN0ZXBOdW1iZXIgPSBzdGVwTnVtYmVyIHx8IDE7XG5cdFx0XHR0aGlzLl9yZWZyZXNoKHN0ZXBOdW1iZXIpO1xuXHRcdH1cblx0fSxcblx0Z2V0U3RlcHMoKSB7XG5cdFx0cmV0dXJuIHRoaXMuY29uZmlnLnN0ZXBzO1xuXHR9LFxuXHRzZXRTdGVwcyh2YWx1ZSkge1xuXHRcdHRoaXMuZGVmaW5lKFwic3RlcHNcIiwgdmFsdWUpO1xuXHR9XG59LCB3ZWJpeC51aS52aWV3LCB3ZWJpeC5FdmVudFN5c3RlbSk7XG5cblxuLy8gV0VCUEFDSyBGT09URVIgLy9cbi8vIC4vc291cmNlcy9oaW50LmpzIiwiZXhwb3J0IGxldCBiZSA9IHdlYml4LmkxOG4ubG9jYWxlc1tcImJlLUJZXCJdID0ge1xuXHRoaW50Ontcblx0XHRuZXh0OiBcItCd0LDRgdGC0YPQv9C90YtcIixcblx0XHRwcmV2OiBcItCf0LDQv9GP0YDRjdC00L3RllwiLFxuXHRcdGxhc3Q6IFwi0JrQsNC90LXRhiDQotGD0YDQsFwiXG5cdH1cbn07XG5cblxuLy8gV0VCUEFDSyBGT09URVIgLy9cbi8vIC4vc291cmNlcy9pMThuL2JlLmpzIiwiLypHZXJtYW4gKEdlcm1hbnkpIGxvY2FsZSovXG5leHBvcnQgbGV0IGRlID0gd2ViaXguaTE4bi5sb2NhbGVzW1wiZGUtREVcIl0gPSB7XG5cdGhpbnQ6e1xuXHRcdG5leHQ6IFwiTsOkY2hzdGVyXCIsXG5cdFx0cHJldjogXCJCaXNoZXJpZ2VcIixcblx0XHRsYXN0OiBcIkVuZGUgVG91clwiXG5cdH1cbn07XG5cblxuXG4vLyBXRUJQQUNLIEZPT1RFUiAvL1xuLy8gLi9zb3VyY2VzL2kxOG4vZGUuanMiLCJleHBvcnQgbGV0IGVuID0gd2ViaXguaTE4bi5sb2NhbGVzW1wiZW4tVVNcIl09e1xuXHRoaW50Ontcblx0XHRuZXh0OiBcIk5leHRcIixcblx0XHRwcmV2OiBcIlByZXZpb3VzXCIsXG5cdFx0bGFzdDogXCJFbmQgVG91clwiXG5cdH1cbn07XG5cblxuLy8gV0VCUEFDSyBGT09URVIgLy9cbi8vIC4vc291cmNlcy9pMThuL2VuLmpzIiwiLypTcGFuaXNoIChTcGFpbiwgSW50ZXJuYXRpb25hbCBTb3J0KSBsb2NhbGUqL1xuZXhwb3J0IGxldCBlcyA9IHdlYml4LmkxOG4ubG9jYWxlc1tcImVzLUVTXCJdID0ge1xuXHRoaW50Ontcblx0XHRuZXh0OiBcIlNpZ3VpZW50ZVwiLFxuXHRcdHByZXY6IFwiQW50ZXJpb3JcIixcblx0XHRsYXN0OiBcIkZpbiBkZSBWaWFqZVwiXG5cdH1cbn07XG5cblxuXG4vLyBXRUJQQUNLIEZPT1RFUiAvL1xuLy8gLi9zb3VyY2VzL2kxOG4vZXMuanMiLCJleHBvcnQgbGV0IGZyID0gd2ViaXguaTE4bi5sb2NhbGVzW1wiZnItRlJcIl09e1xuXHRoaW50Ontcblx0XHRuZXh0OiBcIlByb2NoYWluXCIsXG5cdFx0cHJldjogXCJQcsOpY8OpZGVudFwiLFxuXHRcdGxhc3Q6IFwiRW5kIFRvdXJcIlxuXHR9XG59O1xuXG5cbi8vIFdFQlBBQ0sgRk9PVEVSIC8vXG4vLyAuL3NvdXJjZXMvaTE4bi9mci5qcyIsIi8qSXRhbGlhbiAoSXRhbHkpIGxvY2FsZSovXG5leHBvcnQgbGV0IGl0ID0gd2ViaXguaTE4bi5sb2NhbGVzW1wiaXQtSVRcIl0gPSB7XG5cdGhpbnQ6e1xuXHRcdG5leHQ6IFwiU2VndWVudGVcIixcblx0XHRwcmV2OiBcIlByZWNlZGVudGVcIixcblx0XHRsYXN0OiBcIkVuZCBUb3VyXCJcblx0fVxufTtcblxuXG5cbi8vIFdFQlBBQ0sgRk9PVEVSIC8vXG4vLyAuL3NvdXJjZXMvaTE4bi9pdC5qcyIsImV4cG9ydCBsZXQgamEgPSB3ZWJpeC5pMThuLmxvY2FsZXNbXCJqYS1KUFwiXT17XG5cdGhpbnQ6e1xuXHRcdG5leHQ6IFwi5qyhXCIsXG5cdFx0cHJldjogXCLliY1cIixcblx0XHRsYXN0OiBcIue1guS6huODhOOCouODvFwiXG5cdH1cbn07XG5cblxuLy8gV0VCUEFDSyBGT09URVIgLy9cbi8vIC4vc291cmNlcy9pMThuL2phLmpzIiwiZXhwb3J0IGxldCBwdCA9IHdlYml4LmkxOG4ubG9jYWxlc1tcInB0LUJSXCJdID0ge1xuXHRoaW50Ontcblx0XHRuZXh0OiBcIlByw7N4aW1vXCIsXG5cdFx0cHJldjogXCJBbnRlcmlvclwiLFxuXHRcdGxhc3Q6IFwiRW5kIFRvdXJcIlxuXHR9XG59O1xuXG5cblxuLy8gV0VCUEFDSyBGT09URVIgLy9cbi8vIC4vc291cmNlcy9pMThuL3B0LmpzIiwiZXhwb3J0IGxldCBydSA9IHdlYml4LmkxOG4ubG9jYWxlc1tcInJ1LVJVXCJdPXtcblx0aGludDp7XG5cdFx0bmV4dDogXCLQodC70LXQtNGD0Y7RidC40LlcIixcblx0XHRwcmV2OiBcItCf0YDQtdC00YvQtNGD0YnQuNC5XCIsXG5cdFx0bGFzdDogXCLQmtC+0L3QtdGGINCi0YPRgNCwXCJcblx0fVxufTtcblxuXG4vLyBXRUJQQUNLIEZPT1RFUiAvL1xuLy8gLi9zb3VyY2VzL2kxOG4vcnUuanMiLCIvKkNoaW5lc2UgKFNpbXBsaWZpZWQsIFBSQykgbG9jYWxlKi9cbmV4cG9ydCBsZXQgemggPSB3ZWJpeC5pMThuLmxvY2FsZXNbXCJ6aC1DTlwiXSA9IHtcblx0aGludDp7XG5cdFx0bmV4dDogXCLkuIvkuIDkuKpcIixcblx0XHRwcmV2OiBcIuS7peWJjVwiLFxuXHRcdGxhc3Q6IFwi57uT5p2f5beh6KeGXCJcblx0fVxufTtcblxuXG5cbi8vIFdFQlBBQ0sgRk9PVEVSIC8vXG4vLyAuL3NvdXJjZXMvaTE4bi96aC5qcyJdLCJzb3VyY2VSb290IjoiIn0=