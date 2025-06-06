// src/services/historyService.js
export const getHistoryByCif = (cif) => {
    return Promise.resolve([{ cif: cif, history: [`History for CIF ${cif}`] }]);
  };