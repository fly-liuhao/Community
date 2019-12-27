/*添加评论*/
function addComment(comment_type) {
    var param = comment_type == 1
        ? {
            parentId: $('#question_id').val(),
            content: $('#comment').val(),
            type: comment_type
        }
        : {
            parentId: $('#comment_id').val(),
            content: $('#comment').val(),
            type: comment_type
        }
        // js比较特殊，如果有值直接返回true，没值返回false
        if(!param.content){
            alert("回复的内容不能为空！");
            return;
        }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        sync: true,
        data: JSON.stringify(param),
        dataType: "json",
        success: function (result) {
            if (result.code == 200) {
                window.location.reload();
                $('#comment_area').hide();
            } else {
                if (result.code == 2003) {
                    var isAccepted = confirm(result.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=78111a4363ecd4d6b210&redirect_uri=http://localhost:8080/callback&scope=user&state=randomString");
                        window.localStorage.setItem("isclose", true);
                    }
                } else {
                    alert(result.message)
                }
            }
        }
    });
}

/*index.html页面*/
function isClose() {
    var isclose = window.localStorage.getItem("isclose");
    if (isclose) {
        window.close();
        window.localStorage.removeItem("isclose");
    }
}


/* 时间戳转换成几年前、几天前、几小时前等格式 */
function timeFormat(time) {
    if (time > 0) {
        var result;
        time = parseInt(time) * 1000;
        var minute = 1000 * 60;
        var hour = minute * 60;
        var day = hour * 24;
        var month = day * 30;
        var now = new Date().getTime();
        var diffValue = now - time;
        if (diffValue < 0) {
            return;
        }
        var monthC = diffValue / month;
        var weekC = diffValue / (7 * day);
        var dayC = diffValue / day;
        var hourC = diffValue / hour;
        var minC = diffValue / minute;
        if (monthC >= 1) {
            if (monthC <= 12) {
                result = "" + parseInt(monthC) + "月前";
            } else {
                result = "" + parseInt(monthC / 12) + "年前";
            }
        } else if (weekC >= 1) {
            result = "" + parseInt(weekC) + "周前";
        } else if (dayC >= 1) {
            result = "" + parseInt(dayC) + "天前";
        } else if (hourC >= 1) {
            result = "" + parseInt(hourC) + "小时前";
        } else if (minC >= 1) {
            result = "" + parseInt(minC) + "分钟前";
        } else {
            result = "刚刚";
        }
        return result;
    } else {
        return '';
    }
}
