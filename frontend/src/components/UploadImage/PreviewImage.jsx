import React from 'react';

const PreviewImage = ({ image }) => {
  const [preview, setPreview] = React.useState(null);

  const reader = new FileReader();
  reader.readAsDataURL(image);
  reader.onload = () => {
    setPreview(reader.result);
  };

  return (
    <div>
      {preview ? <img alt="preview" src={preview} width="100px" height="100px" /> : 'Loading...'}
    </div>
  );
};

export default PreviewImage;
