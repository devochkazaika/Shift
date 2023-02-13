import React from 'react';
import { motion } from 'framer-motion';

import styles from './AlertMessage.module.scss';

const AlertMessage = ({ success }) => {
  return (
    <motion.div
      initial={{ scale: 0.2, x: '-50%', opacity: 0 }}
      animate={{ scale: 1, x: 0, opacity: 1 }}
      className={`${styles.root} ${success ? styles.success : styles.fail}`}>
      {' '}
      {success ? 'Данные успешно отправлены!' : 'Возникла ошибка :('}
    </motion.div>
  );
};

export default AlertMessage;
