import React from 'react';

const UploadImage = ({ field, form, ...props }) => {
  const [image, setImage] = React.useState(null);

  const handleUploadImage = async (e) => {
    let img = e.target.files[0];
    setImage(img);
    const buffer = await img.arrayBuffer();
    let byteArray = new Uint8Array(buffer);
    form.setFieldValue(field.name, Array.from(byteArray));
  };

  return (
    <>
      <input
        name={field.name}
        type="file"
        accept="image/*"
        onChange={(e) => handleUploadImage(e)}
      />
      <div className="file_preview">{image && <img src={image.name} />}</div>
    </>
  );
};

export default UploadImage;
