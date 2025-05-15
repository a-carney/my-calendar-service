class CalendarView extends View {
    

    static EDIT_CONTAINER = "edit-form-container";
    static EDIT = "edit-event";
    static SAVED = "event-saved";
    static CANCEL = "cancel-form";


    initialize() {
        this.events = [];
        this.currentDate = new Date();
        this.loading = true;
        this.showForm = false;

        this.handleEditEvent = this.handleEditEvent.bind(this);
        this.handleEventSaved = this.handleEventSaved.bind(this);
        this.handleCancelForm = this.handleCancelForm.bind(this);
    }

    get startDate() {
        return Utils.toIsoString(Utils.getFirstDayOfMonth(this.currentDate));
    }

    get endDate() {
        return Utils.toIsoString(Utils.getLastDayOfMonth(this.currentDate));
    }

    get formatMonth() {
        return this.currentDate.toLocaleDateString('en-US', { month: 'long', year: 'numberic' });
    }

    async loadEvents() {
        this.loading = true;
        this.events = await this.api.getEventsByDateRange(this.startDate, this.endDate);
        this.renderEvents();
        this.loading = false;
    }

    renderEvents() {
        const view = this.events
                        .map(e => CardComponent.eventCard(e))
                        .join('');
                    
        const container = Component.getElement('events-list');

        if (container) {
            container.innerHTML = view || '<div class="no-events">No events found for this period.</div>'
        }
    }

    previousMonth() {
        this.currentDate.setMonth(this.currentDate.getMonth() - 1);
        this.loadEvents();
    }

    nextMonth() {
        this.currentDate.setMonth(this.currentDate.getMonth() + 1);
        this.loadEvents();
    }

    async handleEditEvent(e) {
        const id = e.detail.id;
        const event = await this.api.getEventById(id);

        Component.renderInfo(
            EDIT_CONTAINER,
            FormComponent.eventForm(event, true)
        );
    }

    handleEventSaved() {
        this.loadEvents();
        this.showForm = false;
        Component.clear(EDIT_CONTAINER);
    }

    setupEventListeners() {
        document.addEventListener(EDIT, this.handleEditEvent);
        document.addEventListener(SAVED, this.handleEventSaved);
        document.addEventListener(CANCEL, this.handleCancelForm);
    }

    cleanupEventListeners() {
        document.removeEventListener(EDIT, this.handleEditEvent);
        document.removeEventListener(SAVED, this.handleEventSaved);
        document.removeEventListener(CANCEL, this.handleCancelForm);
    }

    render() {
        return `
        <div x-data="calendarViewData">
                <h1>Calendar</h1>
                
                <!-- Calendar navigation -->
                <div class="calendar-controls">
                    <button @click="previousMonth">Previous Month</button>
                    <span x-text="formatMonth"></span>
                    <button @click="nextMonth">Next Month</button>
                </div>
                
                <!-- Events container -->
                <div id="events-container" class="events-list">
                    <template x-if="loading">
                        <div class="loading">Loading events...</div>
                    </template>
                    <div id="events-list"></div>
                </div>
                
                <!-- Attendees and edit containers -->
                <div id="attendees-container"></div>
                <div id="edit-form-container"></div>
                
                <!-- Add event button/form -->
                <div>
                    <button x-show="!showForm" @click="showForm = true">Add Event</button>
                    <button x-show="showForm" @click="showForm = false">Cancel</button>
                    
                    <div x-show="showForm" id="new-event-form-container"></div>
                </div>
            </div>
        `;
    }



    getAlpineData() {
        return {
            currentDate: this.currentDate,
            events: this.events,
            loading: this.loading,
            showForm: this.showForm,

            get formatMonth() {
                return this.currentDate.toLocaleDateString(
                    'en-US',
                    { month: 'long', year: 'numeric' },
                );
            },

            init() {
                Component.renderInfo(
                    'new-event-form-container',
                    FormComponent.eventForm()
                );
                calendarView.loadEvents();
            },

            previousMonth() {
                calendarView.previousMonth();
            },

            nextMonth() {
                calendarView.nextMonth();
            }
        };
    }
}

const calendarView = new CalendarView();

document.addEventListener(
    'DOMContentLoaded', 
    () => {
        Alpine.data(
            'calendarViewData',
            () => calendarView.getAlpineData()
        );
    }
);
