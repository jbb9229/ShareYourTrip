ClassicEditor
.create( document.querySelector( '#editor' ), {
  toolbar: [ "Heading", "|",
    "FontBackgroundColor", "FontColor", "FontSize", "|",
    "Bold", "Italic", "Underline", "Strikethrough", "Highlight", "|",
    "TodoList", "BulletedList", "NumberedList", "|",'alignment',
    "Indent", "Outdent", "|","Image", "ImageCaption", "ImageResize", "ImageStyle", "ImageToolbar", "ImageUpload", "SpecialCharacters", "Link", "BlockQuote", "Code", "CodeBlock", "InsertTable", "MediaEmbed", "exportPdf", "|",
    "Essentials", "IndentBlock", "Paragraph", "PasteFromOffice", "HorizontalLine", "PageBreak",
    "Table", "TableToolbar", "TextTransformation", "WordCount", "Autoformat"],
  cloudServices: {
    tokenUrl: 'https://example.com/cs-token-endpoint',
    uploadUrl: 'https://your-organization-id.cke-cs.com/easyimage/upload/'
  },
  alignment: {
    options: [ 'left', 'center', 'right' ]
  },
  heading: {
    options: [
      { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
      { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
      { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
      { model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3' },
      { model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4' }
    ]
  },
  codeBlock: {
    languages: [
      { language: 'plaintext', label: 'Plain text' },
      { language: 'css', label: 'CSS' },
      { language: 'html', label: 'HTML' },
      { language: 'javascript', label: 'JavaScript', class: 'js javascript js-code' },
      { language: 'java', label: 'JAVA' },
      { language: 'php', label: 'PHP', class: 'php-code' },
      { language: 'python', label: 'Python' },
      { language: 'c', label: 'C' },
      { language: 'cs', label: 'C#' },
      { language: 'cpp', label: 'C++' },
      { language: 'diff', label: 'Diff' },
      { language: 'ruby', label: 'Ruby' },
      { language: 'typescript', label: 'TypeScript' },
      { language: 'xml', label: 'XML' }
    ]
  }
})
.catch( error => {
  console.log( error );
});