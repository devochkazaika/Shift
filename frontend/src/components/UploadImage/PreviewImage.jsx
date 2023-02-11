import React from 'react';
import { AnimatePresence } from 'framer-motion';

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
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fillRule="evenodd"
              strokeLinejoin="round"
              strokeMiterlimit="2"
              clipRule="evenodd"
              viewBox="0 0 32 32">
              <path d="M21 5V4a3 3 0 0 0-3-3h-4a3 3 0 0 0-3 3v1H4a1 1 0 0 0 0 2h1v20.91c0 .796.316 1.559.879 2.121A2.996 2.996 0 0 0 8 30.91h16c.796 0 1.559-.316 2.121-.879A2.996 2.996 0 0 0 27 27.91V7h1a1 1 0 0 0 0-2h-7Zm4 2v20.91a.997.997 0 0 1-1 1H8a.997.997 0 0 1-1-1V7h18Zm-6-2h-6V4a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1v1Z" />
            </svg>
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
