
const DATE_FORMAT = { 
    month: 'short', 
    day: 'numeric', 
    year: 'numeric' 
}
const TIME_FORMAT = { 
    hour: '2-digit', 
    minute: '2-digit' 
}

const LANGUAGE = 'en-US'



const Components = {
  formatDate(dateString) {
      return new Date(dateString).toLocaleDateString(LANGUAGE, DATE_FORMAT);
  },

  formatTime(dateString) {
      return new Date(dateString).toLocaleTimeString(LANGUAGE, TIME_FORMAT);
  },

  eventCard(event) {
    return `
    <div class="event-card ${event.status.toLowerCase()}" id="event-${event.id}">
                <h3>${event.title}</h3>
                <div class="event-time">
                    <span>${this.formatDate(event.startDate)}</span> 
                    ${event.allDay ? '(All day)' : `${this.formatTime(event.startDate)} - ${this.formatTime(event.endDate)}`}
                </div>
                ${event.location ? `
                    <div class="event-location">
                        <strong>Location:</strong> ${event.location.name}
                    </div>
                ` : ''}
                ${event.description ? `
                    <div class="event-description">
                        <p>${event.description}</p>
                    </div>
                ` : ''}
                <div class="event-actions">
                    <button hx-get="/api/events/${event.id}/attendees"
                            hx-target="#attendees-container"
                            hx-swap="innerHTML">
                        Attendees
                    </button>
                    <button class="edit-btn"
                            hx-get="/api/events/${event.id}"
                            hx-target="#edit-form-container"
                            hx-swap="innerHTML">
                        Edit
                    </button>
                    <button class="delete-btn"
                            hx-delete="/api/events/${event.id}"
                            hx-confirm="Are you sure you want to delete this event?"
                            hx-target="#event-${event.id}"
                            hx-swap="outerHTML">
                        Delete
                    </button>
                </div>
            </div>
    `;
  },

  contactCard(contact) {
    return `
            <div class="contact-card" id="contact-${contact.id}">
                <h3>${contact.firstName} ${contact.lastName}</h3>
                ${contact.email ? `
                    <div class="contact-email">
                        <strong>Email:</strong> ${contact.email}
                    </div>
                ` : ''}
                ${contact.phone ? `
                    <div class="contact-phone">
                        <strong>Phone:</strong> ${contact.phone}
                    </div>
                ` : ''}
                ${contact.organization ? `
                    <div class="contact-org">
                        <strong>Organization:</strong> ${contact.organization}
                    </div>
                ` : ''}
                <div class="contact-actions">
                    <button class="edit-btn"
                            hx-get="/api/contacts/${contact.id}"
                            hx-target="#edit-contact-form"
                            hx-swap="innerHTML">
                        Edit
                    </button>
                    <button class="delete-btn"
                            hx-delete="/api/contacts/${contact.id}"
                            hx-confirm="Are you sure you want to delete this contact?"
                            hx-target="#contact-${contact.id}"
                            hx-swap="outerHTML">
                        Delete
                    </button>
                </div>
            </div>
        `;
  }
};


