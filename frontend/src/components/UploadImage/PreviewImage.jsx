import React from 'react';

const PreviewImage = ({ image, setImage, setFieldValue, fieldName, input }) => {
  const [preview, setPreview] = React.useState(null);

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
          <img alt="preview" src={preview} width="100px" height="100px" />
          <button onClick={handleDeleteImage}>Удалить</button>
        </>
      ) : (
        'Loading...'
      )}
    </div>
  );
};

export default PreviewImage;
