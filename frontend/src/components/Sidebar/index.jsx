import React from 'react';
import { FaBars } from 'react-icons/fa';
import { BsFilePlus } from 'react-icons/bs';
import { NavLink } from 'react-router-dom';

import styles from './Sidebar.module.scss';
import { AdminRoute } from '../Security/AdminRoute';

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
  const adminItems = [
    {
      path: '/unApproved',
      name: 'Не принятые',
      icon: <BsFilePlus />,
    }
  ];
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
        {adminItems.map((item, i) => (
          <AdminRoute key={i}>
            <NavLink key={i} to={item.path} className={styles.link}>
              <div className={styles.icon}>{item.icon}</div>
              {open && <div className={styles.text}>{item.name}</div>}
            </NavLink>
          </AdminRoute>
        ))}
        <NavLink to={"/login"} className={styles.link}>
          <div> Вход</div>
        </NavLink>
      </div>
    </div>
  );
};

export default Sidebar;
