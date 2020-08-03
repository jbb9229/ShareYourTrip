var main = {
  init  : function () {
    var _this = this;
    $('#btn-save').on('click', function () {
      _this.save();
    });
    $('#btn-update').on('click', function () {
      _this.update();
    })
    $('#btn-delete').on('click', function () {
      _this.delete();
    })
  },
  save  : function () {
    var data = {
      title   : $('#title').val(),
      authorId: $('#authorId').val(),
      author  : $('#author').val(),
      content : myEditor.getData()
    };

    if (validateTitle(title.value)) {
      alert('제목은 필수 입력입니다.');
    } else {
      $.ajax({
        type       : 'POST',
        url        : '/api/posts/save',
        dataType   : 'json',
        contentType: 'application/json; charset=utf-8',
        data       : JSON.stringify(data)
      })
      .done(function () {
        alert('글이 등록되었습니다.');
        window.location.href = '/posts/list';
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
    }
  },
  update: function () {
    var data = {
      title  : $('#title').val(),
      content: myEditor.getData()
    };

    var postId = $('#postId').val();

    if (validateTitle(title.value)) {
      alert('제목은 필수 입력입니다.');
    } else {
      $.ajax({
        type       : 'PUT',
        url        : '/api/posts/update/' + postId,
        dataType   : 'json',
        contentType: 'application/json; charset=utf-8',
        data       : JSON.stringify(data)
      })
      .done(function () {
        alert('글이 수정되었습니다.');
        window.location.href = "/posts/detail/" + postId;
      })
      .fail(function (error) {
        alert(JSON.stringify(error))
      });
    }
  },
  delete: function () {
    var postId = $('#postId').val();

    $.ajax({
      type       : 'DELETE',
      url        : '/api/posts/delete/' + postId,
      dataType   : 'json',
      contentType: 'application/json; charset=utf-8'
    })
    .done(function () {
      alert('글이 삭제되었습니다.');
      window.location.href = '/posts/list';
    })
    .fail(function (error) {
      alert(JSON.stringify(error));
    })
  }
};

function validateTitle(title) {
  if (title.trim().length == 0) {
    return true;
  } else {
    return false;
  }
}

main.init();