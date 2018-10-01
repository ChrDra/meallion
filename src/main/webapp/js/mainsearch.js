/*
 * Separat js file to make sure, it is integrated after tagify and all dependencies are integrated
 */

var input = document.querySelector('input[name=tags]');

function onAddTag(e){
    
}

tagify = new Tagify( input, {
    duplicates: true,
    whitelist: ['veggie', '5min'],
    callbacks: {
        add : onAddTag // calls an imaginary "onAddTag" function when a tag is added
    }
});

// listen to custom tags' events such as 'add' or 'remove'
tagify.on('remove', function(e){
    console.log(e, e.detail);
});

tagify.on('add', function(e){
    tags = $("#tags_input").serialize();
    trigger_selection(startWaterfall);
});