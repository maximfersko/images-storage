document.addEventListener("DOMContentLoaded", () => {
    const signInForm = document.getElementById("signIn");
    signInForm.onsubmit = (event) => {
        event.preventDefault();

        const formData = new FormData(signInForm);
        let data = {};
        formData.forEach(function (value, key) {
            data[key] = value;
        });
        console.log(data)
        const json = JSON.stringify(data);
        fetch("/api/v1/auth/sign-in", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: json
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                response.text().then(text => {
                    throw new Error(text)
                });
            }
        })
            .then(data => {
                console.log('Token details:', data);
                localStorage.setItem('token', data.token);
                console.log(data.role)
                localStorage.setItem('isAdmin', data.role)
                window.location.href = "index";
            })
            .catch(error => {
                let exceptionLabel = document.getElementsByClassName("auth-exception");
                exceptionLabel[0].innerHTML = error.text;
            });
    };
});