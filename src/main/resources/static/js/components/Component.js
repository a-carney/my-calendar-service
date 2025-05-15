class Component {

    static getElement(id) {
        document.getElementById(id);
    }

    static renderInfo(id, html) {
        const container = this.getElement(id);
        if (container) {
            container.innerHTML = html;
        }
    }

    static appendTo(id, html) {
        const container = this.getElement(id);
        if (container) {
            container.insertAdjacentHTML('beforeend', html);
        }
    }

    static clear(id) {
        const container = this.getElement(id);
        if (container) {
            container.innerHTML = '';
        }
    }
}