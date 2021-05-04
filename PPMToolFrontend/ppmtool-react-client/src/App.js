import './App.css';
import './components/Dashboard'
import Dashboard from './components/Dashboard';
import Header from './components/Layout/Header';

function App() {
  return (
    <div className="App">
      <Header />
      <Dashboard />
    </div>
  );
}

export default App;
