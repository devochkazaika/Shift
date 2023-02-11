import React from 'react';
import { disableBodyScroll, enableBodyScroll } from 'body-scroll-lock';
import { motion } from 'framer-motion';

import { ReactComponent as CloseIcon } from '../../assets/icons/close.svg';

import modalStyles from './Modal.module.scss';

const Modal = ({ onClose, image }) => {
  const modalRef = React.useRef();

  disableBodyScroll(document);

  const closeModal = () => {
    onClose();
    enableBodyScroll(document);
  };

  const onBackgroundClick = (e) => {
    if (modalRef.current === e.target) {
      closeModal();
    }
  };

  return (
    <div className={modalStyles.overlay} onClick={onBackgroundClick} ref={modalRef}>
      <div>
        <CloseIcon className={modalStyles.btn_close} onClick={closeModal} width="50px" />
        <motion.div
          initial={{ scale: 0.2, x: '-80%', opacity: 0 }}
          animate={{ scale: 1, x: 0, opacity: 1 }}
          exit={{ scale: 0.2, x: '-80%', y: '-30px', opacity: 0, transition: { duration: 0.2 } }}>
          <img src={image} />
        </motion.div>
      </div>
    </div>
  );
};

export default Modal;
