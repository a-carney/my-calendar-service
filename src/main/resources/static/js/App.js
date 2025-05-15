class App {

    constructor() {
        this.currentView = null;
        this.views = {
            calendar: calendarView,
            contacts: contactsView,
            locations: locationsView
        };

        this.navigate = this.navigate.bind(this);
        this.handleHashChange = this.handleHashChange.bind(this);
    }

    static get instance() {
        if (!App._instance) {
            App._instance = new App();
        }

        return App._instance;
    }

    init() {
        const hash = window.location.hash.substring(1);
        const view = this.views[hash] ? hash : 'calendar';

        this.navigate(view, false);

        window.addEventListener('hashchange', this.handleHashChange);
    }

    handleHashChange() {
        const hash = window.location.hash.substring(1);
        if (this.views[hash] && hash !== this.currentView) {
            this.navigate(hash, false);
        }
    }

    navigate(view, updateHash = true) {
        if (view === this.currentView) return;

        if (this.currentView && this.views[this.currentView]) {
            this.views[this.currentView].unmount();
        }

        this.currentView = view;

        if (updateHash) {
            window.location.hash = view;
        }
        
        const render = this.views[view];
        if (render) {
            Component.renderInfo('app-content', render.render());
            render.mount();
        } else {
            Component.renderInfo('app-content', '<div>view not found</div>');
        }
    }

    getAlpineData() {
        return {
            currentView: this.currentView,
            init()          {   App.instance.init()             },
            navigate(view)  {   App.instance.navigate(view);    }
        };
    }
}

document.addEventListener(
        'DOMContentLoaded',
        () => {
            Alpine.data('App', () => App.instance.getAlpineData());
        }
    );