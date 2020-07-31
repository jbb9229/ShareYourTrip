var myEditor;

class UploadAdapter {
  constructor(loader) {
    this.loader = loader;
  }

  upload() {
    return this.loader.file
    .then(file => new Promise((resolve, reject) => {
      this._initRequest();
      this._initListeners(resolve, reject, file);
      this._sendRequest(file);
      console.log("1");
    }));
  }

  abort() {
    if (this.xhr) {
      this.xhr.abort();
      console.log("1");
    }
  }

  _initRequest() {
    const xhr = this.xhr = new XMLHttpRequest();
    xhr.open('POST', '/api/posts/fileupload', true);
    xhr.responseType = 'json';
    console.log("1");
  }

  _initListeners(resolve, reject, file) {
    const xhr = this.xhr;
    const loader = this.loader;
    const genericErrorText = `Couldn't upload file: ${file.name}.`;
    xhr.addEventListener('error', () => reject(genericErrorText));
    xhr.addEventListener('abort', () => reject());
    xhr.addEventListener('load', () => {
      const response = xhr.response;
      if (response.imageUrl.length != 0) {
        console.log(this);
      }
      resolve({default: response.imageUrl});
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
    console.log(data);
    this.xhr.send(data);
  }

}

function MyCustomUploadAdapterPlugin(editor) {
  editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
    return new UploadAdapter(loader);
  };
}

ClassicEditor
.create(document.querySelector('#editor'), {
  extraPlugins: [ MyCustomUploadAdapterPlugin ],
  toolbar      : ["Heading", "|",
    "FontBackgroundColor", "FontColor", "FontSize", "|",
    "Bold", "Italic", "Underline", "Strikethrough", "Highlight", "|",
    "TodoList", "BulletedList", "NumberedList", "|", 'alignment',
    "Indent", "Outdent", "|", "Image", "ImageCaption", "ImageResize",
    "ImageStyle", "ImageToolbar", "ImageUpload", "SpecialCharacters", "Link",
    "BlockQuote", "Code", "CodeBlock", "InsertTable", "MediaEmbed", "exportPdf",
    "|",
    "Essentials", "IndentBlock", "Paragraph", "PasteFromOffice",
    "HorizontalLine", "PageBreak",
    "Table", "TableToolbar", "TextTransformation", "WordCount", "Autoformat"],
  cloudServices: {
    tokenUrl : 'https:example.com/cs-token-endpoint',
    uploadUrl: 'https:your-organization-id.cke-cs.com/easyimage/upload/'
  },
  alignment    : {
    options: ['left', 'center', 'right']
  },
  heading      : {
    options: [
      {model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph'},
      {
        model: 'heading1',
        view : 'h1',
        title: 'Heading 1',
        class: 'ck-heading_heading1'
      },
      {
        model: 'heading2',
        view : 'h2',
        title: 'Heading 2',
        class: 'ck-heading_heading2'
      },
      {
        model: 'heading3',
        view : 'h3',
        title: 'Heading 3',
        class: 'ck-heading_heading3'
      },
      {
        model: 'heading4',
        view : 'h4',
        title: 'Heading 4',
        class: 'ck-heading_heading4'
      }
    ]
  },
  codeBlock    : {
    languages: [
      {language: 'plaintext', label: 'Plain text'},
      {language: 'css', label: 'CSS'},
      {language: 'html', label: 'HTML'},
      {
        language: 'javascript',
        label   : 'JavaScript',
        class   : 'js javascript js-code'
      },
      {language: 'java', label: 'JAVA'},
      {language: 'php', label: 'PHP', class: 'php-code'},
      {language: 'python', label: 'Python'},
      {language: 'c', label: 'C'},
      {language: 'cs', label: 'C#'},
      {language: 'cpp', label: 'C++'},
      {language: 'diff', label: 'Diff'},
      {language: 'ruby', label: 'Ruby'},
      {language: 'typescript', label: 'TypeScript'},
      {language: 'xml', label: 'XML'}
    ]
  },
  mediaEmbed: {
    previewsInData: true,
  }
})
.then( editor => {
  myEditor = editor;
})
.catch(error => {
  console.log(error);
});