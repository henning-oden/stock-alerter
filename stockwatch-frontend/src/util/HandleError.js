export default (err) => {
    document.getElementById("errors").innerHTML = processErrorMessage(err);
}

const processErrorMessage = (err) => {
    if (err.message === 'Failed to fetch') {
        return 'Could not connect to backend server.';
    }
    if (err.message === '403' && err.login) {
        return 'Invalid username or password.';
    }
    return err;
}