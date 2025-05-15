class LocationsView extends View {


    initialize() {
        this.locations = [];
        this.loading = true;
        this.showForm = false;
        
        this.handleEditLocation = this.handleEditLocation.bind(this);
        this.handleLocationSaved = this.handleLocationSaved.bind(this);
        this.handleCancelForm = this.handleCancelForm.bind(this);
    }
    
    async loadLocations() {
        this.loading = true;
        this.locations = await this.api.getAllLocations();
        this.renderLocations();
        this.loading = false;
    }
    
    renderLocations() {
        CardComponent.renderCardList(
            'locations-list',
            this.locations,
            CardComponent.locationCard,
            'No locations found.'
        );
    }
    
    async handleEditLocation(e) {
        const id = e.detail.id;
        const location = await this.api.getLocationById(id);
        Component.renderInto('edit-location-form-container', 
            FormComponent.locationForm(location, true));
    }
    
    handleLocationSaved() {
        this.loadLocations();
        this.showForm = false;
        Component.clear('edit-location-form-container');
    }
    
    handleCancelForm() {
        this.showForm = false;
        Component.clear('edit-location-form-container');
    }
    
    setupEventListeners() {
        document.addEventListener('edit-location', this.handleEditLocation);
        document.addEventListener('location-saved', this.handleLocationSaved);
        document.addEventListener('cancel-form', this.handleCancelForm);
    }
    
    cleanupEventListeners() {
        document.removeEventListener('edit-location', this.handleEditLocation);
        document.removeEventListener('location-saved', this.handleLocationSaved);
        document.removeEventListener('cancel-form', this.handleCancelForm);
    }
    
    render() {
        return `
            <div x-data="locationsViewData">
                <h1>Locations</h1>
                
                <div class="controls">
                    <button x-show="!showForm" @click="showForm = true">Add Location</button>
                    <button x-show="showForm" @click="showForm = false">Cancel</button>
                </div>
                
                <div x-show="showForm" id="new-location-form-container"></div>
                <div id="edit-location-form-container"></div>
                
                <div id="locations-list" class="locations-list">
                    <template x-if="loading">
                        <div class="loading">Loading locations...</div>
                    </template>
                </div>
            </div>
        `;
    }
    
    getAlpineData() {
        return {
            locations: this.locations,
            loading: this.loading,
            showForm: this.showForm,
            
            init() {
                Component.renderInto('new-location-form-container', 
                    FormComponent.locationForm());
                
                locationsView.loadLocations();
            }
        };
    }
}

const locationsView = new LocationsView();

document.addEventListener('DOMContentLoaded', () => {
    Alpine.data('locationsViewData', () => locationsView.getAlpineData());
});
