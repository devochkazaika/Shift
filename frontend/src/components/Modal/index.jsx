import React from 'react';
import { disableBodyScroll, enableBodyScroll } from 'body-scroll-lock';
import { motion } from 'framer-motion';

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
        <svg
          onClick={closeModal}
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          width="50px"
          className={modalStyles.btn_close}>
          <path d="M4.293,18.293,10.586,12,4.293,5.707A1,1,0,0,1,5.707,4.293L12,10.586l6.293-6.293a1,1,0,1,1,1.414,1.414L13.414,12l6.293,6.293a1,1,0,1,1-1.414,1.414L12,13.414,5.707,19.707a1,1,0,0,1-1.414-1.414Z" />
        </svg>
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
