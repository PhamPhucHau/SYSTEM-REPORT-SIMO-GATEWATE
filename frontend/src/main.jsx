import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App.jsx';

import Modal from 'react-modal';

// Cấu hình react-modal
Modal.setAppElement('#root');

// Render ứng dụng
createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>
);
