document.addEventListener("DOMContentLoaded", function () {
    const signUpForm = document.getElementById("signUp");
    signUpForm.onsubmit = function (event) {
        event.preventDefault();

        let formData = new FormData(signUpForm);
        const isAdmin = document.getElementById('isAdmin');
        localStorage.setItem("isAdmin", isAdmin.checked);
        const data = {
            'isAdmin': localStorage.getItem("isAdmin")
        };
        formData.forEach(function (value, key) {
            data[key] = value;
        });
        const json = JSON.stringify(data);

        fetch("/api/v1/auth/sign-up", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: json
        }).then(response => {
            if (response.ok) {
                console.log("ok")
                window.location.href = "/sign-in";
            } else {
                console.log("throw")
                response.text().then(text => {
                    let exceptionLabel = document.getElementsByClassName("auth-exception");
                    exceptionLabel[0].innerHTML = text;
                });
            }
        }).catch(error => {
            console.error('Error during sign up:', error);
        });
    };
});