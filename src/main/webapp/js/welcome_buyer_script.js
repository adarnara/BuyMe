

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


const checkForBidAlerts = () => {
    if (userId === null) {
        console.error("UserId is null");
        return;
    }
    const url = `${contextPath}/bidAlert?userId=${userId}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data && data.length > 0) {
                data.forEach(alert => showAlert(alert.message));
            }
        })
        .catch(error => console.error('Error fetching bid alerts:', error));
};


checkForBidAlerts();
checkForAlert();
setInterval(checkForAlert, 30000);
setInterval(checkForBidAlerts, 30000);

const showAlert = (message) => {
    alert(message);
};

