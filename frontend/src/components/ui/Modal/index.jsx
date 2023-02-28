import React from 'react';
import { motion } from 'framer-motion';

import { ReactComponent as CloseIcon } from '../../../assets/icons/close.svg';
import styles from './Modal.module.scss';
import useBodyScroll from '../../../hooks/useBodyScroll';

const Modal = ({ children, onClose }) => {
  useBodyScroll();

  return (
    <div className="overlay" onClick={onClose}>
      <div>
        <CloseIcon className={styles.btn_close} onClick={onClose} width="50px" />
        <motion.div
          initial={{ scale: 0.2, x: '-80%', opacity: 0 }}
          animate={{ scale: 1, x: 0, opacity: 1 }}
          exit={{ scale: 0.2, x: '-80%', y: '-30px', opacity: 0, transition: { duration: 0.2 } }}>
          <div
            onClick={(e) => {
              e.stopPropagation();
            }}>
            {children}
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default Modal;
