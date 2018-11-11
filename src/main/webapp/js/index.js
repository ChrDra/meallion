
$(".main_search_input_button").click(function(){
    $("#search_currently_disabled_label").slideToggle(200,function(){
    });
});

$("#fileuploadform").hide();

$( "#uploadbutton" ).click(function() {
  $("#selectfile" ).click();
});

$("#fileuploadform").change(function() {$("#submitfile").click();  });



/* ajax asynchronus solution
$("#fileuploadform").on('change', '#selectfile', function() {
    alert("wie oft");
    var data = new FormData(); // das ist unser Daten-Objekt ...
    data.append('file', this.files[0]); // ... an die wir unsere Datei anh&auml;ngen
    $.ajax({
    url: 'Upload', // Wohin soll die Datei geschickt werden?
    data: data,          // Das ist unser Datenobjekt.
    type: 'POST',         // HTTP-Methode, hier: POST
    processData: false,
    async: true,
    contentType: false,
    // und wenn alles erfolgreich verlaufen ist, schreibe eine Meldung
    // in das Response-Div
    success: function(re) {

        }
   });

});

*/



