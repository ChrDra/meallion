
var editor = new Quill('.editor');  // First matching element will be used

var container = $('.editor-container').get(0);

var quill = new Quill('#editor-container', {
  modules: {
    toolbar: [
      [{ header: [1, 2, false] }],
      ['bold', 'italic', 'underline'],
      []
    ]
  },
  placeholder: 'F&uuml;ge hier die Schritte und dazugeh&ouml;rigen Bilder ein',
  theme: 'snow'  // or 'bubble'
});


