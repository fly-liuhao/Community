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

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        sync: true,
        data: JSON.stringify(param),
        dataType: "json",
        success: function (result) {
            if (result.code == 200) {
                console.log(result);
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