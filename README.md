# SYSTEM-REPORT-SIMO-GATEWATE
# Frontend - Website View
# SHBVN Portal SIMO 🚀

A modern web application built with React, Vite, and Bootstrap, featuring template management, data upload capabilities, and user management.

## 🌟 Features

- 📊 Template API Management
- 📁 File Upload System
- 👥 User Management
- 📜 History Tracking
- 🔐 Authentication System
- 🎨 Modern UI with Bootstrap and Tailwind CSS

## 🛠️ Tech Stack

- **Frontend Framework:** React 19
- **Build Tool:** Vite 6
- **Styling:** 
  - Bootstrap 5.3
  - Tailwind CSS 4.0
  - React Bootstrap 2.10
- **Routing:** React Router 7
- **HTTP Client:** Axios
- **Date Handling:** React DatePicker
- **Development Tools:**
  - ESLint
  - PostCSS
  - Autoprefixer

## 🚀 Getting Started

### Prerequisites

- Node.js (version 18.18.0 or higher)
- npm or yarn

### Installation

1. Clone the repository:
```bash
git clone [your-repository-url]
```

2. Install dependencies:
```bash
npm install
# or
yarn install
```

3. Create a `.env.development` file in the root directory:
```bash
VITE_SIMO_APP_API_URL=http://localhost:8081
```

4. Start the development server:
```bash
npm run dev
# or
yarn dev
```

## 📝 Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run lint` - Run ESLint
- `npm run preview` - Preview production build

## 🌐 Environment Variables

| Variable | Description |
|----------|-------------|
| `VITE_SIMO_APP_API_URL` | Backend API URL |

## 📁 Project Structure

```
src/
├── components/        # Reusable components
├── pages/            # Page components
├── css/              # CSS styles
├── services/         # API services
├── util/            # Utility functions
├── router/          # Route configurations
├── App.jsx          # Main application component
└── main.jsx         # Application entry point
```

## 🔧 Configuration Files

- `vite.config.js` - Vite configuration
- `tailwind.config.js` - Tailwind CSS configuration
- `eslint.config.js` - ESLint configuration
- `.env.development` - Development environment variables

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

