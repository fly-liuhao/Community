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
                alert(result.message)
            }
        }
    });
}