class Utils {
    static LANGUAGE = 'en-US';

    static formatDate(date) {
        if (!date)  return '';

        return new Date(date).toLocaleDateString(
            this.LANGUAGE,
            {
                year: 'numeric',
                month: 'short',
                day: 'numeric'
            }
        );
    }     

    static formatTime(date) {
        if (!date)  return '';

        return new Date(
            this.LANGUAGE,
            {
                hour: '2-digit',
                minute: '2-digit'
            }
        );
    }

    static formatDateTime(date) {
        if (!date)  return '';

        d = this.formatDate(date);
        t = this.formatTime(date);

        return  `${d} ${t}`;
    }

    static getFirstDayOfMonth(date) {
        const day = new Date(date);
        day.setDate(1);
        day.setHours(0, 0, 0, 0);

        return day;
    }

    static getLastDayOfMonth(date) {
        const day = new Date(date);
        day.setMonth(day.getMonth + 1, 0);
        day.setHours(0, 0, 0, 0);

        return day;
    }

    static toIsoString(date) {
        if (!date)  return '';

        return date.toIsoString().subString(0, 19);
    }

    static toInputDateTimeString(isoString) {
        if (!isoString) return '';

        return isoString.replace('Z', '').subString(0, 19);
    }

    static createEvent(name, detail = {}) {
        return new CustomEvent(name, { details });
    }

    static dispatchEvent(name, detail = {}) {
        document.dispatchEvent(this.createEvent(name, detail));
    }
}