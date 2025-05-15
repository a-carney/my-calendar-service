class View {

    // (note: empty methods are placeholders for child classes) 

    constructor() {
        this.api = ApiService.instance;
        this.initialize();
    }

    initialize() {}

    render() {
        return '<div>BASE</div>'
    }

    getAlpineData() {}
    setupEventListeners() {}
    cleanupEventListeners() {}

    mount() {
        this.setupEventListeners;
    }

    unmount() {
        this.cleanupEventListeners;
    }
}