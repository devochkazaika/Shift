import React from 'react';

import PreviewImage from './PreviewImage';

const UploadImage = ({ field, form, ...props }) => {
  const [image, setImage] = React.useState(null);
  const inputRef = React.useRef();

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
        ref={inputRef}
        name={field.name}
        type="file"
        accept="image/*"
        onChange={(e) => handleUploadImage(e)}
      />
      {image && (
        <PreviewImage
          image={image}
          setImage={setImage}
          setFieldValue={form.setFieldValue}
          fieldName={field.name}
          input={inputRef.current}
        />
      )}
    </>
  );
};

export default UploadImage;
