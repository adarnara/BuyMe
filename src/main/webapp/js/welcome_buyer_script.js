const checkForAlert = () => {
    fetch(`${contextPath}/alert`)
        .then(response => response.json())
        .then(data => {
            if (data && data.alertID) {
                const message = data.message;

                showAlert(message);
            }
        })
        .catch(error => {
            console.error('Error fetching alert:', error);
        });
}

checkForAlert();

setInterval(checkForAlert, 30000); // 30000 milliseconds = 30 seconds

const showAlert = (message) => {
    alert(message);
}