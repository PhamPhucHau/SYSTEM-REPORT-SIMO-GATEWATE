// src/services/templateService.js
export const getTemplates = () => {
    return Promise.resolve([{ id: 1, name: "Template 1" }]); // Mock data
  };
  
  export const createTemplate = () => {
    return Promise.resolve({ id: 2, name: "New Template" }); // Mock creation
  };