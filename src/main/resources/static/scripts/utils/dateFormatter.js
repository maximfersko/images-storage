import {options} from "../config/configDate.js";

export const dateFormater = (localDateTimeString) => {
    const correctFormatString = localDateTimeString.replace(/\s/g, 'T');
    const date = new Date(correctFormatString);
    if (isNaN(date)) {
        console.error("Received date is invalid:", localDateTimeString);
        return "Invalid date format";
    }
    return new Intl.DateTimeFormat('ru-RU', options).format(date);
}