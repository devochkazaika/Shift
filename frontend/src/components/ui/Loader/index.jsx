import React from 'react';
import useBodyScroll from '../../../hooks/useBodyScroll';

import styles from './Loader.module.scss';

const Loader = () => {
  useBodyScroll();

  return (
    <div className="overlay">
      <div className={styles.dots}>
        <div className={`${styles.dot} ${styles.dot1}`}></div>
        <div className={`${styles.dot} ${styles.dot2}`}></div>
        <div className={`${styles.dot} ${styles.dot3}`}></div>
      </div>
    </div>
  );
};

export default Loader;
