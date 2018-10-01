
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
  placeholder: 'Füge hier die Schritte und dazugehörigen Bilder ein',
  theme: 'snow'  // or 'bubble'
});


