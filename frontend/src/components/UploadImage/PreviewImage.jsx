import React from 'react';
import { AnimatePresence } from 'framer-motion';

import Modal from '../Modal';

const PreviewImage = ({ image, setImage, setFieldValue, fieldName, input }) => {
  const [preview, setPreview] = React.useState(null);
  const [modalShown, toggleModal] = React.useState(false);

  const reader = new FileReader();
  reader.readAsDataURL(image);
  reader.onload = () => {
    setPreview(reader.result);
  };

  const handleDeleteImage = () => {
    input.value = '';
    setPreview(null);
    setImage(null);
    setFieldValue(fieldName, null);
  };

  return (
    <div style={{ marginTop: '10px' }}>
      {preview ? (
        <>
          <img
            style={{ objectFit: 'cover' }}
            alt="preview"
            src={preview}
            width="100px"
            height="100px"
            onClick={() => toggleModal(true)}
          />
          <button onClick={handleDeleteImage}>Удалить</button>
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
