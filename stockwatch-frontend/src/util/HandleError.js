export default (err) => {
    document.getElementById("errors").innerHTML = processErrorMessage(err);
}

const processErrorMessage = (err) => {
    if (err === 'TypeError') {
        return 'Could not connect to backend server.';
    }
    return err;
}