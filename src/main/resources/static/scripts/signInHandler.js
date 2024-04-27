document.addEventListener("DOMContentLoaded", function() {
    var signInForm = document.getElementById("signIn");
    signInForm.onsubmit = function(event) {
        event.preventDefault();

        var formData = new FormData(signInForm);
        var data = {};
        formData.forEach(function(value, key) {
            data[key] = value;
        });
        var json = JSON.stringify(data);

        fetch("/api/v1/auth/sign-in", {
            method: "POST",
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json',
            },
            body: json
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                response.text().then(text => { throw new Error(text) });
            }
        })
            .then(data => {
                console.log('Token details:', data);
                localStorage.setItem('token', data.token);
                window.location.href = "/index.html";
            })
            .catch(error => {
                console.error('Error during sign in:', error);
            });
    };
});