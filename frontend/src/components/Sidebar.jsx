import React from 'react';
import { FaBars } from 'react-icons/fa';
import { BsFilePlus } from 'react-icons/bs';
import { NavLink } from 'react-router-dom';

const Sidebar = () => {
  const [open, setOpen] = React.useState(true);

  const menuItems = [
    {
      path: '/stories',
      name: 'Stories',
      icon: <BsFilePlus />,
    },
    {
      path: '/banners',
      name: 'Banners',
      icon: <BsFilePlus />,
    },
  ];
  return (
    <div className={`sidebar ${!open ? 'collapsed' : ''}`}>
      <div className="top_section">
        {open && <h1 className="logo">Faktura</h1>}
        <div className="bars">
          <FaBars onClick={() => setOpen(!open)} />
        </div>
      </div>
      <div className="nav_links">
        {menuItems.map((item, i) => (
          <NavLink key={i} to={item.path} className="link">
            <div className="icon">{item.icon}</div>
            {open && <div className="link_text">{item.name}</div>}
          </NavLink>
        ))}
      </div>
    </div>
  );
};

export default Sidebar;
