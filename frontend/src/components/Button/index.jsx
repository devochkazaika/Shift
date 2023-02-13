import React from 'react';

import styles from './Button.module.scss';

const Button = ({ handleOnClick, text, icon, type, color }) => {
  return (
    <button className={`${styles.button} ${color}`} type={type} onClick={handleOnClick}>
      <span className={styles.icon}>{icon}</span>
      {text}
    </button>
  );
};

export default Button;
