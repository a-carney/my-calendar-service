class ContactsView extends View {

    static EDIT_CONTAINER = "edit-contact-form-container";
    static EDIT = "edit-contact";
    static SAVED = "contact-saved";
    static CANCEL = "cancel-form";


    initialize() {
        this.contacts = [];
        this.loading = true;
        this.showForm = false;
        this.searchQuery = '';
        this.searchTimeout = null;
        
        this.handleEditContact = this.handleEditContact.bind(this);
        this.handleContactSaved = this.handleContactSaved.bind(this);
        this.handleCancelForm = this.handleCancelForm.bind(this);
    }
    
    async loadContacts() {
        this.loading = true;
        this.contacts = await this.api.getAllContacts();
        this.renderContacts();
        this.loading = false;
    }
    
    async searchContacts() {
        clearTimeout(this.searchTimeout);
        this.searchTimeout = setTimeout(async () => {
            this.loading = true;
            
            if (this.searchQuery.trim()) {
                this.contacts = await this.api.searchContacts(this.searchQuery);
            } else {
                this.contacts = await this.api.getAllContacts();
            }
            
            this.renderContacts();
            this.loading = false;
        }, 300);
    }
    
    renderContacts() {
        CardComponent.renderCardList(
            'contacts-list',
            this.contacts,
            CardComponent.contactCard,
            'No contacts found.'
        );
    }
    
    async handleEditContact(e) {
        const id = e.detail.id;
        const contact = await this.api.getContactById(id);
        Component.renderInto(EDIT_CONTAINER, 
            FormComponent.contactForm(contact, true));
    }
    
    handleContactSaved() {
        this.loadContacts();
        this.showForm = false;
        Component.clear(EDIT_CONTAINER);
    }
    
    handleCancelForm() {
        this.showForm = false;
        Component.clear(EDIT_CONTAINER);
    }
    
    setupEventListeners() {
        document.addEventListener(EDIT, this.handleEditContact);
        document.addEventListener(SAVED, this.handleContactSaved);
        document.addEventListener(CANCEL, this.handleCancelForm);
    }
    
    cleanupEventListeners() {
        document.removeEventListener(EDIT, this.handleEditContact);
        document.removeEventListener(SAVED, this.handleContactSaved);
        document.removeEventListener(CANCEL, this.handleCancelForm);
    }
    
    render() {
        return `
            <div x-data="contactsViewData">
                <h1>Contacts</h1>
                
                <div class="controls">
                    <div class="search">
                        <input type="text" placeholder="Search contacts..." 
                               x-model="searchQuery"
                               @input="searchContacts">
                    </div>
                    
                    <button x-show="!showForm" @click="showForm = true">Add Contact</button>
                    <button x-show="showForm" @click="showForm = false">Cancel</button>
                </div>
                
                <div x-show="showForm" id="new-contact-form-container"></div>
                <div id="edit-contact-form-container"></div>
                
                <div id="contacts-list" class="contacts-list">
                    <template x-if="loading">
                        <div class="loading">Loading contacts...</div>
                    </template>
                </div>
            </div>
        `;
    }
    
    getAlpineData() {
        return {
            contacts: this.contacts,
            loading: this.loading,
            showForm: this.showForm,
            searchQuery: this.searchQuery,
            
            init() {
                Component.renderInto('new-contact-form-container', 
                    FormComponent.contactForm());
                
                contactsView.loadContacts();
            },
            
            searchContacts() {
                contactsView.searchQuery = this.searchQuery;
                contactsView.searchContacts();
            }
        };
    }
}

const contactsView = new ContactsView();

document.addEventListener('DOMContentLoaded', () => {
    Alpine.data('contactsViewData', () => contactsView.getAlpineData());
});