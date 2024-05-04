let newBidForm = document.getElementById('new-bid-form');
let bidAmount = document.getElementById('bidAmount');
let currentPrice = document.getElementById('currentPrice');
let bidIncrement = document.getElementById('auctionBidIncrement');
let bidOption = document.getElementById('newBid');
let autoBidOption = document.getElementById('newAutoBid');
let bidTypeInput = document.getElementById('bidType');
let autoBidIncrement = document.getElementById('bidIncrement');
let hiddenBidIncrement = document.getElementById('hiddenBidIncrement');

newBidForm.addEventListener('submit', async e => {
    e.preventDefault();
    if(bidOption.checked && validateBidPrice() || autoBidOption.checked && validateAutoBidPrice()) {
        if(autoBidOption.checked && autoBidIncrement.value !== "") {
            hiddenBidIncrement.value = autoBidIncrement.value;
        }
        newBidForm.submit();
    }
});

const validateBidPrice = () => {
    if(bidAmount.value === "") {
        setError(bidAmount, "Please enter a bid amount.");
        return false;
    }

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
        setError(bidAmount, "Bid amount must have up to two decimal places.");
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

let maxBidAmount = document.getElementById('maxBidAmount');

const disableBidInputs = () => {
    bidAmount.value = ''
    bidAmount.disabled = true;
    maxBidAmount.disabled = false;
    autoBidIncrement.disabled = false;
}

const disableAutoBidInputs = () => {
    maxBidAmount.value = ''
    autoBidIncrement.value = ''
    bidAmount.disabled = false;
    maxBidAmount.disabled = true;
    autoBidIncrement.disabled = true;
}

autoBidOption.addEventListener('change', () => {
    if(autoBidOption.checked) {
        bidTypeInput.value = 'autoBid';
        disableBidInputs();
    }
})

bidOption.addEventListener('change', () => {
    if(bidOption.checked) {
        bidTypeInput.value = 'bid';
        disableAutoBidInputs();
    }
})

const validateAutoBidPrice = () => {
    if(maxBidAmount.value === "") {
        setError(maxBidAmount, "Please enter a max bid amount.");
        return false;
    }

    if (maxBidAmount.value.trim().includes(',')) {
        setError(maxBidAmount, "Please remove any commas from price.");
        return false;
    } else if (autoBidIncrement.value.trim().includes(',')) {
        setError(autoBidIncrement, "Please remove any commas from price.");
        return false;
    }

    let maxBidAmountValue = parseFloat(maxBidAmount.value.trim());
    let autoBidIncrementValue = null;
    if(autoBidIncrement.value !== "") {
        autoBidIncrementValue = parseFloat(autoBidIncrement.value.trim());
    }

    if (isNaN(maxBidAmountValue)) {
        setError(maxBidAmount, "Please enter a valid max bid amount.");
        return false;
    } else if (autoBidIncrementValue != null && isNaN(autoBidIncrementValue)) {
        setError(autoBidIncrement, "Please enter a valid auto bid amount.");
        return false;
    }

    let regex = /\$\d{1,3}(?:,\d{3})*(?:\.\d{1,2})?/;
    let currentPriceValue = parseFloat(currentPrice.textContent.match(regex)[0].replace(',', '').slice(1));

    if(maxBidAmountValue < currentPriceValue) {
        setError(maxBidAmount, "Max bid amount must be greater than current price.");
        return false;
    }

    let bidIncrementValue = parseFloat(bidIncrement.textContent.match(regex)[0].replace(',', '').slice(1));

    if(maxBidAmountValue < currentPriceValue + bidIncrementValue) {
        setError(maxBidAmount, "Max bid amount must be greater than the current price plus the bid increment.");
        return false;
    } else if(autoBidIncrementValue != null && autoBidIncrementValue < bidIncrementValue) {
        setError(autoBidIncrement, "Bid increment must be greater than seller's bid increment.");
        return false;
    }

    if(!isValidDecimalPlaces(maxBidAmountValue)) {
        setError(maxBidAmount, "Bid amount must have up to two decimal places.");
        return false;
    } else if(autoBidIncrementValue != null && !isValidDecimalPlaces(autoBidIncrementValue)) {
        setError(autoBidIncrement, "Bid increment must have up to two decimal places.");
        return false;
    }

    return true;
}

maxBidAmount.addEventListener('change', () => {
    resetForm(maxBidAmount);
})

autoBidIncrement.addEventListener('change', () => {
    resetForm(autoBidIncrement);
})