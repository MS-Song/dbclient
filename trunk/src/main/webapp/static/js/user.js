var changePasswordForm=function() {
    var html='<form name="changePasswordTable" id="changePasswordTable"><table class="table-list">'
            +'<tr>'
            +'<th>현재비밀번호</th>'
            +'<td><input type="password" name="current_password" id="current_password" /></td>'
            +'</tr>'
            +'<tr>'    
            +'<th>새 비밀번호</th>'
            +'<td><input type="password" name="new_password" id="new_password" /></td>'
            +'</tr>'
            +'<tr>'
            +'<th>새 비밀번호 확인</th>'
            +'<td><input type="password" name="new_password_confirm" id="new_password_confirm" /></td>'    
            +'</tr>'
            +'</table>'
            +'<input type="hidden" name="mode" value="changePW" />'
            +'</form>';
    $( "#changePasswordPopup" ).html(html);
};

var userGroupList = new Array('root' , 'user');

var userManageForm = function() {
        $.post("/controller/user/MemberManageController.php", {"mode": "list","time" : function() { return new Date().getTime(); }}, function(data) {
            var html = '<form name="userManageTable" id="userManageTable"><table class="table-list">'
                + '<thead>'
                + '<tr>'
                + '<th>account'
                + '<input type="hidden" name="mode" value="save">'
                + '</th>'
                + '<th>username</th>'
                + '<th>password</th>'        
                + '<th>group</th>'
                + '<th><input type="button" id="addUser" value="추가"></th>'
                + '</tr>'
                + '</thead>'
                + '<tbody id="rowUserBody">';
            

            var subHtml = '';
            var usergroup = new Array();
            usergroup[0] = '0';
            usergroup[1] = '1';
            
            try {
                data = eval(data);
            } catch(e) {
                alert('데이터 변환중 오류가 발생하였습니다.');
                console.log(data);
            }
            
            for (var i = 0; i < data.length; i++) {
                
                var account = data[i]['account'];
                var username = data[i]['name'];
                var group = data[i]['group'];
                
                var usergroupText = '<select name="group[]">';
                for (var j = 0; j < usergroup.length; j++) {
                    var selected = '';
                    console.log(group + ' , ' + usergroup[j]);
                    if (group == usergroup[j]) {
                        selected = 'selected';
                    }
                    usergroupText += '<option value="' + usergroup[j] + '" ' + selected + '>' + userGroupList[j] + '</option>';
                }
                usergroupText += '</select>';                
                subHtml += '<tr class="subHtml">';
                subHtml += '<td><input type="text"     name="account[]" 		value="' + account + '"></td>';
                subHtml += '<td><input type="text"     name="username[]" 		value="' + username + '"></td>';
                subHtml += '<td><input type="text"     name="password[]" 		value=""></td>';                
                subHtml += '<td>' + usergroupText + '</td>';
                subHtml += '<td><input type="button"   name="delete" 		value="삭제"></td>';
                subHtml += '</tr>';

            }
                html += subHtml;
                html += '<tbody></table></form>';            
                $("#userPopup").html(html);            
            
             $("#addUser").click(function(event) {
                $('#rowUserBody').append(userManageGetSubHTML());
            });
            
            $(document).on("click", "#rowUserBody input[name=delete]", function(event) {
                var targetRow = $(this).parents('.subHtml');
                var inputs = targetRow.find('input[type!=button]');
                var hasValue = false;
                for (var i = 0; i < inputs.length; i++) {
                    if ($(inputs[i]).val() != '') {
                        hasValue = true;
                        break;
                    }
                }
                if (hasValue && !confirm('작성된 내용이 있습니다. 삭제하시겠습니까?')) {
                    return;
                }
                targetRow.remove();
            });

            
        });
};

var userManageGetSubHTML = function() {
    var usergroup = new Array();
    usergroup[0] = '0';
    usergroup[1] = '1';
    var usergroupText = '<select name="new_group[]">';
    for (var i = 0; i < usergroup.length; i++) {
        usergroupText += '<option value="' + usergroup[i] + '">' + userGroupList[i] + '</option>';
    }
    usergroupText += '</select>';

    var subHtml = '';
    
    subHtml += '<tr class="subHtml jq-UserAdd">';
    subHtml += '<td><input type="text"     name="new_account[]" 		value=""></td>';
    subHtml += '<td><input type="text"     name="new_username[]" 		value=""></td>';
    subHtml += '<td><input type="text"     name="new_password[]" 		value=""></td>';                
    subHtml += '<td>' + usergroupText + '</td>';
    subHtml += '<td><input type="button"   name="delete" 		value="삭제"></td>';
    subHtml += '</tr>';

    return subHtml;
};

$(document).ready(function(){
    $("#userPopup").dialog({
        autoOpen: false,
        width: 900,
        height: 600,
        modal: true,
        title: '사용자 관리',
        buttons: [
            {
                text: "저장",
                click: function() {
                    if (confirm("변경 내용을 저장 하시겠습니까?")) {
                        var status=0;

                        var invalid = false;
                        $(".jq-UserAdd input[type='text']").each( function () {
                            if( $(this).val() == '') {
                                invalid = true;
                            }
                        });

                        if( invalid == true ) {
                            alert('추가되는 항목은 모두 입력되어야 합니다.');
                            return;
                        }


                        $.post("/controller/user/MemberManageController.php", $("#userManageTable").serializeArray(), function(data) {
                            status = data.status;

                        }).done(function (data) {
                            if( status != 'error') {
                                $("#userPopup").dialog("close");
                            }
                        }).fail(function(xhr,txt) {
                            alert(txt);
                            console.log(txt);
                        });
                    };
                }
            },
            {
                text: "취소",
                click: function() {
                    if (confirm("취소 하시겠습니까?")) {
                        $(this).dialog("close");
                    }
                    ;
                }
            }
        ],
        open: function(event, ui) {
            $(this).dialog("option", "height", $(window).height());
        }
    });   
        
	$("#changePasswordPopup").dialog({
	    autoOpen: false,
	    width: 400,
	    height: 220,
	    modal: true,
	    title: '비밀번호변경',
	    buttons: [
	        {
	            text: "저장",
	            click: function() {
	                if (confirm("변경 내용을 저장 하시겠습니까?")) {
	                    
	                    if( $("#current_password").val() == "" ) {
	                        alert("현재 비밀번호를 입력해주세요");
	                        return false;
	                    }
	
	                    if( $("#new_password").val() == '') {
	                        alert('새 비밀번호를 입력해주세요');
	                        return false;
	                    }
	                    
	                    if( $("#new_password_confirm").val() == '' ) {
	                        alert('새 비밀번호 확인을 입력해주세요');
	                        return false;
	                    }
	                    
	                    if( $("#new_password").val() != $("#new_password_confirm").val()) {
	                        alert("새 비밀번호와 새 비밀번호 확인 값이 다릅니다.");
	                        return false;
	                    }
	
	                    $.post("/controller/user/MemberManageController.php", $("#changePasswordTable").serializeArray(), function(data) {
	
	                        console.log(data);                            
	                        alert(data.message);
	                        status = data.status;
	
	                    }).done(function () {
	                        if( status != 'error') {
	                            $("#changePasswordPopup").dialog("close");
	                        }
	                    });
	
	                };
	
	            }
	        },
	        {
	            text: "취소",
	            click: function() {
	                if (confirm("취소 하시겠습니까?")) {
	                    $(this).dialog("close");
	                }
	                ;
	            }
	        }
	    ],
	    open: function(event, ui) {
	        $(this).dialog("option", "height",220);
	    }
	});           	
	
    $("#userManager").click(function(event) {
        $("#userPopup").dialog("open");     
        event.preventDefault();
        userManageForm();         
    });
});