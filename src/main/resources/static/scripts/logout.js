const logoutBtn = document.getElementById("logoutButton");

logoutBtn.addEventListener('click', () => {
    fetch('/logout', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        }
    });
    window.location.replace("/sign-in")
});