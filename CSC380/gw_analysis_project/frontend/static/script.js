// event listener for upload button, trigger file input to open file selection
document.getElementById('uploadTextFile').addEventListener('click', function() {
    document.getElementById('fileUpload').click();
});

// when file is selected via the file input, trigger file upload
document.getElementById('fileUpload').addEventListener('change', function(e) {
    var file = e.target.files[0];  // retrieve file from file input
    var formData = new FormData();  // create new FormData obj. to hold file data for sending
    formData.append('file', file);  // append selected file to FormData object under key 'file'

    // show loading image
    document.getElementById('loadingImage').style.display = 'block';

    // send POST request to server with file data to the /uploads endpoint
    fetch('/uploads', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json()) // parse JSON response from server
    .then(data => {

        // hide loading image
        document.getElementById('loadingImage').style.display = 'none';

        // check if server response includes pdf key, update PDF viewer & download
        if(data.pdf) {
            document.getElementById('pdfViewer').src = `/PDF/${data.pdf}`;  // set source of PDF viewer to uploaded PDF
            document.getElementById('downloadPDF').setAttribute('data-pdf', data.pdf);  // set data attribute with PDF filename for later use
            console.log('File uploaded and processed: ', data.pdf);
        } else if(data.error) {
            console.error('Error:', data.error);  // log errors returned from server
            alert(data.error);  // Show alert if there's an error
        }
    })
    .catch(error => {
        // hide loading image
        document.getElementById('loadingImage').style.display = 'none';
        console.error('Error:', error);  // catche & log errors during fetch operation
        alert('File could not be parsed');  // error if request itself fails
    });
});

// when clicking preview PDF button, fetch latest PDF filename from server
document.getElementById('viewPDF').addEventListener('click', function() {
    fetch('/get_latest_pdf')
        .then(response => response.json())  // parse JSON response
        .then(data => {
            if(data.pdf) {
                document.getElementById('pdfViewer').src = `/PDF/${data.pdf}`;  // update preview PDF to latest PDF
                document.getElementById('downloadPDF').setAttribute('data-pdf', data.pdf);  // update download link
            } else {
                console.error('No PDF available to preview');
            }
        })
        .catch(error => {
            console.error('Error fetching PDF:', error);  // catche & log errors when fetching latest PDF
        });
});

// when clicking download PDF button, open PDF in a new tab
document.getElementById('downloadPDF').addEventListener('click', function() {
    const pdfName = this.getAttribute('data-pdf');  // retrieve filename of the PDF from the data attribute
    if(pdfName) {
        window.open(`/PDF/${pdfName}`, '_blank');  // open PDF in a new tab
    } else {
        alert("Please upload and process a text file first.");
    }
});

