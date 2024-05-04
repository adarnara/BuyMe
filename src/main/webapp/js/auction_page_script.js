let newBidForm = document.getElementById('new-bid-form');
let bidAmount = document.getElementById('bidAmount');
let currentPrice = document.getElementById('currentPrice');
let bidIncrement = document.getElementById('auctionBidIncrement');

newBidForm.addEventListener('submit', async e => {
    e.preventDefault();
    if(validatePrice()) {
        newBidForm.submit();
    }
});

const validatePrice = () => {
    if (bidAmount.value.trim().includes(',')) {
        setError(bidAmount, "Please remove any commas from price.");
        return false;
    }

    let bidAmountValue = parseFloat(bidAmount.value.trim());

    if (isNaN(bidAmountValue)) {
        setError(bidAmount, "Please enter a valid bid amount.");
        return false;
    }

    let regex = /\$\d{1,3}(?:,\d{3})*(?:\.\d{1,2})?/;
    let currentPriceValue = parseFloat(currentPrice.textContent.match(regex)[0].replace(',', '').slice(1));

    if(bidAmountValue < currentPriceValue) {
        setError(bidAmount, "Bid amount must be greater than current price.");
        return false;
    }

    let bidIncrementValue = parseFloat(bidIncrement.textContent.match(regex)[0].replace(',', '').slice(1));

    if(bidAmountValue < currentPriceValue + bidIncrementValue) {
        setError(bidAmount, "Bid amount must be greater than the current price plus the bid increment.");
        return false;
    }

    if(!isValidDecimalPlaces(bidAmountValue)) {
        setError(initialPrice, "Bid amount must have up to two decimal places.");
        return false;
    }

    return true;
}

const isValidDecimalPlaces = (number) => {
    const decimalPlaces = (number.toString().split('.')[1] || '').length;
    return decimalPlaces <= 2;
}

bidAmount.addEventListener('change', () => {
    resetForm(bidAmount);
})

const setError = (element, message) => {
    let inputControl = element.parentElement;
    let errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = message;
    errorDisplay.classList.add('error');
    element.classList.add('is-invalid');
}

const resetForm = (element) => {
    let inputControl = element.parentElement;
    let errorDisplay = inputControl.querySelector('.error');

    if(element.classList.contains('is-invalid')) {
        errorDisplay.innerText = "";
        element.classList.remove('is-invalid');
    }
};