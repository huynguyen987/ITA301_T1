function validatePassword() {
    const password = document.getElementById("password").value;
    const passwordError = document.getElementById("passwordError");

    // Define the password validation requirements
    const minLength = 8;
    const hasUppercase = /[A-Z]/.test(password);
    const hasLowercase = /[a-z]/.test(password);
    const hasNumber = /\d/.test(password);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

    if (
        password.length >= minLength &&
        hasUppercase &&
        hasLowercase &&
        hasNumber &&
        hasSpecialChar
    ) {
        passwordError.textContent = ""; // Clear error message if valid
        return true;
    } else {
        passwordError.textContent = "Password must be at least 8 characters, contain uppercase, lowercase, a number, and a special character.";
        return false;
    }
}

// Attach validation to form submission
function validateForm(event) {
    if (!validatePassword()) {
        event.preventDefault(); // Prevent form submission if password is invalid
    }
}

// Attach event listeners on page load
document.addEventListener("DOMContentLoaded", function() {
    const passwordField = document.getElementById("password");
    if (passwordField) {
        passwordField.addEventListener("input", validatePassword);
    }

    const form = document.querySelector("form");
    if (form) {
        form.addEventListener("submit", validateForm);
    }
});
