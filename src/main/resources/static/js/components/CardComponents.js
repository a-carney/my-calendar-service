class CardComponent extends Component {

    static eventCard(event) {
        return `
            <div class="event-card ${event.status.toLowerCase()}" id="event-${event.id}">
                <h3>${event.title}</h3>
                
                <div class="event-time">
                    ${event.allDay ? 
                        `<span>${Utils.formatDate(event.startDate)}</span> (All day)` : 
                        `<span>${Utils.formatDate(event.startDate)}</span>
                         <span>${Utils.formatTime(event.startDate)} - ${Utils.formatTime(event.endDate)}</span>`
                    }
                </div>
                
                ${event.location ? 
                    `<div class="event-location">
                        <strong>Location:</strong> ${event.location.name}
                    </div>` : 
                    ''
                }
                
                ${event.description ? 
                    `<div class="event-description">
                        <p>${event.description}</p>
                    </div>` : 
                    ''
                }
                
                <div class="event-actions">
                    <button hx-get="/api/events/${event.id}/attendees"
                            hx-target="#attendees-container"
                            hx-swap="innerHTML">
                        Attendees
                    </button>
                    
                    <button class="edit-btn"
                            onclick="Utils.dispatchEvent('edit-event', {id: ${event.id}})">
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
    }

    static contactCard(contact) {
        return `
            <div class="contact-card" id="contact-${contact.id}">
                <h3>${contact.firstName} ${contact.lastName}</h3>
                
                ${contact.email ? 
                    `<div class="contact-email">
                        <strong>Email:</strong> ${contact.email}
                    </div>` : 
                    ''
                }
                
                ${contact.phone ? 
                    `<div class="contact-phone">
                        <strong>Phone:</strong> ${contact.phone}
                    </div>` : 
                    ''
                }
                
                ${contact.organization ? 
                    `<div class="contact-org">
                        <strong>Organization:</strong> ${contact.organization}
                    </div>` : 
                    ''
                }
                
                <div class="contact-actions">
                    <button class="edit-btn"
                            onclick="Utils.dispatchEvent('edit-contact', {id: ${contact.id}})">
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
    
    static locationCard(location) {
        return `
            <div class="location-card" id="location-${location.id}">
                <h3>${location.name}</h3>
                
                ${location.address ? 
                    `<div class="location-address">
                        <strong>Address:</strong> ${location.address}
                    </div>` : 
                    ''
                }
                
                ${location.city ? 
                    `<div class="location-city">
                        <strong>City:</strong> ${location.city}
                    </div>` : 
                    ''
                }
                
                ${location.postalCode ? 
                    `<div class="location-postal">
                        <strong>Postal Code:</strong> ${location.postalCode}
                    </div>` : 
                    ''
                }
                
                <div class="location-actions">
                    <button class="edit-btn"
                            onclick="Utils.dispatchEvent('edit-location', {id: ${location.id}})">
                        Edit
                    </button>
                    
                    <button class="delete-btn"
                            hx-delete="/api/locations/${location.id}"
                            hx-confirm="Are you sure you want to delete this location?"
                            hx-target="#location-${location.id}"
                            hx-swap="outerHTML">
                        Delete
                    </button>
                </div>
            </div>
        `;
    }

    static renderCardList(containerId, items, cardRenderer, emptyMessage = 'No items found.') {
        const container = this.getElement(containerId);
        if (!container) return;
        
        if (!items || items.length === 0) {
            container.innerHTML = `<div class="no-items">${emptyMessage}</div>`;
            return;
        }
        
        container.innerHTML = items.map(item => cardRenderer(item)).join('');
    }
}