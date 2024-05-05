

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

const checkForExceedAutoBidAlerts = () => {
    if (userId === null) {
        console.error("UserId is null");
        return;
    }
    const url = `${contextPath}/autoBidAlert?userId=${userId}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data && data.length > 0) {
                data.forEach(alert => {
                    if (alert && alert.message) {
                        showAlert(alert.message);
                    }
                });
            }
        })
        .catch(error => console.error('Error fetching exceed auto-bid alerts:', error));
};



checkForAlert();
checkForExceedAutoBidAlerts();


setInterval(checkForAlert, 30000);
setInterval(checkForExceedAutoBidAlerts, 10000);



const showAlert = (message) => {
    alert(message);
};

