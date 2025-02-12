 src="https://code.jquery.com/jquery-3.6.0.min.js"
    $(document).ready(function() {
    window.addEventListener("load", function() {
        setTimeout(function() {
            // This hides the address bar:
            window.scrollTo(0, 1);
        }, 0);
    });

    var textArray = [
    "SpringBoot API",
    "REST API",
    "SpringBoot MVC",
    "MicroServices"
    ];

    var currentIndex = 0;
    var outputDiv = $("#output");

    function displayText() {
    var text = textArray[currentIndex];
    var textToShow = text.split(";");
    outputDiv.html(`<span class="system-text">System</span><span class="out-text">.out.</span><span class="println-text">println(${textToShow})</span>`);
    setTimeout(function() {
    outputDiv.html("");
    currentIndex = (currentIndex + 1) % textArray.length;
    displayText();
}, 1000); // Adjust the delay time as needed (in milliseconds)
}
    displayText();
});
    $(document).ready(function () {
    function fetchQuote() {
        $.ajax({
            url: '/api/quote', // URL to fetch quote data from the server
            type: 'GET',
            dataType: 'json',
            success: function (response) {
                var content = '<span style="color: #672a2a; font-family: Apple Chancery; font-family: URW Chancery L; background-color: rgba(121,126,131,0.3);">"' + response.content + '"<br>' + response.author + '</span>';
                $('#quoteContent').html(content);
            },
            error: function (xhr, status, error) {
                console.error('Error fetching quote:', error);
            }
        });
    }

    fetchQuote(); // Fetch quote on page load

    setInterval(function () {
    fetchQuote(); // Fetch quote every minute
}, 9000); // 60000 milliseconds = 1 minute
});

 $(document).ready(function() {
     $('#contactForm').submit(function (e) {
         e.preventDefault(); // Prevent the form from submitting normally

         // AJAX request
         $.ajax({
             url: '/api/success', // URL for the success endpoint
             type: 'GET', // Use GET method for a @GetMapping endpoint
             success: function (response) {
                 // Display success message in a dialog box
                 $('#dialog').html(response).dialog({
                     modal: true,
                     buttons: {
                         Ok: function() {
                             $(this).dialog('close');
                             $('html, body').animate({scrollTop: 0}, 'slow');
                             window.location.reload(); // Reload the page after success
                         }
                     }
                 });

                 // Reset the form
                 $('#contactForm')[0].reset();
             },
             error: function (xhr, status, error) {
                 // Handle error
                 console.error(xhr.responseText);
                 // Display error message in a dialog box
                 $('#dialog').html("An error occurred while submitting the form.").dialog({
                     modal: true,
                     buttons: {
                         Ok: function() {
                             $(this).dialog('close');
                         }
                     }
                 });
             }
         });
     });
 });
 $(document).ready(function(){
     $.ajax({
         url: "/api/about", // Assuming this endpoint returns the JSON data
         type: 'GET',
         dataType: 'json',
         success: function(data){
             var webDeveloperDescription = data.webDeveloperDescription;
             $('#about_me').html(webDeveloperDescription);

             var about = data.neo;
             $('#name').html(about);

             var about = data.specialized;
             $('#specialized').html(about);

             var about = data.aboutMe;
             $('#web').html(about);

             var about = data.qte;
             $('#qte').html(about);

             var about = data.instructions;
             $('#instructions').html(about);

             var about = data.neo;
             $('#name1').html(about);
         },
         error: function(xhr, status, error) {
             console.error("Error:", error);
         }
     });
 });

 $(document).ready(function(){
     $.ajax({
         url: "/api/ip-details",
         type: 'GET',
         success: function(data){
             var about = data;
             $('#ip').html(about);
         },
         error: function(xhr, status, error) {
             console.error("Error:", error);
         }
     });
 });
 // Fetch the quote data from the API when the page loads
 document.addEventListener("DOMContentLoaded", function () {
     fetchQuote();
 });

 function fetchQuote() {
     fetch('/api/quote')
         .then(response => {
             if (response.ok) {
                 return response.text();
             } else if (response.status === 404) {
                 document.getElementById('quoteContainer').textContent = "No quote found!";
                 document.getElementById('errorContainer').style.display = 'none';
             } else {
                 throw new Error("Unexpected error occurred.");
             }
         })
         .then(quote => {
             if (quote) {
                 document.getElementById('quoteContainer').textContent = quote;
                 document.getElementById('errorContainer').style.display = 'none';
             }
         })
         .catch(error => {
             document.getElementById('quoteContainer').style.display = 'none';
             document.getElementById('errorContainer').style.display = 'block';
         });
 }

 // $(document).ready(function() {
 //     $.ajax({
 //         url: "/api/experience",
 //         type: "GET",
 //         success: function(data) {
 //             console.log("Received data:", data);
 //             var list = '<ul>';
 //             $.each(data, function(index, item){
 //                 list += '<li>' + item + '</li>';
 //             });
 //             list += '</ul>';
 //             $('#list-services').html(list);
 //             console.log(list);
 //         },
 //         error: function(xhr, status, error) {
 //             console.error("Error fetching experience data: " + error);
 //         }
 //     });
 // });