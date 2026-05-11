import { Link, Outlet, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Layout() {
  const { logout, user } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="flex h-screen bg-gray-100">
      {/* Sidebar */}
      <aside className="w-64 bg-gray-900 text-white hidden md:flex flex-col">
        <div className="p-4 text-2xl font-bold border-b border-gray-800">Mini CRM</div>
        <nav className="flex-1 p-4 space-y-2">
          <Link to="/" className="block py-2 px-4 rounded hover:bg-gray-800">Dashboard</Link>
          <Link to="/projects" className="block py-2 px-4 rounded hover:bg-gray-800">Projects</Link>
          <Link to="/tasks" className="block py-2 px-4 rounded hover:bg-gray-800">Tasks</Link>
        </nav>
      </aside>

      {/* Main Content */}
      <div className="flex-1 flex flex-col overflow-hidden">
        {/* Topbar */}
        <header className="flex items-center justify-between p-4 bg-white shadow-sm">
          <div className="text-xl font-semibold md:hidden">CRM</div>
          <div className="flex items-center space-x-4 ml-auto">
            <span>Welcome, {user?.name || 'User'}</span>
            <button onClick={handleLogout} className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600">
              Logout
            </button>
          </div>
        </header>

        {/* Page Content */}
        <main className="flex-1 overflow-x-hidden overflow-y-auto bg-gray-100 p-6">
          <Outlet />
        </main>
      </div>
    </div>
  );
}