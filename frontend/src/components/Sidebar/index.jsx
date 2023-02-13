import React from 'react';
import { FaBars } from 'react-icons/fa';
import { BsFilePlus } from 'react-icons/bs';
import { NavLink } from 'react-router-dom';

import styles from './Sidebar.module.scss';

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
      </div>
    </div>
  );
};

export default Sidebar;
