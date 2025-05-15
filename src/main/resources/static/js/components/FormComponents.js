class FormComponent extends Component {
    
    static eventForm(event = {}, isEdit = false) {
        const formId = isEdit ? `event-form-${event.id}` : 'new-event-form';
        const action = isEdit ? `/api/events/${event.id}` : '/api/events';
        const method = isEdit ? 'PUT' : 'POST';
        
        return `
            <form id="${formId}" 
                  class="form" 
                  hx-${method.toLowerCase()}="${action}" 
                  hx-swap="none"
                  hx-on::after-request="Utils.dispatchEvent('event-saved')">
                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" id="title" name="title" value="${event.title || ''}" required>
                </div>
                
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description">${event.description || ''}</textarea>
                </div>
                
                <div class="form-group">
                    <label for="startDate">Start Date/Time</label>
                    <input type="datetime-local" id="startDate" name="startDate" 
                           value="${Utils.toInputDateTimeString(event.startDate)}" required>
                </div>
                
                <div class="form-group">
                    <label for="endDate">End Date/Time</label>
                    <input type="datetime-local" id="endDate" name="endDate"
                           value="${Utils.toInputDateTimeString(event.endDate)}" required>
                </div>
                
                <div class="form-group">
                    <label for="locationId">Location</label>
                    <select id="locationId" name="locationId" 
                            hx-get="/api/locations"
                            hx-trigger="load once"
                            hx-indicator=".htmx-indicator">
                        <option value="">Select location...</option>
                        ${event.location ? 
                            `<option value="${event.location.id}" selected>${event.location.name}</option>` : 
                            ''}
                    </select>
                    <span class="htmx-indicator">Loading locations...</span>
                </div>
                
                <div class="form-group">
                    <label>
                        <input type="checkbox" name="allDay" ${event.allDay ? 'checked' : ''}> All Day
                    </label>
                </div>
                
                <div class="form-group">
                    <label for="status">Status</label>
                    <select id="status" name="status">
                        <option value="SCHEDULED" ${event.status === 'SCHEDULED' ? 'selected' : ''}>Scheduled</option>
                        <option value="HAPPENING" ${event.status === 'HAPPENING' ? 'selected' : ''}>Happening</option>
                        <option value="COMPLETED" ${event.status === 'COMPLETED' ? 'selected' : ''}>Completed</option>
                        <option value="CANCELED" ${event.status === 'CANCELED' ? 'selected' : ''}>Canceled</option>
                    </select>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn-primary">Save</button>
                    <button type="button" class="btn-secondary" 
                            onclick="Utils.dispatchEvent('cancel-form')">
                        Cancel
                    </button>
                </div>
            </form>
        `;
    }
    
    static contactForm(contact = {}, isEdit = false) {
        const formId = isEdit ? `contact-form-${contact.id}` : 'new-contact-form';
        const action = isEdit ? `/api/contacts/${contact.id}` : '/api/contacts';
        const method = isEdit ? 'PUT' : 'POST';
        
        return `
            <form id="${formId}" 
                  class="form" 
                  hx-${method.toLowerCase()}="${action}" 
                  hx-swap="none"
                  hx-on::after-request="Utils.dispatchEvent('contact-saved')">
                <div class="form-group">
                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName" value="${contact.firstName || ''}" required>
                </div>
                
                <div class="form-group">
                    <label for="lastName">Last Name</label>
                    <input type="text" id="lastName" name="lastName" value="${contact.lastName || ''}" required>
                </div>
                
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="${contact.email || ''}">
                </div>
                
                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone" value="${contact.phone || ''}">
                </div>
                
                <div class="form-group">
                    <label for="organization">Organization</label>
                    <input type="text" id="organization" name="organization" value="${contact.organization || ''}">
                </div>
                
                <div class="form-group">
                    <label for="notes">Notes</label>
                    <textarea id="notes" name="notes">${contact.notes || ''}</textarea>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn-primary">Save</button>
                    <button type="button" class="btn-secondary" 
                            onclick="Utils.dispatchEvent('cancel-form')">
                        Cancel
                    </button>
                </div>
            </form>
        `;
    }
    
    static locationForm(location = {}, isEdit = false) {
        const formId = isEdit ? `location-form-${location.id}` : 'new-location-form';
        const action = isEdit ? `/api/locations/${location.id}` : '/api/locations';
        const method = isEdit ? 'PUT' : 'POST';
        
        return `
            <form id="${formId}" 
                  class="form" 
                  hx-${method.toLowerCase()}="${action}" 
                  hx-swap="none"
                  hx-on::after-request="Utils.dispatchEvent('location-saved')">
                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" id="name" name="name" value="${location.name || ''}" required>
                </div>
                
                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" value="${location.address || ''}">
                </div>
                
                <div class="form-group">
                    <label for="city">City</label>
                    <input type="text" id="city" name="city" value="${location.city || ''}">
                </div>
                
                <div class="form-group">
                    <label for="postalCode">Postal Code</label>
                    <input type="text" id="postalCode" name="postalCode" value="${location.postalCode || ''}">
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn-primary">Save</button>
                    <button type="button" class="btn-secondary" 
                            onclick="Utils.dispatchEvent('cancel-form')">
                        Cancel
                    </button>
                </div>
            </form>
        `;
    }
}