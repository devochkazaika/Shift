import React from 'react';

import buttonStyles from './Button.module.scss';

const Button = ({ handleOnClick, text, icon, type, color }) => {
  return (
    <button className={buttonStyles.button + ' ' + color} type={type} onClick={handleOnClick}>
      <span className={buttonStyles.icon}>{icon}</span>
      {text}
    </button>
  );
};

export default Button;
