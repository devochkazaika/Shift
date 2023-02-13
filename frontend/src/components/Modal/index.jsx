import React from 'react';
import { disableBodyScroll, enableBodyScroll } from 'body-scroll-lock';
import { motion } from 'framer-motion';

import { ReactComponent as CloseIcon } from '../../assets/icons/close.svg';

import styles from './Modal.module.scss';

const Modal = ({ onClose, image }) => {
  const modalRef = React.useRef();

  React.useEffect(() => {
    disableBodyScroll(document);
    return () => enableBodyScroll(document);
  }, []);

  const onBackgroundClick = (e) => {
    if (modalRef.current === e.target) {
      onClose();
    }
  };

  return (
    <div className="overlay" onClick={onBackgroundClick} ref={modalRef}>
      <div>
        <CloseIcon className={styles.btn_close} onClick={() => onClose()} width="50px" />
        <motion.div
          initial={{ scale: 0.2, x: '-80%', opacity: 0 }}
          animate={{ scale: 1, x: 0, opacity: 1 }}
          exit={{ scale: 0.2, x: '-80%', y: '-30px', opacity: 0, transition: { duration: 0.2 } }}>
          <img src={image} className={styles.image} />
        </motion.div>
      </div>
    </div>
  );
};

export default Modal;
