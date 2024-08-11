import React from 'react';
import { FaBars } from 'react-icons/fa';
import { BsFilePlus } from 'react-icons/bs';
import { NavLink } from 'react-router-dom';

import styles from './Sidebar.module.scss';
import { AdminRoute } from '../Security/AdminRoute';
import keycloak from '../Security/Keycloak';

const Sidebar = (flags) => {
  const [open, setOpen] = React.useState(true);

  const handleLogout = () => {
    keycloak.logout(); 
  };

  const menuItems = [
    // {
    //   path: '/story',
    //   name: 'Stories',
    //   icon: <BsFilePlus />,
    // },
    // {
    //   path: '/banners',
    //   name: 'Banners',
    //   icon: <BsFilePlus />,
    // },
  ];
  if (!flags.stories){
    menuItems.push({
      path: '/story',
      name: 'Stories',
      icon: <BsFilePlus />,
    })
  }
  if (!flags.banners){
    menuItems.push({
      path: '/banners',
      name: 'Banners',
      icon: <BsFilePlus />,
    })
  }
  {console.log(flags)}
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
        <button onClick={handleLogout} className={styles.link} style={{"background": "none", "border": "none"}}>
          {open && <div>Войти</div>}
        </button>
      </div>
    </div>
  );
};

export default Sidebar;
