$(document).ready(function() {
    console.log("url for ajax: ", window.location.href);
    $.ajax({
        url: window.location.href + 'spells',
        type: "GET",
        success: function(data) {
            // transform JSON file to js Object
            var obj = $.parseJSON(data);

            // take the array inside the Object and assign it to js array
            var words = obj.spells;

            // document.getElementById('results').innerHTML = "<a href='' >" + obj + "</a>"; --- JS

            $.each(words, function(key, value) {                                          // --- jQuery
                $('#results-ul').append("<li><a href='/spells/" + value + "' id='" + (key + 1) + "' >" + value + "</a></li>");
            });
        }
    });
});

function updateResults() {
    var input, filter, ul, li, a, i, txtValue;
    input = document.getElementById("search-bar-input");
    filter = input.value.toUpperCase();
    ul = document.getElementById("results-ul");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("a")[0];
        txtValue = a.textContent || a.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
}