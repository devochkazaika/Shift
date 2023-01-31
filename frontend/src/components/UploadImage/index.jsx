import React from 'react';

import PreviewImage from './PreviewImage';

const UploadImage = ({ field, form, ...props }) => {
  const [image, setImage] = React.useState(null);

  const handleUploadImage = async (e) => {
    let img = e.target.files[0];
    setImage(img);
    if (img) {
      const buffer = await img.arrayBuffer();
      let byteArray = new Uint8Array(buffer);
      form.setFieldValue(field.name, Array.from(byteArray));
    } else {
      form.setFieldValue(field.name, null);
    }
  };

  return (
    <>
      <input
        name={field.name}
        type="file"
        accept="image/*"
        onChange={(e) => handleUploadImage(e)}
      />
      {image && <PreviewImage image={image} />}
    </>
  );
};

export default UploadImage;
