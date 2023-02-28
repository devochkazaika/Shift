import React from 'react';

import styles from './Bin.module.scss';

const Bin = ({ handleOnClick }) => {
  return (
    <span onClick={handleOnClick} className={styles.bin}>
      <span></span>
      <i></i>
    </span>
  );
};

export default Bin;
