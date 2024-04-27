document.addEventListener("DOMContentLoaded", function () {
    var signUpForm = document.getElementById("signUp");
    signUpForm.onsubmit = function (event) {
        event.preventDefault();

        var formData = new FormData(signUpForm);
        var data = {};
        formData.forEach(function (value, key) {
            data[key] = value;
        });
        var json = JSON.stringify(data);

        fetch("/api/v1/auth/sign-up", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: json
        }).then(response => {
            if (response.ok) {
                window.location.href = "/signin.html";
            } else {
                response.text().then(text => {
                    throw new Error(text)
                });
            }
        }).catch(error => {
            console.error('Error during sign up:', error);
        });
    };
});