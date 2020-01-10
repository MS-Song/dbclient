
/**
 * web form 을 생성 한다.
 * @param param
 * @param isDescription
 * @param isDisable
*/
let createWebForm = function(param,isDescription=false,isDisable=false) {

    // 제외 되는 파라메터 인 경우 null을 리턴한다.
    if ($.inArray(param.name, excludeParams) != -1) {
        return null;
    }

    // view 객체
    let viewElement = {};

    // 필수값 여부를 추가 한다.
    let required = param.required ? "<span style='color: red;'>*</span> " : "<span style='color: black;'>*</span> ";
    // label을 명칭과 설명으로 나눈다.

    let discriptions;
    if (undefined == param.description) {
        discriptions = [param.name, ""];
    } else {
        discriptions = param.description.split("||");
    }
    // 왼쪽 설명 (필드 명)
    let leftDescription = required + discriptions[0];
    // tooltip 에 보여줄 상세한 설명
    let tooltipDescription = isDescription && undefined != discriptions[1] ? discriptions[1] : "";


    if (param.enum != undefined) {
        viewElement = {
            view: "combo",
            label: leftDescription,
            labelWidth: 150,
            adjust: true,
            options: param.enum,
            name: param.name,
            disabled: isDisable
        };
    } else if (param.type == "boolean") {
        viewElement = {
            view: "combo",
            label: leftDescription,
            labelWidth: 150,
            adjust: true,
            options: ["true", "false"],
            name: param.name,
            disabled: isDisable
        };
    } else if (param.type == "select") {
        viewElement = {
            view: "select",
            label: leftDescription,
            labelWidth: 150,
            adjust: true,
            options: param.values,
            name: param.name,
            disabled: isDisable
        };
    } else if (param.name.toLowerCase().indexOf("database") >= 0) {
        let setDatabaseOptions = isDisable ? useAllDatabaseOptions : useDatabaseOptions;
        viewElement = {
            view: "select",
            label: leftDescription,
            labelWidth: 150,
            adjust: true,
            options: setDatabaseOptions,
            name: 'databaseId',
            disabled: isDisable
        };
    } else if (param.name.toLowerCase().indexOf("member") >= 0) {
        viewElement = {
            view: "text",
            label: leftDescription,
            labelWidth: 150,
            adjust: true,
            name: param.name,
            readonly: true,
            disabled: isDisable,
            on: {
                "onItemClick": function (view, e) { // enter 검색 추가
                    if (undefined != param.collectionFormat
                        && param.collectionFormat == "multi") {
                        member_list_popup(view, true);
                    } else {
                        member_list_popup(view, false);
                    }
                }
            }
        };
    } else if (param.name.toLowerCase().indexOf("date") >= 0 || param.type == 'DATE') {
        viewElement = {
            view: "datepicker",
            label: leftDescription,
            labelWidth: 150,
            adjust: true,
            name: param.name,
            //format:"%d %M %Y at %H:%i",
            format: "%Y-%m-%d",
            stringResult: true,
            disabled: isDisable,
            //on:{"onChange":function(before,after){
            //	console.log(this.getValue());
            //}}
        };
    } else if (param.name.toLowerCase().indexOf("runsql") >= 0) {
        viewElement = {
            view: "textarea",
            label: leftDescription,
            labelWidth: 150,
            height: 200,
            adjust: true,
            name: param.name,
            readonly: false,
            disabled: isDisable,
        };
    } else if (param.type == "hidden") { // hidden
        viewElement = {
            view: "text",
            type: "hidden",
            height: 0,
            name: param.name,
            value: param.value
        };
    } else {
        viewElement = {
            view: "text",
            label: leftDescription,
            labelWidth: 150,
            adjust: true,
            name: param.name,
            disabled: isDisable
        };
    }


if("" != tooltipDescription){
        viewElement['tooltip'] = tooltipDescription;
    }
    return viewElement;
};