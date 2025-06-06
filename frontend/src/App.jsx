import AppRouter from "./router/AppRouter";
import "bootstrap/dist/css/bootstrap.min.css";
import Modal from 'react-modal';
Modal.setAppElement('#root');
function App() {
  return (
    // <div className="d-flex justify-content-center align-items-center vh-100 bg-gray-100">
    <div>
      <AppRouter />
    </div>
  );
}

export default App;