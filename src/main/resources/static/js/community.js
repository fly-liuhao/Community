/*添加评论*/
function addComment(commentParentId, commentType) {
    var param = commentType == 1
        ? {
            parentId: commentParentId,
            content: $("#question_comment_content").val(),
            type: commentType
        }
        : {
            parentId: commentParentId,
            content: $("#second_level_comment_" + commentParentId + "_content").val(),
            type: commentType
        }
    // js比较特殊，如果有值直接返回true，没值返回false
    if (!param.content) {
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

/* 获取二级回复 */
function getSecondLevelComment(commentParentId) {
    console.log(commentParentId);
    // 获取评论区dom元素
    var secondLevelCommentDom = $("#comment_" + commentParentId);
    // 获取评论图标的dom元素
    var commentIconDom = secondLevelCommentDom.prev().children(".glyphicon-comment");
    secondLevelCommentDom.toggleClass("in");
    commentIconDom.toggleClass("active");

    if (secondLevelCommentDom.hasClass("in")) {
        $.getJSON("/getComment?parentId=" + commentParentId, function (data) {
            $(data.map.data.reverse()).each(function (index, comment) {
                // 拼接html
                var mediaLiftElement = $("<div/>", {
                    "class": "media-left"
                }).append($("<a/>", {
                    "href": "#"
                }).append($("<img/>", {
                    "class": "media-object img-rounded",
                    "src": comment.user.avatarUrl,
                    "alt": "jpg"
                })));

                var mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<h5/>", {
                    "class": "media-heading"
                }).append($("<span/>", {
                    "html": comment.user.name
                })).append($("<span/>", {
                    "class": "text-desc",
                    "html": comment.user.bio
                }))).append($("<p/>", {
                    "html": comment.content
                })).append($("<span/>", {
                    "class": "pull-right comment-time",
                    "html": moment(comment.gmtCreate).fromNow()
                    // "html": comment.pubtime
                }));

                var mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLiftElement)
                    .append(mediaBodyElement);
                if (index == 1) {
                    mediaElement.append($("<hr/>"));
                }


                // 将拼接好的html拼接到前端页面中
                secondLevelCommentDom.prepend(mediaElement);
            });
        });
    }
}


/*index.html页面*/
function isClose() {
    var isclose = window.localStorage.getItem("isclose");
    if (isclose) {
        window.close();
        window.localStorage.removeItem("isclose");
    }
}

/* 打开发布问题的标签库 */
function showTags() {
    $("#tag_lib").show();
}

/* 添加标签 */
function addTag(tag) {
    // 获取已输入的标签
    var original = $("#tag").val();

    // 判断新加入标签是否已存在（不包含返回-1）
    if (original.indexOf(tag) == -1) {
        if (original) {
            $("#tag").val(original + ', ' + tag);
        } else {
            $("#tag").val(tag);
        }
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
