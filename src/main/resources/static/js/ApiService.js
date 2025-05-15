class ApiService {
    constructor() {
        this.baseUrl = '/api';
    }
    
    static get instance() {
        if (!ApiService._instance) {
            ApiService._instance = new ApiService();
        }
        return ApiService._instance;
    }

    getCsrfToken() {
        const cookies = document.cookie.split(';');
        for (let c of cookies) {
            const [name, value] = c.trim().split('=');
            if (name === 'XSRF-TOKEN') {
                return decodeURIComponent(value);
            }
        }
        return null;
    }
    
    async fetch(endpoint, options = {}) {
        try {

            const csrfToken = this.getCsrfToken();
            const response = await fetch(`${this.baseUrl}${endpoint}`, {
                headers: {
                    'Content-Type': 'application/json',
                    ...(csrfToken ? { 'X-XSRF-TOKEN': csrfToken } : {}),
                    ...options.headers
                },
                credentials: 'include',
                ...options
            });
            
            if (!response.ok) {
                throw new Error(`API error: ${response.status}`);
            }
            
            if (response.status === 204 || options.method === 'DELETE') {
                return { success: true };
            }
            
            return await response.json();
        } catch (error) {
            console.error('API Error:', error);
            return { error: error.message };
        }
    }
    
    
    async getAllEvents() {
        return this.fetch('/events');
    }
    
    async getEventById(id) {
        return this.fetch(`/events/${id}`);
    }
    
    async getEventsByDateRange(start, end) {
        return this.fetch(`/events/by-date-range?start=${start}&end=${end}`);
    }
    
    async createEvent(eventData) {
        return this.fetch('/events', {
            method: 'POST',
            body: JSON.stringify(eventData)
        });
    }
    
    async updateEvent(id, eventData) {
        return this.fetch(`/events/${id}`, {
            method: 'PUT',
            body: JSON.stringify(eventData)
        });
    }
    
    async deleteEvent(id) {
        return this.fetch(`/events/${id}`, {
            method: 'DELETE'
        });
    }
    
    async getEventAttendees(eventId) {
        return this.fetch(`/events/${eventId}/attendees`);
    }
    
    
    async getAllContacts() {
        return this.fetch('/contacts');
    }
    
    async getContactById(id) {
        return this.fetch(`/contacts/${id}`);
    }
    
    async searchContacts(query) {
        return this.fetch(`/contacts/search?name=${query}`);
    }
    
    async createContact(contactData) {
        return this.fetch('/contacts', {
            method: 'POST',
            body: JSON.stringify(contactData)
        });
    }
    
    async updateContact(id, contactData) {
        return this.fetch(`/contacts/${id}`, {
            method: 'PUT',
            body: JSON.stringify(contactData)
        });
    }
    
    async deleteContact(id) {
        return this.fetch(`/contacts/${id}`, {
            method: 'DELETE'
        });
    }
    
    
    async getAllLocations() {
        return this.fetch('/locations');
    }
    
    async getLocationById(id) {
        return this.fetch(`/locations/${id}`);
    }
    
    async createLocation(locationData) {
        return this.fetch('/locations', {
            method: 'POST',
            body: JSON.stringify(locationData)
        });
    }
    
    async updateLocation(id, locationData) {
        return this.fetch(`/locations/${id}`, {
            method: 'PUT',
            body: JSON.stringify(locationData)
        });
    }
    
    async deleteLocation(id) {
        return this.fetch(`/locations/${id}`, {
            method: 'DELETE'
        });
    }
}