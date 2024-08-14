import React, { useEffect, useState } from 'react';
import { FaBars } from 'react-icons/fa';
import { BsFilePlus } from 'react-icons/bs';
import { NavLink } from 'react-router-dom';

import styles from './Sidebar.module.scss';
import { AdminRoute } from '../Security/AdminRoute';
import keycloak from '../Security/Keycloak';

const Sidebar = ({ flags }) => {
  const [open, setOpen] = useState(true);
  const [menuItems, setMenuItems] = useState([]);

  useEffect(() => {
    if (flags) {
      const items = [];
      if (flags.stories) {
        items.push({
          path: '/',
          name: 'Stories',
          icon: <BsFilePlus />,
        });
      }
      if (flags.banners) {
        items.push({
          path: '/banners',
          name: 'Banners',
          icon: <BsFilePlus />,
        });
      }
      setMenuItems(items);
    }
  }, [flags]);

  const handleLogout = () => {
    keycloak.logout();
  };

  return (
    <div className={`${styles.sidebar} ${!open ? styles.collapsed : ''}`}>
      <div className={styles.top_section}>
        {open && <span className={styles.logo}>Faktura</span>}
        <div className={styles.bars}>
          <FaBars onClick={() => setOpen(!open)} />
        </div>
      </div>
      <div className={styles.nav_links}>
        {menuItems.map((item, i) => (
          <NavLink key={i} to={item.path} className={styles.link}>
            <div className={styles.icon}>{item.icon}</div>
            {open && <div className={styles.text}>{item.name}</div>}
          </NavLink>
        ))}
        <AdminRoute>
          <h1 className='textSidebar'>
            <span>ADMIN</span>
          </h1>
        </AdminRoute>
        <AdminRoute>
          <NavLink to="/unApproved" className={styles.link}>
            <div className={styles.icon}><BsFilePlus /></div>
            {open && <div className={styles.text}>Непринятые</div>}
          </NavLink>
          <NavLink to="/deleted" className={styles.link}>
            <div className={styles.icon}><BsFilePlus /></div>
            {open && <div className={styles.text}>Удаленные</div>}
          </NavLink>
        </AdminRoute>
        <button onClick={handleLogout} className={styles.link} style={{ background: "none", border: "none" }}>
          {open && <div>Выйти</div>}
        </button>
      </div>
    </div>
  );
};

export default Sidebar;
