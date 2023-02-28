import React from 'react';
import { AnimatePresence } from 'framer-motion';

import { ReactComponent as BinIcon } from '../../../assets/icons/bin.svg';
import { convertByteArrayToSrc } from '../../../utils/helpers/byteArrayFunctions';

import styles from './PreviewImage.module.scss';

import Modal from '../../ui/Modal';

const PreviewImage = ({ image, setFieldValue, fieldName, inputRef }) => {
  const [preview, setPreview] = React.useState(null);
  const [modalShown, toggleModal] = React.useState(false);

  React.useEffect(() => {
    setPreview(convertByteArrayToSrc(image));
  }, [image]);

  const handleDeleteImage = () => {
    inputRef.current.value = null;
    setPreview(null);
    setFieldValue(fieldName, null);
  };

  return (
    <div className={styles.root}>
      {preview ? (
        <>
          <img
            className={styles.preview_image}
            alt="preview"
            src={preview}
            width="100px"
            height="100px"
            onClick={() => toggleModal(true)}
          />
          <div className={styles.delete} onClick={handleDeleteImage}>
            <BinIcon />
            <span>Удалить</span>
          </div>
          <AnimatePresence initial={false} onExitComplete={() => null}>
            {modalShown && (
              <Modal onClose={() => toggleModal(false)}>
                <img src={preview} alt="modal_image" className={styles.modal_image} />
              </Modal>
            )}
          </AnimatePresence>
        </>
      ) : (
        'Loading...'
      )}
    </div>
  );
};

export default PreviewImage;
