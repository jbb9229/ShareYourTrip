var main = {
  init : function() {
    var _this = this;
    $('#btn-save').on('click', function() {
      _this.save();
    });
    $('#btn-update').on('click', function() {
      _this.update();
    })
    $('#btn-delete').on('click', function() {
      _this.delete();
    })
  },
  save : function() {
    var data = {
      title : $('#title').val(),
      author : $('#author').val(),
      content : editor.getData()
    };


    $.ajax({
      type: 'POST',
      url: '/api/posts/save',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    })
    .done(function() {
      alert('글이 등록되었습니다.');
      window.location.href = '/posts/list';
    })
    .fail(function(error) {
      alert(JSON.stringify(error));
    });
  },
  update : function() {
    var data = {
      title: $('#title').val(),
      content: $('#content').val()
    };

    var postId = $('#postId').val();

    $.ajax({
      type: 'PUT',
      url: '/api/posts/update/' + postId,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(data)
    })
    .done(function() {
      alert('글이 수정되었습니다.');
      window.location.href="/posts/list";
    })
    .fail(function(error) {
      alert(JSON.stringify(error))
    });
  },
  delete : function() {
    var postId = $('#postId').val();

    $.ajax({
      type: 'DELETE',
      url: '/api/posts/delete/' + postId,
      dataType: 'json',
      contentType: 'application/json; charset=utf-8'
    })
    .done(function() {
      alert('글이 삭제되었습니다.');
      window.location.href='/posts/list';
    })
    .fail(function(error) {
      alert(JSON.stringify(error));
    })
  }
};

main.init();