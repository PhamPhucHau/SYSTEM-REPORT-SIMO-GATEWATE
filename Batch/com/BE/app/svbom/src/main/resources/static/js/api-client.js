/**
 * SVB SIMO API Client
 * Common functionality for API testing interface
 */

class SVBAPIClient {
    constructor() {
        this.baseURL = '';
        this.defaultHeaders = {
            'Content-Type': 'application/json',
        };
    }

    /**
     * Make a generic API request
     * @param {string} endpoint - API endpoint
     * @param {string} method - HTTP method
     * @param {object} data - Request body data
     * @param {object} headers - Additional headers
     * @returns {Promise} - Response promise
     */
    async request(endpoint, method = 'GET', data = null, headers = {}) {
        const url = `${this.baseURL}${endpoint}`;
        const requestHeaders = { ...this.defaultHeaders, ...headers };
        
        const options = {
            method,
            headers: requestHeaders,
        };

        if (data && method !== 'GET') {
            options.body = typeof data === 'string' ? data : JSON.stringify(data);
        }

        try {
            const response = await fetch(url, options);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            const contentType = response.headers.get('content-type');
            if (contentType && contentType.includes('application/json')) {
                return await response.json();
            } else {
                return await response.text();
            }
        } catch (error) {
            console.error('API request failed:', error);
            throw error;
        }
    }

    /**
     * GET request
     * @param {string} endpoint - API endpoint
     * @param {object} params - Query parameters
     * @returns {Promise} - Response promise
     */
    async get(endpoint, params = {}) {
        const queryString = new URLSearchParams(params).toString();
        const url = queryString ? `${endpoint}?${queryString}` : endpoint;
        return this.request(url, 'GET');
    }

    /**
     * POST request
     * @param {string} endpoint - API endpoint
     * @param {object} data - Request body data
     * @returns {Promise} - Response promise
     */
    async post(endpoint, data = null) {
        return this.request(endpoint, 'POST', data);
    }

    /**
     * PUT request
     * @param {string} endpoint - API endpoint
     * @param {object} data - Request body data
     * @returns {Promise} - Response promise
     */
    async put(endpoint, data = null) {
        return this.request(endpoint, 'PUT', data);
    }

    /**
     * DELETE request
     * @param {string} endpoint - API endpoint
     * @returns {Promise} - Response promise
     */
    async delete(endpoint) {
        return this.request(endpoint, 'DELETE');
    }
}

// Global API client instance
const apiClient = new SVBAPIClient();

/**
 * Utility functions for the web interface
 */

// Show notification
function showNotification(message, type = 'info', duration = 3000) {
    const notification = document.createElement('div');
    notification.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
    notification.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    notification.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
        if (notification.parentNode) {
            notification.remove();
        }
    }, duration);
}

// Format JSON for display
function formatJSON(obj) {
    try {
        return JSON.stringify(obj, null, 2);
    } catch (error) {
        return String(obj);
    }
}

// Validate required fields
function validateRequiredFields(fields) {
    const errors = [];
    
    fields.forEach(field => {
        const element = document.getElementById(field.id);
        if (!element || !element.value.trim()) {
            errors.push(field.name);
            if (element) {
                element.classList.add('is-invalid');
            }
        } else if (element) {
            element.classList.remove('is-invalid');
        }
    });
    
    return errors;
}

// Clear form validation
function clearValidation(formId) {
    const form = document.getElementById(formId);
    if (form) {
        const invalidElements = form.querySelectorAll('.is-invalid');
        invalidElements.forEach(element => {
            element.classList.remove('is-invalid');
        });
    }
}

// Show loading state
function showLoading(elementId, text = 'Loading...') {
    const element = document.getElementById(elementId);
    if (element) {
        element.disabled = true;
        element.innerHTML = `
            <span class="spinner-border spinner-border-sm me-2" role="status"></span>
            ${text}
        `;
    }
}

// Hide loading state
function hideLoading(elementId, originalText) {
    const element = document.getElementById(elementId);
    if (element) {
        element.disabled = false;
        element.innerHTML = originalText;
    }
}

// Copy to clipboard
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        showNotification('Copied to clipboard!', 'success', 2000);
    }).catch(() => {
        showNotification('Failed to copy to clipboard', 'warning', 2000);
    });
}

// Download JSON
function downloadJSON(data, filename) {
    const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
}

// Format date
function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
}

// Format file size
function formatFileSize(bytes) {
    if (!bytes) return 'N/A';
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    if (bytes === 0) return '0 Bytes';
    const i = Math.floor(Math.log(bytes) / Math.log(1024));
    return Math.round(bytes / Math.pow(1024, i) * 100) / 100 + ' ' + sizes[i];
}

// Get status badge class
function getStatusBadgeClass(status) {
    const statusMap = {
        'COMPLETED': 'bg-success',
        'PROCESSING': 'bg-warning',
        'FAILED': 'bg-danger',
        'PENDING': 'bg-info',
        'SUCCESS': 'bg-success',
        'ERROR': 'bg-danger'
    };
    return statusMap[status] || 'bg-secondary';
}

// Debounce function
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Throttle function
function throttle(func, limit) {
    let inThrottle;
    return function() {
        const args = arguments;
        const context = this;
        if (!inThrottle) {
            func.apply(context, args);
            inThrottle = true;
            setTimeout(() => inThrottle = false, limit);
        }
    };
}

// Export functions for global use
window.SVBAPIClient = SVBAPIClient;
window.apiClient = apiClient;
window.showNotification = showNotification;
window.formatJSON = formatJSON;
window.validateRequiredFields = validateRequiredFields;
window.clearValidation = clearValidation;
window.showLoading = showLoading;
window.hideLoading = hideLoading;
window.copyToClipboard = copyToClipboard;
window.downloadJSON = downloadJSON;
window.formatDate = formatDate;
window.formatFileSize = formatFileSize;
window.getStatusBadgeClass = getStatusBadgeClass;
window.debounce = debounce;
window.throttle = throttle; 