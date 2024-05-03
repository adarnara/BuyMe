document.addEventListener('DOMContentLoaded', (event) => {
    const searchIcon = document.getElementById('search-icon');
    const searchContainer = searchIcon.closest('.search-container');
    const searchInput = document.getElementById('search-input');

    searchIcon.addEventListener('click', (event) => {
        event.preventDefault();
        if (searchContainer.classList.contains('expanded')) {
            searchContainer.classList.remove('expanded');
        }
        else {
            searchContainer.classList.add('expanded');
            setTimeout(() => searchInput.focus(), 500);
        }
    });

    document.addEventListener('click', (event) => {
        if (!searchContainer.contains(event.target) && searchContainer.classList.contains('expanded')) {
            searchContainer.classList.remove('expanded');
        }
    });

    searchInput.addEventListener('transitionend', (event) => {
        if (event.propertyName === 'width' && !searchContainer.classList.contains('expanded')) {
            searchInput.value = '';
        }
    });
});

let categorySelect = document.getElementById('itemCategory');
let brandSelect = document.getElementById('itemBrand');
let itemNameSelect = document.getElementById('itemName');
let colorSelect = document.getElementById('itemColor');

let itemNames = {
    'Apple': {
        'Laptop': ['MacBook Air', 'MacBook Pro'],
        'Tablet': ['iPad', 'iPad Pro', 'iPad Air'],
        'Desktop': ['iMac', 'Mac Mini']
    },
    'Dell': {
        'Laptop': ['XPS', 'Inspiron'],
        'Tablet': ['Venue', 'Latitude'],
        'Desktop': ['OptiPlex', 'Precision']
    },
    'Lenovo': {
        'Laptop': ['ThinkPad', 'IdeaPad'],
        'Tablet': ['Yoga', 'ThinkPad Tablet'],
        'Desktop': ['ThinkCentre', 'IdeaCentre']
    }
};

const populateItemNames = () => {
    let brand = brandSelect.value;
    let category = categorySelect.value;
    let itemNamesArr = itemNames[brand][category];

    // Clear existing options
    itemNameSelect.innerHTML = '';

    itemNamesArr.forEach(function(itemName) {
        let option = document.createElement('option');
        option.text = itemName;
        option.value = itemName;
        itemNameSelect.add(option);
    });
}

categorySelect.addEventListener('change', () => {
    if(categorySelect.value !== 'Item category' && categorySelect.classList.contains('is-invalid')) {
        categorySelect.classList.remove('is-invalid');
    }

    if (categorySelect.value !== 'Item category') {
        brandSelect.removeAttribute('disabled');
    } else {
        brandSelect.setAttribute('disabled', 'disabled');
        brandSelect.selectedIndex = 0;
        itemNameSelect.innerHTML = '';
        let option = document.createElement('option');
        option.text = 'Item name';
        option.value = 'Item name';
        itemNameSelect.add(option);
        itemNameSelect.setAttribute('disabled', 'disabled');
    }
})

brandSelect.addEventListener('change', () => {
    if(brandSelect.value !== 'Item brand' && brandSelect.classList.contains('is-invalid')) {
        brandSelect.classList.remove('is-invalid');
    }

    if (brandSelect.value !== 'Item brand') {
        itemNameSelect.removeAttribute('disabled');
        populateItemNames();
    } else {
        itemNameSelect.innerHTML = '';
        let option = document.createElement('option');
        option.text = 'Item name';
        option.value = 'Item name';
        itemNameSelect.add(option);
        itemNameSelect.setAttribute('disabled', 'disabled');
    }
});

itemNameSelect.addEventListener('change', () => {
    if(itemNameSelect.value !== 'Item name' && itemNameSelect.classList.contains('is-invalid')) {
        itemNameSelect.classList.remove('is-invalid');
    }
})

colorSelect.addEventListener('change', () => {
    if(colorSelect.value !== 'Item color' && colorSelect.classList.contains('is-invalid')) {
        colorSelect.classList.remove('is-invalid');
    }
})

const validateItem = () => {
    if(categorySelect.value === 'Item category') {
        categorySelect.classList.add('is-invalid');
        return false;
    } else if(brandSelect.value === 'Item brand') {
        brandSelect.classList.add('is-invalid');
        return false;
    } else if(itemNameSelect.value === 'Item name') {
        itemNameSelect.classList.add('is-invalid');
        return false;
    } else if(colorSelect.value === 'Item color') {
        colorSelect.classList.add('is-invalid');
        return false;
    }
    return true;
};

let newAuctionForm = document.getElementById('new-auction-form');
let initialPrice = document.getElementById('initialPrice');
let bidIncrement = document.getElementById('bidIncrement');
let minimumPrice = document.getElementById('minimumPrice');
let closingDate = document.getElementById('closingDate');
let closingTime = document.getElementById('closingTime');

newAuctionForm.addEventListener('submit', async e => {
    e.preventDefault();
    if(validatePrice() && validateTime() && validateItem()) {
        newAuctionForm.submit();
    }
});

const setError = (element, message) => {
    let inputControl = element.parentElement;
    let errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = message;
    errorDisplay.classList.add('error');
    element.classList.add('is-invalid');
}

const validateTime = () => {
    const closingDateValue = new Date(closingDate.value);
    const closingTimeValue = closingTime.value.split(':');
    closingDateValue.setDate(closingDateValue.getDate() + 1);

    const hours = parseInt(closingTimeValue[0]);
    const minutes = parseInt(closingTimeValue[1]);
    closingDateValue.setHours(hours, minutes, 0, 0);

    const currentDateValue = new Date();

    if(currentDateValue < closingDateValue) {
        return true;
    } else if(currentDateValue.getMonth() === closingDateValue.getMonth() && currentDateValue.getDay() === closingDateValue.getDay() && currentDateValue.getFullYear() === closingDateValue.getFullYear()) {
        // if same day but future time
        if(currentDateValue.getHours() < closingDateValue.getHours() || currentDateValue.getHours() === closingDateValue.getHours() && currentDateValue.getMinutes() < closingDateValue.getMinutes()) {
            return true;
        } else {
            setError(closingTime, "Please choose a future time or date.");
        }
    } else {
        setError(closingDate, "Please choose a future date.");
        return false;
    }
};

const validatePrice = () => {
    if (initialPrice.value.trim().includes(',')) {
        setError(initialPrice, "Please remove any commas from price.");
        return false;
    } else if(bidIncrement.value.trim().includes(',')) {
        setError(bidIncrement, "Please remove any commas from price.");
        return false;
    } else if(minimumPrice.value.trim().includes(',')) {
        setError(minimumPrice, "Please remove any commas from price.");
        return false;
    }

    const initialPriceValue = parseFloat(initialPrice.value.trim());
    const bidIncrementValue = parseFloat(bidIncrement.value.trim());
    let minimumPriceValue = null;
    if(minimumPrice.value !== "") {
        minimumPriceValue = parseFloat(minimumPrice.value.trim());
    }

    if (isNaN(initialPriceValue)) {
        setError(initialPrice, "Please enter a valid initial price.");
        return false;
    } else if(isNaN(bidIncrementValue)) {
        setError(bidIncrement, "Please enter a valid bid increment.");
        return false;
    } else if(minimumPriceValue != null && isNaN(minimumPriceValue)) {
        setError(minimumPrice, "Please enter a valid minimum price.");
        return false;
    }

    if(initialPriceValue <= 0) {
        setError(initialPrice, "Initial price must be greater than zero.");
        return false;
    } else if(bidIncrementValue <= 0) {
        setError(bidIncrement, "Bid increment must be greater than zero.");
        return false;
    } else if(minimumPriceValue != null && minimumPriceValue <= 0) {
        setError(minimumPrice, "Minimum price must be greater than zero.");
        return false;
    }

    if(!isValidDecimalPlaces(initialPriceValue)) {
        setError(initialPrice, "Initial price must have up to two decimal places.");
        return false;
    } else if(!isValidDecimalPlaces(bidIncrementValue)) {
        setError(bidIncrement, "Bid increment must have up to two decimal places.");
        return false;
    } else if(minimumPriceValue != null && !isValidDecimalPlaces(minimumPriceValue)) {
        setError(minimumPrice, "Minimum price must have up to two decimal places.");
        return false;
    }

    return true;
}

const isValidDecimalPlaces = (number) => {
    const decimalPlaces = (number.toString().split('.')[1] || '').length;
    return decimalPlaces <= 2;
}

closingDate.addEventListener('change', () => {
    resetForm(closingDate);
})

closingTime.addEventListener('change', () => {
    resetForm(closingTime);
})

initialPrice.addEventListener('change', () => {
    resetForm(initialPrice);
})

minimumPrice.addEventListener('change', () => {
    resetForm(minimumPrice);
})

bidIncrement.addEventListener('change', () => {
    resetForm(bidIncrement);
})

const resetForm = (element) => {
    let inputControl = element.parentElement;
    let errorDisplay = inputControl.querySelector('.error');

    if(element.classList.contains('is-invalid')) {
        errorDisplay.innerText = "";
        element.classList.remove('is-invalid');
    }
};

let stars = document.getElementById('stars');
let moon = document.getElementById('moon');
let mountains_behind = document.getElementById('mountains_behind');
let mountains_front = document.getElementById('mountains_front');
let btn = document.getElementById('btn');
let header = document.querySelector('header');
let main_message = document.getElementById('main-message');

window.addEventListener('scroll', function (){
    let value = window.scrollY;
    stars.style.left = value * 0.25 + 'px';
    moon.style.top = value * 1.05 + 'px';
    mountains_behind.style.top = value * 0.5 + 'px';
    mountains_front.style.top = value * 0 + 'px';
    btn.style.marginTop = value * 1.5 + 'px';
    header.style.top = value * 0.5 + 'px';
    main_message.style.marginRight = value * 4 + 'px';
    main_message.style.marginTop = value * 1.5 + 'px';
});