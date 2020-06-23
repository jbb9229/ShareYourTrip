function MyCustomUploadAdapterPlugin( editor ) {
  editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
    // Configure the URL to the upload script in your back-end here!
    return new MyUploadAdapter(loader);
  };
}

class MyUploadAdapter {
  constructor(loader) {
    this.loader = loader;
  }

  upload() {
    this.loader.file
    .then(file => new Promise((resolve, reject) => {
      this._initRequest();
      this._initListeners(resolve, reject, file);
      this._sendRequest(file);
    }));

    return this.loader.file;
  }

  abort() {
    if (this.xhr) {
      this.xhr.abort();
    }
  }

  _initRequest() {
    const xhr = this.xhr = new XMLHttpRequest();

    xhr.open('POST', 'http://localhost:8080/api/posts/fileupload', true);
    xhr.responseType = 'json';
  }

  _initListeners(resolve, reject, file) {
    const xhr = this.xhr;
    const loader = this.loader;
    const genericErrorText = `Couldn't upload file: ${ file.name }.`;

    xhr.addEventListener('error', () => reject(genericErrorText));
    xhr.addEventListener('abort', () => reject());
    xhr.addEventListener('load', () => {
      const response = xhr.response;

      $('.ck-widget_selected img').attr("src", response.imageUrl);

      if (!response || response.error) {
        return reject(response && response.error ? response.error.message : genericErrorText);
      }

      resolve({
        default: response.url
      });
    });

    if (xhr.upload) {
      xhr.upload.addEventListener('progress', evt => {
        if (evt.lengthComputable) {
          loader.uploadTotal = evt.total;
          loader.uploaded = evt.loaded;
        }
      });
    }
  }

  _sendRequest(file) {
    const data = new FormData();

    data.append('upload', file);

    this.xhr.send(data);
  }

}

ClassicEditor
.create(document.querySelector('#editor'), {
  extraPlugins: [MyCustomUploadAdapterPlugin],
})
.catch(error => {
  console.log(error);
});

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
      content : $('.ck-content').html()
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