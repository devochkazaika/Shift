import React from 'react';
import { AnimatePresence } from 'framer-motion';

import { ReactComponent as BinIcon } from '../../assets/icons/bin.svg';

import previewImageStyles from './PreviewImage.module.scss';
import { convertByteArrayToSrc } from '../../utils/helpers/byteArrayFunctions';
import Modal from '../Modal';

const PreviewImage = ({ image, setFieldValue, fieldName, input }) => {
  const [preview, setPreview] = React.useState(null);
  const [modalShown, toggleModal] = React.useState(false);

  React.useEffect(() => {
    setPreview(convertByteArrayToSrc(image));
  }, [image]);

  const handleDeleteImage = () => {
    input.current.value = null;
    setPreview(null);
    setFieldValue(fieldName, null);
  };

  return (
    <div className={previewImageStyles.root}>
      {preview ? (
        <>
          <img
            className={previewImageStyles.image}
            alt="preview"
            src={preview}
            width="100px"
            height="100px"
            onClick={() => toggleModal(true)}
          />
          <div className={previewImageStyles.delete} onClick={handleDeleteImage}>
            <BinIcon />
            <span>Удалить</span>
          </div>
          <AnimatePresence initial={false} onExitComplete={() => null}>
            {modalShown && <Modal onClose={() => toggleModal(false)} image={preview} />}
          </AnimatePresence>
        </>
      ) : (
        'Loading...'
      )}
    </div>
  );
};

export default PreviewImage;
